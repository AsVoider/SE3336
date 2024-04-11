package com.movie.backend.dao;

import com.movie.backend.entity.Order;

import java.util.List;

public interface OrderDao{
    public List<Order> getOrdersByUserId(Integer userId);

    Order saveOrder(Order order);

    Order getOrderById(Integer id);

    void deleteOrder(Integer id);
}
