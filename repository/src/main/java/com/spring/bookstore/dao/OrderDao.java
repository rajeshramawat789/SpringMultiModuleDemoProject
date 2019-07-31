package com.spring.bookstore.dao;

import com.spring.bookstore.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Orders, Long> {
}
