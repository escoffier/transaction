package com.example.transaction.controller;

import com.example.transaction.domain.User;
import com.example.transaction.service.UserBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.transaction.service.MyBatisUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private MyBatisUserService myBatisUserService = null;


    @Autowired
    private UserBatchService userBatchService = null;

    @GetMapping()
    @ResponseBody
    public User getUser(Long id) {
        System.out.println("getUser: " + id);
        return myBatisUserService.getUser(id);
    }

    @PostMapping("/")
    @ResponseBody
    public Map<String, User> insertUser(@RequestBody User user) {
        myBatisUserService.insertUser(user);

        Map<String, User> result = new HashMap<>();

        result.put("user", user);

        return result;
    }

    @PostMapping("/multi")
    @ResponseBody
    public List<User> insertUsers(@RequestBody List<User> users ){

        //List<User> users = (List<User>)(userMap.get("users"));
        for (User u : users) {
            System.out.println("------- id: "+ u.getId());
        }

        userBatchService.insertUser(users);
        return users;
    }
}
