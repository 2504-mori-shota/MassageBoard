package com.example.Morihara.controller;

import com.example.Morihara.controller.Form.UserForm;
import com.example.Morihara.repository.entity.User;
import com.example.Morihara.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
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

  @PostMapping("/loading")
    public ModelAndView addContent(
            @Valid @ModelAttribute("formModel") UserForm userForm,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model){
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("/login");
            mav.addObject("formModel", userForm);
            return mav;
        }
      // 投稿をテーブルに格納
      UserForm userInfo = userService.findByAccount(userForm.getAccount());

        //DBから取得した情報がnullの時またはアカウントが停止中の時にエラーを表示させる
      if (userInfo == null || userInfo.getIsStopped() == 1 ||!passwordEncoder.matches(userForm.getPassword(), userInfo.getPassword())) {
          //フラッシュメッセージをセット
          redirectAttributes.addFlashAttribute("errorMessageForm", "ログインに失敗しました");
          return new ModelAndView("redirect:/login");
      }
      session.setAttribute("user", userInfo);
        // rootへリダイレクト
        return new ModelAndView("redirect:/home");
    }
}
