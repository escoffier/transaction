package com.example.transaction.service;

import com.example.transaction.domain.User;

import java.util.List;

public interface UserBatchService {

    public int insertUser(List<User> users);
}
