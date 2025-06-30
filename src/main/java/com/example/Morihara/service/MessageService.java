package com.example.Morihara.service;

import com.example.Morihara.controller.Form.MessageForm;
import com.example.Morihara.controller.Form.UserForm;
import com.example.Morihara.repository.MessageRepository;
import com.example.Morihara.repository.entity.Message;
import com.example.Morihara.repository.entity.User;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public void saveMessage(MessageForm reqMessage) {
        Message saveMessage = setMessageEntity(reqMessage);
        messageRepository.save(saveMessage);
    }
    private Message setMessageEntity(MessageForm reqMessage) {
        Message message = new Message();
        message.setId(reqMessage.getId());
        message.setTitle(reqMessage.getTitle());
        message.setText(reqMessage.getText());
        message.setCategory(reqMessage.getCategory());
        message.setUserId(reqMessage.getUserId());
        message.setCreatedDate(reqMessage.getCreatedDate());
        message.setUpdatedDate(reqMessage.getUpdatedDate());
        return message;
    }
    public Page<MessageForm> findByMessages(String startDate, String endDate, String category, Pageable pageable) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String StrStartDate = "2020-01-01 00:00:00";
        String StrEndDate = "2100-12-31 23:59:59";

        if (!StringUtils.isBlank(startDate)) {
            StrStartDate = startDate + " 00:00:00";
        }
        if (!StringUtils.isBlank(endDate)) {
            StrEndDate = endDate + " 23:59:59";
        }

        Date StrDate = df.parse(StrStartDate);
        Date EndDate = df.parse(StrEndDate);

        if(StringUtils.isBlank(category)){
            Page<Message> results = messageRepository.findByCreatedDateBetweenOrderByCreatedDateDesc(StrDate, EndDate, pageable);
            return setMessageForm(results);
        } else {
            Page<Message> results = messageRepository.findByCreatedDateBetweenAndCategoryContainingOrderByCreatedDateDesc(StrDate, EndDate, category, pageable);
            return setMessageForm(results);
        }


    }
    private Page<MessageForm> setMessageForm(Page<Message> results) {
        List<MessageForm> messages = new ArrayList<>();
        //getContent()->ページコンテンツをListで返却する
        for (Message result : results.getContent()) {
            MessageForm message = new MessageForm();

            message.setId(result.getId());
            message.setTitle(result.getTitle());
            message.setText(result.getText());
            message.setCategory(result.getCategory());
            message.setUserId(result.getUserId());
            message.setCreatedDate(result.getCreatedDate());
            message.setUpdatedDate(result.getUpdatedDate());
            messages.add(message);
        }
        //getTotalPages()->総ページ数を返却する
        //getTotalElements()->全要素数を返す
        //getNumber()->現在何ページ目にいるかを返す
        //getSize()->いくつの要素をページで持つか
        return new PageImpl<>(messages, results.getPageable(), results.getTotalElements());
    }


    public void deleteMessage(Integer id){
        messageRepository.deleteById(id);
    }
}
