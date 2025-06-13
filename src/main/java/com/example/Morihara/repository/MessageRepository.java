package com.example.Morihara.repository;

import com.example.Morihara.repository.entity.Message;
import com.example.Morihara.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message>findByCreteDDateBetweenOrderByCreatedDateAsc(Date StrDate, Date EndDate);
}
