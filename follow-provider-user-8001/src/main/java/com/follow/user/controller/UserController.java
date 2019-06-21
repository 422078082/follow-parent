package com.follow.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.follow.common.result.*;
import com.follow.entity.UserVo;
import com.follow.user.domain.User;
import com.follow.user.service.IUserService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("用户操作类")
@RestController
@DefaultProperties(defaultFallback = "fallback")
@Slf4j
public class UserController {
    @Autowired
    private IUserService iUserService;



    @ApiOperation(value = "用户登录")
    @PostMapping(value = "login")
    @HystrixCommand
    public ResponseData login(@ApiParam(name = "username",value = "用户名",required = true) @RequestParam(name = "username", required = true) String username,
                              @ApiParam(name = "password", value = "password",required = true) @RequestParam(name = "password", required = true) String password)  {

        return iUserService.login(username,password);
    }


    @RequestMapping(value = "/selectList",method = RequestMethod.GET)
    public ResponseData selectList(){
       // SuccessResponseData success = new SuccessResponseData();
        QueryWrapper qw = new QueryWrapper();
        return iUserService.selectList(qw);
    }

    @GetMapping(value="/selectListPage")
    public ResponseData selectListPage(@RequestParam("pageNo") int pageNo ,@RequestParam("pageSize")  int pageSize){

        ResponseResult result = new ResponseResult();
        QueryWrapper qw = new QueryWrapper();
       // qw.eq("","");
        Page<User> page = new Page<>(pageNo,pageSize);
        IPage<User> pageUserList=iUserService.page(page,qw);
        result.setData(pageUserList);
        result.setMsg("查询成功");
        result.setStatus(ResponseStatusCode.OK);
       // result.s
        return ResponseData.success(pageUserList);
    }
    @ApiOperation(value = "插入用户信息",notes = "将用户名和用户的密码信息插入到数据库中")
    @RequestMapping(value="/insertuser",method =RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true,  dataType = "String")
    })
    public ResponseResult insertUser(@ApiParam(name = "username", value = "username",required = true) @RequestParam(name = "username", required = true) String username,
                              @ApiParam(name = "password", value = "password",required = true) @RequestParam(name = "password", required = true) String password
                              ){
        System.out.println("用户名*********："+username);
        System.out.println("密码**********："+password);
        User user= new User();
       // user.setId(3);
        user.setPassword(password);
        user.setUsername(username);
        boolean flag =iUserService.save(user);
       // ResponseResult result = new ResponseResult();
        if(flag){
            return  ResponseResult.ok("增加成功","");
        }else{
            return ResponseResult.fail(ResponseStatusCode.UNKNOW_EXCEPTION,"增加失败","",0l);
        }

       // return result;
    }

    @ApiOperation(value = "插入用户信息",notes = "将用户名和用户的密码信息插入到数据库中")
    @RequestMapping(value="/insertUserBody",method =RequestMethod.POST)

    public ResponseResult insertUserBody(@ApiParam(name = "model", value = "用户信息Model",required = true) User user){
        System.out.println("用户名*********："+user.getUsername());
        System.out.println("密码**********："+user.getPassword());
        boolean flag =iUserService.save(user);
        // ResponseResult result = new ResponseResult();
        if(flag){
            return  ResponseResult.ok("增加成功","");
        }else{
            return ResponseResult.fail(ResponseStatusCode.UNKNOW_EXCEPTION,"增加失败","",0l);
        }
       // return iUserService.save(user);
    }

    @ApiOperation(value = "插入用户信息",notes = "将用户名和用户的密码信息插入到数据库中")
    @RequestMapping(value="/insertUserBody1",method =RequestMethod.POST)

    public ResponseResult insertUserBody1(@RequestBody UserVo userVo){
        User user = new User();
        BeanUtils.copyProperties(userVo,user);
        boolean flag =iUserService.save(user);
        // ResponseResult result = new ResponseResult();
        if(flag){
            return  ResponseResult.ok("增加成功","");
        }else{
            return ResponseResult.fail(ResponseStatusCode.UNKNOW_EXCEPTION,"增加失败","",0l);
        }
    }

    @RequestMapping(value="/getOneUser/{id}",method = RequestMethod.GET)
    @HystrixCommand
    public ResponseData getOneUser(@PathVariable String id ){

        User user =iUserService.getById(id);


        return ResponseData.success();

    }




    public ResponseData fallback(Throwable e) {
        e.printStackTrace();
        return ResponseData.error("服务器繁忙，请稍后再试");
    }

}
