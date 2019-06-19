package com.follow.user.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.follow.common.result.ResponseData;
import com.follow.entity.UserVo;
import com.follow.user.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface IUserService extends IService<User> {
    ResponseData selectList(@Param("ew") Wrapper<User> queryWrapper);
   ResponseData login(String username, String password);

}
