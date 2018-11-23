package com.example.transaction.service;

import com.example.transaction.domain.User;
import com.example.transaction.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class MyBatisUserServiceImpl implements MyBatisUserService {

    @Autowired
    private UserMapper userMapper = null;

    @Override
    public User getUser(Long id) {
        return userMapper.getUser(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 1, propagation = Propagation.REQUIRES_NEW)
    public void insertUser(User user) {
        userMapper.insertUser(user);
//        Map<String,  User> result = new HashMap<>();
//        result.put("user", user);
//        return result;
    }
}
