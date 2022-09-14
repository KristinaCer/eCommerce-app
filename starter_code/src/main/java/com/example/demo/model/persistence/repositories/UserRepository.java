package com.example.demo.model.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.persistence.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
	AppUser findByUsername(String username);
}
