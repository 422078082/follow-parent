package com.follow.service.imp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.follow.constant.code.ResponseCodeConstant;
import com.follow.constant.message.ResponseMessageConstant;
import com.follow.entity.User;

import com.follow.mapper.UserMapper;
import com.follow.result.ResponseData;
import com.follow.result.ResponseResult;
import com.follow.result.ResultJsonUtil;
import com.follow.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Slf4j
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
    public ResponseData login(String username, String password) {
        try {
                QueryWrapper qw = new QueryWrapper();
                qw.eq("username",username);
                qw.eq("password",password);
                User user = baseMapper.selectOne(qw);
                if(null == user){
                    return ResponseData.error("查询用户失败");
                }else{

                    return ResponseData.success();
                    //return ResponseResult.ok(user,"");
                }
        } catch (Exception e) {
            log.error("查询用户失败",e);
        }
        return null;
    }

}
