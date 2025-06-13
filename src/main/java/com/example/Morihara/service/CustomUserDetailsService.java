package com.example.Morihara.service;

import com.example.Morihara.repository.UserRepository;
import com.example.Morihara.repository.entity.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // DB からユーザー取得
        List<User> users = userRepository.findByAccount(account)
               /* .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません"))*/;
        User user = users.get(0);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getAccount())
                .password(user.getPassword()) // ← ハッシュ済みパスワード
                .roles("USER") // 必要に応じて変更
                .build();
    }

}
