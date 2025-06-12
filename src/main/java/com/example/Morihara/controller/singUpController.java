package com.example.Morihara.controller;

import com.example.Morihara.controller.Form.UserForm;
import com.example.Morihara.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class singUpController {
    @Autowired
    UserService userService;


    @GetMapping("/singUp")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        UserForm userForm = new UserForm();
        // 画面遷移先を指定
        mav.setViewName("/singUp");
        // 準備した空のFormを保管
        mav.addObject("formModel", userForm);
        // mav.addObject("errorMessageForm", errorMessages);
        return mav;
    }

    /*
     * 新規投稿処理
     */
    @PostMapping("/insert")

    public ModelAndView addContent(
            @Valid @ModelAttribute("formModel") UserForm userForm, BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model
    ) throws ParseException {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("/singUp");
            mav.addObject("formModel", userForm);
            return mav;
        }
        // ユーザー名の重複をチェック
//        if (userService.isUsernameTaken(userForm.getName())) {
//            ModelAndView mav = new ModelAndView("/singUp");
//            result.rejectValue("account", "account", "ユーザー名が既に使用されています");
//            return mav; // エラーパラメータを追加
//        }
        // 投稿をテーブルに格納
        userService.saveUser(userForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/login");
    }
}
