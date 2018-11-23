package com.example.transaction.service;

import com.example.transaction.domain.User;

import java.util.Map;

public interface MyBatisUserService {
    public User getUser(Long id);

    public void insertUser(User user);
}
