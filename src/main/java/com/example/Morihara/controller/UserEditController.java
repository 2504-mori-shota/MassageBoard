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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class UserEditController {
    @Autowired
    UserService userService;

    @PostMapping("/userEdit")
    public ModelAndView newContent(@RequestParam("id") String strId) {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
       UserForm user = userService.findById(Integer.parseInt(strId));
        // 画面遷移先を指定
        mav.setViewName("/userEdit");
        // 準備した空のFormを保管
        mav.addObject("formModel", user);
        mav.addObject("branchOptions", getBranchOptions());
        mav.addObject("departmentOptions", getDepartmentOptions());
        // mav.addObject("errorMessageForm", errorMessages);
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

    @PostMapping("/update")
    public String updateUser(
            @Validated(UserForm.EditGroup.class)
            @Valid @ModelAttribute("formModel") UserForm userForm,
            BindingResult result,
            Model model){
        // パスワード確認チェック
        if (!result.hasFieldErrors("passwordConfirm") &&
                !userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            result.rejectValue("passwordConfirm", null, "パスワードとパスワード確認が一致しません");
        }


        // 支社と部署の組み合わせチェック
        if (!userService.BranchDepartmentComb(userForm.getBranchId(), userForm.getDepartmentId())) {
            result.rejectValue("branchId", "mismatch","支社と部署の組み合わせが不正です");
        }

        if (result.hasErrors()) {
            model.addAttribute("branchOptions", getBranchOptions());
            model.addAttribute("departmentOptions", getDepartmentOptions());
            return "userEdit"; // フォワードで遷移
        }
        userService.saveUser(userForm);
        return "redirect:/management";
    }
}
