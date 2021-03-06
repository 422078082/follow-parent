package com.follow.user.api;


import com.follow.common.result.ResponseData;

import com.follow.entity.UserVo;
import com.follow.user.fallback.UserServcieFallBack;
import io.swagger.annotations.ApiParam;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microsespringcloud-user",fallback = UserServcieFallBack.class)
public interface UserServiceApi {
   @RequestMapping(value = "/selectList",method = RequestMethod.GET)
    public ResponseData selectList();
    @RequestMapping(value="/insertuser",method =RequestMethod.POST)
    public boolean insertUser(@RequestBody UserVo user);

    @RequestMapping(value="/getOneUser/{id}",method = RequestMethod.GET)
    public ResponseData getOneUser(@PathVariable("id") String id );

  @GetMapping(value="/selectListPage")
   public ResponseData selectListPage(@RequestParam("pageNo") int pageNo ,@RequestParam("pageSize")  int pageSize);


    @PostMapping(value = "login")
    public ResponseData login(@ApiParam(name = "username",value = "用户名",required = true) @RequestParam(name = "username", required = true) String username,
                              @ApiParam(name = "password", value = "password",required = true) @RequestParam(name = "password", required = true) String password);



}
