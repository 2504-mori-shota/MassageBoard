package com.example.Morihara.controller;

import com.example.Morihara.controller.Form.UserForm;
import com.example.Morihara.repository.UserRepository;
import com.example.Morihara.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpSession session;

    /*
     * 投稿内容表示処理
     */

    @GetMapping("/home")
    public String showTop( @RequestParam(name = "startDate", required = false)String startDate ,
                           @RequestParam(name="endDate", required = false )String endDate,
                           Model model
    ) throws ParseException {
        ModelAndView mav = new ModelAndView();
        model.addAttribute("users");
        //model.addAttribute("statuses", User.Status.values()); // ステータスプルダウン用
        model.addAttribute("today", LocalDate.now()); // 今日の日付も渡してあげるわよ
        return "home";
    }

    @GetMapping("/logout")
    public String login() {
        return "login";
    }
    /*
     * 新規投稿画面表示
     */
    @GetMapping("/singUp")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        UserForm userForm = new UserForm();
        // 画面遷移先を指定
        mav.setViewName("/singUp");
        // 準備した空のFormを保管
        mav.addObject("formModel", userForm);
        return mav;
    }
    /*
     * 新規投稿処理
     * フォームからデータが送られていたら、バリエーションでエラーがあれば入力画面に戻す。
     * なければデータを保存してトップページにリダイレクトする流れ。
     */
    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("formModel") @Valid UserForm userForm, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.setViewName("/singUp");
            return mav;
        }
        // 投稿をテーブルに格納
        userService.saveUser(userForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/message/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        userRepository.deleteById(Math.toIntExact(id));
        return "redirect:/"; // 一覧画面に戻す
    }
//    @PostMapping("/task/updateStatus")
//    public String updateStatus(@RequestParam Long id,@RequestParam int status){
//        reportService.updateStatus(id, status);
//        return "redirect:/";
//    }
}
