package com.example.Morihara.controller;

import com.example.Morihara.controller.Form.ReadForm;
import com.example.Morihara.service.ReadService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReadController {
    @Autowired
    HttpSession session;
    @Autowired
    ReadService readService;

    @PostMapping("/read")
    public String read (ReadForm read, RedirectAttributes redirectAttributes) {
        // アカウント重複チェック
        if (readService.UserIdDuB(read.getUserId()) && readService.MessageIdDuB(read.getMessageId())) {
           // result.rejectValue("reRead", "duplicate", "既読にしています");
            redirectAttributes.addFlashAttribute("readMessageForm", "既読にしています");
            return "redirect:/home";
        }
        readService.saveReadForm(read);
        return "redirect:/home";
    }
}

