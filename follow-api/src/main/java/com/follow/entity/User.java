package com.follow.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
/*
用户实体类
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class User implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户登陆密码
     */
    private String    password;


public static void main(String args[]){
    User user= new User();
    user.setId(1l).setPassword("1").setPassword("1");
}
}
