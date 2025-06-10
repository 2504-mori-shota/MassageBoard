package com.example.Morihara.controller;

import com.example.Morihara.controller.Form.UserForm;
import com.example.Morihara.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UserService userService;

    /*
     * 投稿内容表示処理
     */
    @GetMapping("/home")
    public String showTop( @RequestParam(name = "startDate", required = false)String startDate ,
                           @RequestParam(name="endDate", required = false )String endDate,
                           Model model
    ) throws ParseException {
        ModelAndView mav = new ModelAndView();
        List<UserForm> users = userService.findByUpdatedDateBetweenOrderByUpdatedDateDesc(startDate , endDate);
        model.addAttribute("users", users);
        //model.addAttribute("statuses", User.Status.values()); // ステータスプルダウン用
        model.addAttribute("today", LocalDate.now()); // 今日の日付も渡してあげるわよ
        return "home";
    }
//    @GetMapping("/task/delete/{id}")
//    public String deleteTask(@PathVariable("id") Long id) {
//        reportRepository.deleteById(id);
//        return "redirect:/"; // 一覧画面に戻す
//    }
//    @PostMapping("/task/updateStatus")
//    public String updateStatus(@RequestParam Long id,@RequestParam int status){
//        reportService.updateStatus(id, status);
//        return "redirect:/";
//    }
}
