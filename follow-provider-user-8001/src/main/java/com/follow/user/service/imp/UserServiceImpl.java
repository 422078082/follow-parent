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

                String token = UserUtil.saveUserInfo(user, OriginEnum.fromValue("3"), redisUtil,
                        "");

                return ResponseData.success(token);


    }

}
