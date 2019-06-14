package com.follow.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.follow.entity.User;
import com.follow.result.*;
import com.follow.service.IUserService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@RestController
@Api("用户操作类")
@DefaultProperties(defaultFallback = "fallback")
@Slf4j
public class UserController {
    @Autowired
    private IUserService iUserService;


    /**
     * 用户登陆
     */
    @ApiOperation(value = "用户登录")
    @PostMapping(value = "login")
    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseData login(@ApiParam(name = "username",value = "用户名",required = true) @RequestParam(name = "username", required = true) String username,
                              @ApiParam(name = "password", value = "password",required = true) @RequestParam(name = "password", required = true) String password)  throws IOException {
        ResponseData  suc=null;
        try {
          suc = iUserService.login(username,password);
        } catch (Exception e) {
            log.error("查询用户失败",e);
        }
        return suc;
    }

    /**
     * 查询
     */
    @RequestMapping(value = "/selectList",method = RequestMethod.GET)
    public ResponseData selectList(){
       // SuccessResponseData success = new SuccessResponseData();
        QueryWrapper qw = new QueryWrapper();
        return ResponseData.success(iUserService.selectList(qw));
    }

    @GetMapping(value="/selectListPage")
    public ResponseData selectListPage(@RequestParam("pageNo") int pageNo ,@RequestParam("pageSize")  int pageSize){

        ResponseResult result = new ResponseResult();
        QueryWrapper qw = new QueryWrapper();
       // qw.eq("","");
        Page<User> page = new Page<>(pageNo,pageSize);
        IPage<User> pageUserList=iUserService.page(page,qw);
  /*      result.setData(pageUserList);
        result.setMsg("查询成功");
        result.setStatus(ResponseStatusCode.OK);*/
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

    public ResponseResult insertUserBody1(@RequestBody User user){
        boolean flag =iUserService.save(user);
        // ResponseResult result = new ResponseResult();
        if(flag){
            return  ResponseResult.ok("增加成功","");
        }else{
            return ResponseResult.fail(ResponseStatusCode.UNKNOW_EXCEPTION,"增加失败","",0l);
        }
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

        return new User().setId(Integer.valueOf(id)).setUsername("没有这个用户").setPassword("密码不能告诉你");
    }


    public ResponseData fallback(@ApiParam(name = "username",value = "用户名",required = true) @RequestParam(name = "username", required = true) String username,
                                 @ApiParam(name = "password", value = "password",required = true) @RequestParam(name = "password", required = true) String password) {
      // e.printStackTrace();
        return ResponseData.error("服务器繁忙，请稍后再试");
    }

}
