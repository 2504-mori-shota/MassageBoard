package com.example.Morihara.repository;

import com.example.Morihara.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByAccountAndPassword(String account, String password);
}
