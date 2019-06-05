package com.follow.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.follow.entity.User;
import com.follow.result.ResponseResult;
import com.follow.result.ResponseStatusCode;
import com.follow.service.IUserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @GetMapping(value="/selectListPage")
    public ResponseResult<User> selectListPage(int pageNo , int pageSize){

        ResponseResult result = new ResponseResult();
        QueryWrapper qw = new QueryWrapper();
       // qw.eq("","");
        Page<User> page = new Page<>(pageNo,pageSize);
        IPage<User> pageUserList=iUserService.page(page,qw);
        result.setData(pageUserList);
        result.setMsg("查询成功");
        result.setStatus(ResponseStatusCode.OK);
       // result.s
        return result;
    }
    @RequestMapping(value="/insertuser",method =RequestMethod.POST)
    public boolean insertUser(@RequestBody User user){
       // User user= new User();
        user.setPassword(user.getPassword());
        user.setUsername(user.getUsername());
        return iUserService.save(user);
    }

    @RequestMapping(value="/getOneUser/{id}",method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "get_Message")
    public User getOneUser(@PathVariable String id ){

        User user =iUserService.getById(id);
        if(null ==user){
                throw new RuntimeException("当前id："+id+"没有对应的信息");
        }

        return iUserService.getById(id);

    }

    public User get_Message(@PathVariable("id") String id){

        return new User().setId(Long.valueOf(id)).setUsername("没有这个用户").setPassword("密码不能告诉你");
    }

}
