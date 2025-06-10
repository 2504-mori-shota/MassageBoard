package com.example.Morihara.controller;

import com.example.Morihara.controller.Form.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @GetMapping
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        UserForm userForm = new UserForm();
        // 画面遷移先を指定
        mav.setViewName("/login");
        // 準備した空のFormを保管
        mav.addObject("formModel", userForm);
        // mav.addObject("errorMessageForm", errorMessages);
        return mav;
    }
}
