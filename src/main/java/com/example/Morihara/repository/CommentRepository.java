package com.example.Morihara.repository;

import com.example.Morihara.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface CommentRepository extends JpaRepository<Comment, Integer>{
        List<Comment> findByMessageId(int messageId);
}
