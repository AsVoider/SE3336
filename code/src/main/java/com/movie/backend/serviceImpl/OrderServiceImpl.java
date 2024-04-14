package com.movie.backend.serviceImpl;

import com.movie.backend.dao.*;
import com.movie.backend.dto.OrderVM;
import com.movie.backend.dto.TicketDTO;
import com.movie.backend.entity.GrabItem;
import com.movie.backend.entity.Order;
import com.movie.backend.entity.Session;
import com.movie.backend.entity.Ticket;
import com.movie.backend.service.OrderService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    SessionDao sessionDao;

    @Autowired
    UserDao userDao;

    @Autowired
    TicketDao ticketDao;

    @Autowired
    CuratorFramework curatorFramework;

    @Autowired
    GrabItemDao grabItemDao;

    private OrderVM transferOrderDTO(Order order) {
        OrderVM orderVM = new OrderVM();
        orderVM.setId(order.getId());
        orderVM.setTime(order.getTime());
        orderVM.setUserId(order.getUserId());
        System.out.println("order user id is " + order.getUserId());
        orderVM.setTotalPrice(order.getTotalPrice());
        orderVM.setIsPaid(order.getIsPaid());
        Set<TicketDTO> tickets = new HashSet<>();
        List<Ticket> ticketList = order.getTickets();
        if (!ticketList.isEmpty()) {
            for (Ticket ticket : ticketList) {
                TicketDTO ticketDTO = new TicketDTO();
                ticketDTO.setId(ticket.getId());
                ticketDTO.setOrderId(ticket.getOrderId());
                ticketDTO.setSessionId(ticket.getSessionId());
                ticketDTO.setRow(ticket.getRow());
                ticketDTO.setCol(ticket.getCol());
                ticketDTO.setPrice(ticket.getPrice());
                ticketDTO.setState(ticket.getState());
                ticketDTO.setMovieName(ticket.getSession().getMovie().getTitle());
                ticketDTO.setCinemaName(ticket.getSession().getRoom().getCinema().getName());
                ticketDTO.setRoomName(ticket.getSession().getRoom().getName());
                tickets.add(ticketDTO);
            }
        }
        orderVM.setTickets(tickets);
        return orderVM;
    }

    @Override
    public List<OrderVM> getOrdersByUserId(Integer userId) {
        List<OrderVM> orderVMS = new ArrayList<>();
        if (userId == null) {
            return orderVMS;
        }
        List<Order> orders = orderDao.getOrdersByUserId(userId);
        for (Order order : orders) {
            OrderVM orderVM = transferOrderDTO(order);
            orderVMS.add(orderVM);
        }
        return orderVMS;
    }

    @Override
    public OrderVM getOrderById(Integer id) {
        if (id == null) {
            return null;
        }
        Order order = orderDao.getOrderById(id);
        if (order == null) {
            return null;
        }
        OrderVM orderVM = transferOrderDTO(order);
        return orderVM;
    }

    @Override
    public Integer addOrder(Integer userId, Integer sessionId, List<Integer> seats) {
        //传入非法参数
        if (userId == null || sessionId == null || seats == null) return -1;
        //先取出session
        InterProcessMutex mtx = new InterProcessMutex(curatorFramework, "/session_" + sessionId);
        Session session = null;
        boolean error = false;
        try {
            mtx.acquire();
            Optional<Session> sessionOptional = sessionDao.getSessionById(sessionId);
            if (sessionOptional.isEmpty())
                throw new Exception("Invalid Session"); //该场次不存在
            session = sessionOptional.get();
            if (!userDao.existsUserByIdIs(userId)) return -1; //用户不存在
            if (seats.isEmpty() || seats.size() % 2 != 0)
                throw new Exception("Invalid seat"); //没有输入正确的行数和列数

            //0-base -> 1-base
            for (int i = 0; i < seats.size(); ++i) {
                seats.set(i, seats.get(i) - 1);
            }

            //方便起见，转换成一维数组
            List<Integer> sessionSeats = new ArrayList<>();
            String seatStr = session.getSeat();
            for (int i = 0; i < seatStr.length(); ++i) {
                sessionSeats.add(seatStr.charAt(i) - '1');
            }
            StringBuilder sb = new StringBuilder(seatStr);

            Integer row = session.getRoom().getRow();
            Integer col = session.getRoom().getCol();

            //解析座位是否可选
            for (int i = 0; i < seats.size(); i += 2) {
                Integer index = col * seats.get(i) + seats.get(i + 1);
                if (sessionSeats.get(index) != 0) {
                    throw new Exception("Seat Already Taken"); //座位已被抢占
                } else {
                    sessionSeats.set(index, -1);
                    sb.replace(index, index + 1, String.valueOf('0'));
                }
            }

            session.setSeat(sb.toString());

            sessionDao.saveSession(session);
        } catch (Exception e) {
            error = true;
            e.printStackTrace();
        } finally {
            try {
                mtx.release();
            } catch (Exception e) {
//                error = true;
//                e.printStackTrace();
            }
        }

        if (error) return -1;

        //生成新的订单
        Order order = new Order();
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        order.setTime(nowTime);
        order.setUserId(userId);
        Byte isPaid = 1;
        order.setIsPaid(isPaid);
        BigDecimal totalPrice = new BigDecimal(0);
        order = orderDao.saveOrder(order);
        Integer orderId = order.getId();

        //生成新的电影票
        //List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < seats.size(); i += 2) {
            Ticket ticket = new Ticket();
            ticket.setOrderId(orderId);
            Integer seatRow = seats.get(i);
            Integer seatCol = seats.get(i + 1);
            ticket.setCol(seatCol);
            ticket.setRow(seatRow);
            ticket.setSessionId(sessionId);
            ticket.setPrice(session.getPrice());
            totalPrice = totalPrice.add(session.getPrice());
            ticket.setState(0);
            ticketDao.saveTicket(ticket);
            //tickets.add(ticket);
        }

        order.setTotalPrice(totalPrice);
        orderDao.saveOrder(order);
        return orderId;
    }

    @Override
    public void deleteOrder(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        List<Ticket> tickets = order.getTickets();
        Integer sessionId = tickets.get(0).getSessionId();
        Session session = sessionDao.getSessionById(sessionId).get();
        List<Integer> sessionSeats = new ArrayList<>();
        String seatStr = session.getSeat();
        for (int i = 0; i < seatStr.length(); ++i) {
            sessionSeats.add(seatStr.charAt(i) - '1');
        }
        StringBuilder sb = new StringBuilder(seatStr);

        Integer row = session.getRoom().getRow();
        Integer col = session.getRoom().getCol();

        for (Ticket ticket : tickets) {
            Integer index = col * ticket.getRow() + ticket.getCol();
            sessionSeats.set(index, 0);
            sb.replace(index, index + 1, String.valueOf('1'));
        }
        session.setSeat(sb.toString());
        sessionDao.saveSession(session);
        orderDao.deleteOrder(orderId);
    }
    
    @Async
    public void grabTicket(Integer userId, Integer sessionId) {
//        GrabItem grabItem = grabItemDao.save(new GrabItem(userId, sessionId, 0));
//        GrabItem grabItem = new GrabItem(userId, sessionId, 0);
//        InterProcessMutex mtx = new InterProcessMutex(curatorFramework, "/session_" + sessionId);
//        boolean error = false;
//        Session session = null;
//        try {
//            mtx.acquire();
//            Optional<Session> sessionOptional = sessionDao.getSessionById(sessionId);
//            if (sessionOptional.isEmpty())
//                throw new Exception("Invalid Session"); //该场次不存在
//            session = sessionOptional.get();
//
//            int col = session.getRoom().getCol();
//
//            String seatStr = session.getSeat();
//            StringBuilder sb = new StringBuilder(seatStr);
//            for (int i = 0; i < sb.length(); ++i) {
//                if (sb.charAt(i) == '1') {
//                    sb.setCharAt(i, '0');
//                    session.setSeat(sb.toString());
//                    grabItem.setCol(i % col);
//                    grabItem.setRow(i / col);
//                    grabItem.setStatus(1);
//                    break;
//                }
//            }
//            sessionDao.saveSession(session);
//            if (grabItem.getStatus() == 0) {
//                grabItem.setStatus(2);
//            }
//            grabItemDao.save(grabItem);
//        } catch (Exception e) {
//            e.printStackTrace();
//            error = true;
//        } finally {
//            try {
//                mtx.release();
//            } catch (Exception e) {
//                error = true;
//                e.printStackTrace();
//            }
//        }
//        if (error)
//            return;
//
//        if (grabItem.getStatus() == 1) {
//            //生成新的订单
//            Order order = new Order();
//            Timestamp nowTime = new Timestamp(System.currentTimeMillis());
//            order.setTime(nowTime);
//            order.setUserId(userId);
//            Byte isPaid = 1;
//            order.setIsPaid(isPaid);
//            BigDecimal totalPrice = new BigDecimal(0);
//            order = orderDao.saveOrder(order);
//            Integer orderId = order.getId();
//
//            Ticket ticket = new Ticket();
//            ticket.setOrderId(orderId);
//            Integer seatRow = grabItem.getRow();
//            Integer seatCol = grabItem.getCol();
//            ticket.setCol(seatCol);
//            ticket.setRow(seatRow);
//            ticket.setSessionId(sessionId);
//            ticket.setPrice(session.getPrice());
//            totalPrice = totalPrice.add(session.getPrice());
//            ticket.setState(0);
//            ticketDao.saveTicket(ticket);
//
//            order.setTotalPrice(totalPrice);
//            orderDao.saveOrder(order);
//        }
    }
}
