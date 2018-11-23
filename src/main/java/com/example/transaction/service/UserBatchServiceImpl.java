package com.example.transaction.service;

import com.example.transaction.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserBatchServiceImpl implements UserBatchService{

    @Autowired
    private MyBatisUserService myBatisUserService = null;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int insertUser(List<User> users) {
        int count = 0;

        for (User user : users){
            myBatisUserService.insertUser(user);
            count++;
        }
        return count;
    }
}
