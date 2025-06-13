package com.example.Morihara.controller;

import com.example.Morihara.controller.Form.CommentForm;
import com.example.Morihara.controller.Form.MessageForm;
import com.example.Morihara.controller.Form.UserForm;
import com.example.Morihara.repository.MessageRepository;
import com.example.Morihara.repository.UserRepository;
import com.example.Morihara.repository.entity.User;
import com.example.Morihara.service.CommentService;
import com.example.Morihara.service.CustomUserDetailsService;
import com.example.Morihara.service.MessageService;
import com.example.Morihara.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
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
    MessageRepository messageRepository;

    @Autowired
    MessageService messageService;

    @Autowired
    CommentService commentService;

    @Autowired
    HttpSession session;

    /*
     * 投稿内容表示処理
     */

    @GetMapping("/home")
    public ModelAndView showTop(
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate,
            Principal principal,
            Model model) {
        ModelAndView mav = new ModelAndView();

        String account = principal.getName() ;

        UserForm user = userService.findByAccount(account);

        List<MessageForm> messageList = messageService.findAllMessages();  // 変数名を統一

        for (MessageForm message : messageList) {
            List<CommentForm> comments = commentService.findCommentsByMessageId(message.getId());
            message.setComments(comments);
        }

        session.setAttribute("user", user);
        mav.addObject("user", user);
        mav.setViewName("/home");
        mav.addObject("messages", messageList);
        // コメントを別に渡す必要はないです。messagesに含まれているので不要
        return mav;
    }

   @RequestMapping("/logout")
   public String logout(HttpServletRequest request) {
       HttpSession session = request.getSession(false); // セッションを取得
       if (session != null) {
           session.invalidate(); // セッションを破棄
       }
       return "redirect:/"; // ログアウト成功後のリダイレクト
   }

    @DeleteMapping("/message/delete/{id}")
    public ModelAndView deleteMessage(@PathVariable("id") int id) {
        //投稿をテーブルに格納
        messageService.deleteMessage(id);
        //rootへリダイレクト
        return new ModelAndView("redirect:/home");
    }

//    @PostMapping("/task/updateStatus")
//    public String updateStatus(@RequestParam Long id,@RequestParam int status){
//        reportService.updateStatus(id, status);
//        return "redirect:/";
//    }
}
