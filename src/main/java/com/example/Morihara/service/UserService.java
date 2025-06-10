package com.example.Morihara.service;


import com.example.Morihara.controller.Form.UserForm;

import com.example.Morihara.repository.UserRepository;
import com.example.Morihara.repository.entity.User;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserForm> findByUpdatedDateBetweenOrderByUpdatedDateDesc(String startDate, String endDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String StrStartDate = "2020-01-01 00:00:00";
        String StrEndDate = "2100-12-31 23:59:59";

        if (!StringUtils.isBlank(startDate)) {
            StrStartDate = startDate + " 00:00:00";
        }
        if (!StringUtils.isBlank(endDate)) {
            StrEndDate = endDate + " 23:59:59";
        }//df.format(startDate)

        Date StrDate = df.parse(StrStartDate);
        Date EndDate = df.parse(StrEndDate);

        List<User> results = userRepository.findByUpdatedDateBetweenOrderByUpdatedDateDesc(StrDate, EndDate);
        return setUserForm(results);
    }
    private List<UserForm> setUserForm(List<User> results) {
        List<UserForm> users = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            UserForm user = new UserForm();
            User result = results.get(i);
            user.setId(result.getId());
            user.setAccount(result.getAccount());
            user.setPassword(result.getPassword());
            user.setName(result.getName());
            user.setBranchId(result.getBranchId());
//            user.getDepartmentId(result.getDepartmentId());
//            user.getStoppedId(result.setStoppedId();

            user.setCreatedDate(result.getCreatedDate());
            user.setUpdatedDate(result.getUpdatedDate());
            users.add(user);
        }
        return users;
    }
}
