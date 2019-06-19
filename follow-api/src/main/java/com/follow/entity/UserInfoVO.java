package com.follow.entity;

import lombok.Data;

import java.util.Map;

@Data
public class UserInfoVO {
    private UserVo user;
    private Map<String,Object> validateInfo;
    private String type;
    private String role;
}
