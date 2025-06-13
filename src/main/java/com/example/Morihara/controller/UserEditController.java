package com.example.Morihara.controller;

import com.example.Morihara.controller.Form.UserForm;
import com.example.Morihara.repository.entity.User;
import com.example.Morihara.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserEditController {
    @Autowired
    UserService userService;

    @PostMapping("/userEdit")
    public ModelAndView newContent(@RequestParam("id") String strId) {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
       User user = userService.findById(Integer.parseInt(strId));
        // 画面遷移先を指定
        mav.setViewName("/userEdit");
        // 準備した空のFormを保管
        mav.addObject("formModel", user);
        // mav.addObject("errorMessageForm", errorMessages);
        return mav;
    }

    @PostMapping("/update")
    public ModelAndView updateUser(
            @Valid @ModelAttribute("formModel") UserForm userForm,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model){
        userService.saveUser(userForm);
        return new ModelAndView("redirect:/management");
    }
}
