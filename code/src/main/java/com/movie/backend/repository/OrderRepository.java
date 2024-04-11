package com.movie.backend.repository;

import com.movie.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByUserIdIs(Integer userId);

    Order findOrderByIdIs(Integer id);
}