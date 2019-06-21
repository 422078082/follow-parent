package com.follow.user.service.imp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.follow.common.util.EncryptedUtil;


import com.follow.common.util.RedisUtil;
import com.follow.entity.UserVo;
import com.follow.user.domain.User;
import com.follow.user.enums.OriginEnum;
import com.follow.user.enums.UserErrorEnum;
import com.follow.user.mapper.UserMapper;
import com.follow.common.result.ResponseData;
import com.follow.user.service.IUserService;
import com.follow.user.util.UserUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
@Slf4j
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements IUserService{
    @Autowired
    private RedisUtil redisUtil;
   /* @Autowired
    private UserMapper userMapper;
*/
    @Override
    public ResponseData selectList(Wrapper<User> queryWrapper) {
       // EntityWrapper<User> ew = new EntityWrapper<>();
        List<User> user =  baseMapper.selectList(queryWrapper);
        return ResponseData.success(user);
    }

    @Override
    public ResponseData login(String username, String password)  {
        System.setProperty("java.net.preferIPv4Stack", "true");
                QueryWrapper qw = new QueryWrapper();
                qw.eq("username",username);

                User user = baseMapper.selectOne(qw);
                System.out.println("*****************="+user);
                System.out.println(user.getUsername()+"****"+user.getPassword());
                if(user == null){
                    System.out.println(user.getUsername()+"****1"+user.getPassword());
                   return ResponseData.error(UserErrorEnum.该手机号码未注册);
                }

                if (!EncryptedUtil.verify(password, user.getPassword())) {
                    System.out.println(user.getUsername()+"****2"+user.getPassword());
                    return ResponseData.error(UserErrorEnum.密码不正确);
                }
            //**
        // * UserPasswordLoginVO使用这个类
        System.out.println("*******$$$$$="+OriginEnum.fromValue("3"));
       // System.out.println("*******$$$$$="+OriginEnum.fromValue("3"));
       // redisUtil.set("my","123456");

        String token = UserUtil.saveUserInfo(user, OriginEnum.fromValue("3"), redisUtil,
                        "127.0.0.1");
                System.out.println(token);
                return ResponseData.success(token);


    }
    public ResponseData fallback(String username,String password,Throwable e) {
        e.printStackTrace();
        return ResponseData.error("服务器繁忙，请稍后再试");
    }
}
