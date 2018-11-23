package com.example.transaction.mapper;

import com.example.transaction.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public User getUser(Long id);

    public void insertUser(User user);
}
