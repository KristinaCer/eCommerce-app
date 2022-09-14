package com.example.demo.service;

import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String userName, String roleName);
    AppUser getUser(String userName);
    List<AppUser> getUsers();
}
