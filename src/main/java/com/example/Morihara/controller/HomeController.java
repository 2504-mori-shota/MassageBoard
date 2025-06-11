package com.example.Morihara.controller;

import com.example.Morihara.controller.Form.UserForm;
import com.example.Morihara.repository.UserRepository;
import com.example.Morihara.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
        //model.addAttribute("messages");
        //model.addAttribute("statuses", User.Status.values()); // ステータスプルダウン用
        model.addAttribute("today", LocalDate.now()); // 今日の日付も渡してあげるわよ
        return "home";
    }

   @RequestMapping("/logout")
   public String logout(HttpServletRequest request) {
       HttpSession session = request.getSession(false); // セッションを取得
       if (session != null) {
           session.invalidate(); // セッションを破棄
       }
       return "redirect:/"; // ログアウト成功後のリダイレクト
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
