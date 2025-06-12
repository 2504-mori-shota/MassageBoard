package com.example.Morihara.service;

import com.example.Morihara.controller.Form.CommentForm;
import com.example.Morihara.repository.CommentRepository;
import com.example.Morihara.repository.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    /*
     * 取得処理
     */
    public List<CommentForm> findCommentsByMessageId(int messageId) {
        List<Comment> results = commentRepository.findByMessageId(messageId);
        return setCommentForm(results);
    }
    /*
     * Formに詰める処理
     */
    private List<CommentForm> setCommentForm(List<Comment> results) {
        List<CommentForm> comments = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            CommentForm comment = new CommentForm();
            Comment result = results.get(i);

            comment.setId(result.getId());
            comment.setText(result.getText());
            comment.setUserId(result.getUserId());
            comment.setMessageId(result.getMessageId());

            comments.add(comment);
        }
        return comments;
    }
    /*
     *セーーーーブ
     */
    public void saveComment(CommentForm reqComment) {
        Comment saveComment = setCommentEntity(reqComment);
        commentRepository.save(saveComment);
    }
    /*
     * セーブ処理
     */
    private Comment setCommentEntity(CommentForm reqComment) {
        Comment comment = new Comment();

        comment.setId(reqComment.getId());
        comment.setText(reqComment.getText());
        comment.setUserId(reqComment.getUserId());
        comment.setMessageId(reqComment.getMessageId());

        return comment;
    }
}
