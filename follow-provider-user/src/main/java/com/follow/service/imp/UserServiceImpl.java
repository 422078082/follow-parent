package com.follow.service.imp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.follow.entity.User;

import com.follow.mapper.UserMapper;
import com.follow.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service

public class UserServiceImpl  implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectList(Wrapper<User> queryWrapper) {
        return userMapper.selectList(queryWrapper);
    }
}
