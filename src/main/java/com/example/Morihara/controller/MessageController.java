package com.example.Morihara.controller;

import com.example.Morihara.controller.Form.MessageForm;
import com.example.Morihara.controller.Form.UserForm;
import com.example.Morihara.repository.entity.User;
import com.example.Morihara.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    HttpSession session;

    @GetMapping("/message")
    public ModelAndView newContent(HttpServletRequest request, HttpServletResponse response) {
        session = request.getSession();
        // セッションからユーザーオブジェクトを取得
        UserForm user = (UserForm) session.getAttribute("user");
        if (user == null) {
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        MessageForm messageForm = new MessageForm();
        // 画面遷移先を指定
        mav.setViewName("/message");
        // 準備した空のFormを保管
        mav.addObject("messageInfo", messageForm);
        // mav.addObject("errorMessageForm", errorMessages);
        return mav;

    }
}
