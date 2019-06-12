package com.follow.service.imp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.follow.entity.User;

import com.follow.mapper.UserMapper;
import com.follow.result.ResponseResult;
import com.follow.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service

public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements IUserService{

   /* @Autowired
    private UserMapper userMapper;
*/
    @Override
    public List<User> selectList(Wrapper<User> queryWrapper) {
       // EntityWrapper<User> ew = new EntityWrapper<>();
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public ResponseResult login(String username, String password) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("username",username);
        qw.eq("password",password);
        User user = baseMapper.selectOne(qw);
        if(null == user){
                return ResponseResult.ok("没有些用户","");
        }else{
            return ResponseResult.ok("查询成功",user,"");
            //return ResponseResult.ok(user,"");
        }
       // return null;
    }

}
