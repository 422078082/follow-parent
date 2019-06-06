package com.follow.user.api;

import com.follow.entity.User;
import com.follow.result.ResponseResult;
import com.follow.user.fallback.UserServcieFallBack;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "MICROSESPRINGCLOUD-USER",fallbackFactory = UserServcieFallBack.class)
public interface UserServiceApi {
   @RequestMapping(value = "/selectList",method = RequestMethod.GET)
    public List<User> selectList();
    @RequestMapping(value="/insertuser",method =RequestMethod.POST)
    public boolean insertUser(@RequestBody User user);

    @RequestMapping(value="/getOneUser/{id}",method = RequestMethod.GET)
    public User getOneUser(@PathVariable("id") String id );

  @GetMapping(value="/selectListPage")
   public ResponseResult<User> selectListPage(int pageNo , int pageSize);

    @PostMapping(value = "login")
    public ResponseResult login(@ApiParam(name = "username",value = "用户名",required = true) @RequestParam(name = "username", required = true) String username,
                                @ApiParam(name = "password", value = "password",required = true) @RequestParam(name = "password", required = true) String password);


}
