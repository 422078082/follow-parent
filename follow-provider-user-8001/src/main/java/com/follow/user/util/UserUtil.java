package com.follow.user.util;

import com.alibaba.fastjson.JSONObject;
import com.follow.common.jwt.JwtTokenUtil;
import com.follow.common.util.RedisUtil;

import com.follow.entity.UserInfoVO;
import com.follow.entity.UserVo;
import com.follow.user.domain.User;
import com.follow.user.enums.OriginEnum;
import com.follow.user.enums.UserTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Component
public class UserUtil {

    private static ThreadLocal<UserInfoVO> userInfo = new InheritableThreadLocal<>();

    public static UserInfoVO getCurrentUser(){
        return userInfo.get();
    }

    public static void setCurrentUser(UserInfoVO user){
        userInfo.set(user);
    }

    public static String saveUserInfo(User user, OriginEnum originEnum, RedisUtil redisUtil, String ip) {
        UserVo userVO = new UserVo();
        BeanUtils.copyProperties(user, userVO);
        String token = JwtTokenUtil.generateToken(String.valueOf(user.getId()), new HashMap<>());
        System.out.println("redis 测试-----:"+String.valueOf(user.getId()));
        String userStr = redisUtil.get(String.valueOf(user.getId()));
        System.out.println("redis 测试-----121:"+String.valueOf(user.getId()));
        if (StringUtils.isBlank(userStr)) {
            UserInfoVO userInfoVO = new UserInfoVO();
            userInfoVO.setUser(userVO);
            Map<String,Object> map = new HashMap<>();
            map.put(originEnum.toValue(), token);
            userInfoVO.setValidateInfo(map);
            userInfoVO.setType("user");
            userInfoVO.setRole("1120873544352403458");
            redisUtil.set(String.valueOf(user.getId()), JSONObject.toJSONString(userInfoVO),90,TimeUnit.DAYS);
            //设置全局用户信息
            //UserInfo.userId = userInfoVO.getUser().getUid().toString();
           // UserInfo.userType = userInfoVO.getType();
            //UserInfo.ip = ip;
        } else {
            UserInfoVO userInfoVO = JSONObject.parseObject(userStr,UserInfoVO.class);
            userInfoVO.setUser(userVO);
            Map<String,Object> map = userInfoVO.getValidateInfo();
            map.put(originEnum.toValue(), token);
            userInfoVO.setValidateInfo(map);
            userInfoVO.setType(UserTypeEnum.普通用户.toValue());
            userInfoVO.setRole("1120873544352403458");
            redisUtil.set(String.valueOf(user.getId()), JSONObject.toJSONString(userInfoVO),90, TimeUnit.DAYS);
            //设置全局用户信息
          //  UserInfo.userId = userInfoVO.getUser().getUid().toString();
           // UserInfo.userType = userInfoVO.getType();
           // UserInfo.ip = ip;
        }
        System.out.println("测试token-------:"+token);
        return token;
    }
}
