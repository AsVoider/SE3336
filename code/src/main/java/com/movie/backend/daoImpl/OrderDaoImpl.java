package com.movie.backend.daoImpl;

import com.movie.backend.dao.OrderDao;
import com.movie.backend.entity.Order;
import com.movie.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Order> getOrdersByUserId(Integer userId) {

        return orderRepository.findOrdersByUserIdIs(userId);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findOrderByIdIs(id);
    }

    @Override
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}
