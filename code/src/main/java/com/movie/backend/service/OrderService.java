package com.movie.backend.service;


import com.movie.backend.dto.OrderVM;

import java.util.List;

public interface OrderService {
    List<OrderVM> getOrdersByUserId(Integer userId);

    OrderVM getOrderById(Integer id);

    //用户下订单的事务
    //1. 将sessionId转换为session
    //2. 转换session中的seat
    //3. 检查seat是否都可以购买，如果不可以，直接返回-1
    //4. 将seat设置后，回写字符串
    //5. 生成空的order
    //6. 根据sessionId等信息生成ticket，并计算总价格
    //7. 写回总价格
    //8. 返回订单号
    Integer addOrder(Integer userId, Integer sessionId, List<Integer> seats);

    void deleteOrder(Integer orderId);
    // 抢票
    void grabTicket(Integer userId, Integer sessionId);
}
