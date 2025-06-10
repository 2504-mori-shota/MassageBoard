package com.example.Morihara.service;

import com.example.Morihara.controller.Form.UserForm;
import com.example.Morihara.repository.UserRepository;
import com.example.Morihara.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserForm findByAccountAndPassword(UserForm userForm){

        List<User> results = new ArrayList<>();
        String account = userForm.getAccount();
        String password = createEndocedPwd(userForm.getPassword());
        results.add((User) userRepository.findByAccountAndPassword(account, userForm.getPassword()));
        List<UserForm> users = setUserForm(results);
        return users.get(0);
    }

    private List<UserForm> setUserForm(List<User> results) {
        List<UserForm> users = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            UserForm user = new UserForm();
            User result = results.get(i);
            user.setId(result.getId());
            user.setAccount(result.getAccount());
            user.setPassword(result.getPassword());
            user.setBranchId(result.getBranchId());
            user.setDepartmentId(result.getDepartmentId());
            user.setCreatedDate(result.getCreatedDate());
            user.setUpdatedDate(result.getUpdatedDate());
            users.add(user);
        }
        return users;
    }
        private String createEndocedPwd(String pwd) {
           String encodedPwd = passwordEncoder.encode(pwd);
        return encodedPwd;
    }

    // 暗号化されたあとのpw同士を比較する
    private boolean pwdMatch(String newPwd, String originPwd) {
        if (passwordEncoder.matches(newPwd, originPwd)) {
            return true;
        }
        return false;
    }
}
