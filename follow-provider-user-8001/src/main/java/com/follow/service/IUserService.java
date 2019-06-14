package com.follow.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.follow.entity.User;
import com.follow.result.ResponseData;
import com.follow.result.ResponseResult;
import com.follow.result.ResultJsonUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface IUserService extends IService<User> {
    List<User> selectList(@Param("ew") Wrapper<User> queryWrapper);
    ResponseData login(String username, String password);

}
