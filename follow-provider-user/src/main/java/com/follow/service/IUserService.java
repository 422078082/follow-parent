package com.follow.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.follow.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface IUserService  {
    List<User> selectList(@Param("ew") Wrapper<User> queryWrapper);
}
