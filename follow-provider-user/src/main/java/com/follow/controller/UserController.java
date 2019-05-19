package com.follow.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.follow.entity.User;
import com.follow.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private IUserService iUserService;

    /**
     * 查询
     */
    @RequestMapping(value = "/selectList",method = RequestMethod.GET)
    public List<User> selectList(){
        QueryWrapper qw = new QueryWrapper();
        return iUserService.selectList(qw);
    }
    @RequestMapping(value="insertuser/{username}/{password}",method =RequestMethod.GET)
    public void insertUser(@PathVariable String username,@PathVariable String password){
        User user= new User();
        user.setPassword(password);
        user.setUsername(username);
        iUserService.save(user);
    }

}
