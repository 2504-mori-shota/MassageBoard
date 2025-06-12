package com.example.Morihara.controller;

import com.example.Morihara.controller.Form.UserForm;
import com.example.Morihara.controller.Form.UserInfoForm;
import com.example.Morihara.repository.UserRepository;
import com.example.Morihara.repository.entity.User;
import com.example.Morihara.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserManageController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    @GetMapping("/management")
    public ModelAndView manage(
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes,
            Model model) {
        session = request.getSession();
        UserForm user = (UserForm) session.getAttribute("user");
        List<User> users = userService.findByIdWithDepartmentAndBranch(user.getId());
        User userInfoForm =  users.get(0);

        if (userInfoForm.getDepartment().getId() != 1 && userInfoForm.getDepartmentId() != 1) {
            //フラッシュメッセージをセット
            redirectAttributes.addFlashAttribute("errorMessageForm", "不正なアクセスです");
            return new ModelAndView("redirect:/home");
        }

        ModelAndView mav = new ModelAndView();
        // 投稿を全件取得
        List<UserForm> userFormList = userService.findByAllUser();
        // 画面遷移先を指定
        mav.setViewName("/management");
        // 投稿データオブジェクトを保管
        mav.addObject("users", userFormList);
        model.addAttribute("statuses", User.isStopped.values());
        return mav;
    }

    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam Integer id, @RequestParam int status){
        userService.updateStatus(id, status);
        return "redirect:/management";
    }
}
