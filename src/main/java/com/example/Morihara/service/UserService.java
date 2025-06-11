package com.example.Morihara.service;

import com.example.Morihara.controller.Form.UserForm;
import com.example.Morihara.repository.UserRepository;
import com.example.Morihara.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserForm findByAccountAndPassword(UserForm userForm){

        String account = userForm.getAccount();
        String password = createEndocedPwd(userForm.getPassword());
        //暗号化は後回しAdd commentMore actions
        List<User> results = userRepository.findByAccountAndPassword(account, userForm.getPassword());
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
            user.setName(result.getName());
            user.setPassword(result.getPassword());
            user.setBranchId(result.getBranchId());
            user.setDepartmentId(result.getDepartmentId());
            user.setIsStopped(result.getIsStopped());
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
    /*
     * レコード追加
     */
    public void saveUser(UserForm reqUser) {
        User saveUser = setUserEntity(reqUser);
        userRepository.save(saveUser);
    }
    /*
     * リクエストから取得した情報をEntityに設定
     */
    private User setUserEntity(UserForm reqUser) {
        User report = new User();
        report.setId(reqUser.getId());
        report.setAccount(reqUser.getAccount());
        report.setPassword(reqUser.getPassword());
        report.setName(reqUser.getName());
        report.setBranchId(reqUser.getBranchId());
        report.setDepartmentId(reqUser.getBranchId());
        report.setIsStopped(reqUser.getIsStopped());
        return report;
    }

//    private final Logger logger = LoggerFactory.getLogger(UserService.class);
//    // ユーザー名の重複をチェックするメソッド
//    public boolean isUsernameTaken(String username) {
//        // ユーザー名が重複しているかをチェック
//        logger.debug("isUsernameTaken_username:" + username); // ユーザー名をログ出力
//        Optional<User> existingUser = userRepository.findByUsername(username); // 既存のユーザーを取得
//        logger.debug("isUsernameTaken_existingUser:" + existingUser); // 既存のユーザーをログ出力
//        return existingUser.isPresent(); // 既存のユーザーが存在するかを返す
//    }

    public void deleteUser(Integer id){

        userRepository.deleteById(id);
    }
}
