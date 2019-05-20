package com.follow.controller;


import com.follow.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class UserComsumerController {

    private static final String Rest_Url_Prefix="http://localhost:8001";

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/comsumer/selectList",method = RequestMethod.GET)
    public List<User> selectList(){
       // QueryWrapper qw = new QueryWrapper();
        return restTemplate.getForObject(Rest_Url_Prefix+"/selectList",List.class);
    }
    @RequestMapping(value="/comsumer/insertuser",method =RequestMethod.GET)
    public boolean insertUser(@RequestBody User user){
    return restTemplate.postForObject(Rest_Url_Prefix+"/insertuser/",user, Boolean.class);
    }

    @RequestMapping(value="/comsumer/getOneUser/{id}",method = RequestMethod.GET)
    public User getOneUser(@PathVariable String id ){
        return restTemplate.getForObject(Rest_Url_Prefix+"/getOneUser/"+id,User.class);
    }

}
