package com.example.Morihara.controller;

import com.example.Morihara.controller.Form.UserForm;
import com.example.Morihara.repository.entity.User;
import com.example.Morihara.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class singUpController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    @GetMapping("/singUp")
    public ModelAndView newContent(
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
        UserForm userForm = new UserForm();
        mav.addObject("formModel", userForm);
        //プルダウンで使用
        mav.addObject("branchOptions", getBranchOptions());
        mav.addObject("departmentOptions", getDepartmentOptions());
        // 画面遷移先を指定
        mav.setViewName("/singUp");
        return mav;
    }

    // コントローラー内に選択肢を返すメソッド プルダウンで使用
    private Map<Integer, String> getBranchOptions() {
        Map<Integer, String> options = new LinkedHashMap<>();
        options.put(1, "本社");
        options.put(2, "A支社");
        options.put(3, "B支社");
        options.put(4, "C支社");
        return options;
    }

    private Map<Integer, String> getDepartmentOptions() {
        Map<Integer, String> options = new LinkedHashMap<>();
        options.put(1, "総務人事部");
        options.put(2, "情報管理部");
        options.put(3, "営業部");
        options.put(4, "技術部");
        return options;
    }

    /*
     * 新規投稿処理
     */
    @PostMapping("/insert")

    public String addContent(@Validated(UserForm.SingUpGroup.class)
                                   @Valid @ModelAttribute("formModel") UserForm userForm, BindingResult result,
                                   Model model
    ) throws ParseException {
        // パスワード確認チェック
        if (!result.hasFieldErrors("passwordConfirm") &&
                !userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            result.rejectValue("passwordConfirm", null, "パスワードとパスワード確認が一致しません");
        }
        // アカウント重複チェック
        if (userService.AccountDuB(userForm.getAccount())) {
            result.rejectValue("account", "duplicate", "このアカウントはすでに使用されています");
        }

        // 支社と部署の組み合わせチェック
        if (!userService.BranchDepartmentComb(userForm.getBranchId(), userForm.getDepartmentId())) {
            result.rejectValue("branchId", "mismatch","支社と部署の組み合わせが不正です");
        }

        if (result.hasErrors()) {

            model.addAttribute("branchOptions", getBranchOptions());
            model.addAttribute("departmentOptions", getDepartmentOptions());
            return "singUp"; // フォワードで遷移
        }
        // 投稿をテーブルに格納
        userService.saveUser(userForm);
        // rootへリダイレクト
        return "redirect:/management";
    }
}
