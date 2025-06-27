package com.example.Morihara.repository;

import com.example.Morihara.repository.entity.Message;
import com.example.Morihara.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message>findByCreatedDateBetweenOrderByCreatedDateDesc(Date StrDate, Date EndDate);
    Page<Message>findByCreatedDateBetweenOrderByCreatedDateDesc(Date StrDate, Date EndDate, Pageable pageable);

    List<Message>findByCreatedDateBetweenAndCategoryContainingOrderByCreatedDateDesc(Date StrDate, Date EndDate, String category);
    Page<Message>findByCreatedDateBetweenAndCategoryContainingOrderByCreatedDateDesc(Date StrDate, Date EndDate, String category, Pageable pageable);
}
