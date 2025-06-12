package com.example.Morihara.controller;

import com.example.Morihara.controller.Form.CommentForm;
import com.example.Morihara.controller.Form.MessageForm;
import com.example.Morihara.controller.Form.UserForm;
import com.example.Morihara.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    HttpSession session;

//    @GetMapping("/comment")
//    public ModelAndView newContent(HttpServletRequest request, HttpServletResponse response) {
//        session = request.getSession();
//        UserForm user = (UserForm) session.getAttribute("user");
//        ModelAndView mav = new ModelAndView();
//        CommentForm commentForm = new CommentForm();
//        mav.setViewName("/comment");
//        mav.addObject("commentInfo", commentForm);
//        return mav;
//    }

    @PostMapping("/addComment")
    public String addComment(
            @Valid @ModelAttribute CommentForm commentForm,
            BindingResult result,
            HttpServletRequest request
    ) throws ParseException {
        if (result.hasErrors()) {
            // エラー処理したければここで redirectAttributes にメッセージ追加してもOK
            return "redirect:/home";
        }

        commentService.saveComment(commentForm);
        return "redirect:/home";
    }
}