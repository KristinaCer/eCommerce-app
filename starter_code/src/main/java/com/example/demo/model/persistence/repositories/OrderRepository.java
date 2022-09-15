package com.example.demo.model.persistence.repositories;


import com.example.demo.model.persistence.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.persistence.UserOrder;

import java.util.List;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {
    List<UserOrder> findByAppUser(AppUser appUser);
}
