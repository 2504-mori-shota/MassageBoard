package com.example.Morihara.controller;

import com.example.Morihara.controller.Form.ReadForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReadController {
    @Autowired
    HttpSession session;

    @PostMapping("/read")
    public String read (ReadForm read) {
        read.setUserId(read.getUserId());
        return "redirect:/home";
    }
}

