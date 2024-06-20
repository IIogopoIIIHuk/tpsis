package com.example.ITired.repositories;

import com.example.ITired.Role;
import com.example.ITired.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername (String username);
    List<User> findAll();
    List<User> findByRoles(Role role);
}