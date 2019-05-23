package com.follow.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.follow.entity.User;
import com.follow.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value="/insertuser",method =RequestMethod.POST)
    public boolean insertUser(@RequestBody User user){
       // User user= new User();
        user.setPassword(user.getPassword());
        user.setUsername(user.getUsername());
        return iUserService.save(user);
    }

    @RequestMapping(value="/getOneUser/{id}",method = RequestMethod.GET)
    public User getOneUser(@PathVariable String id ){
       return iUserService.getById(id);
    }

}
