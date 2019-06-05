package com.follow.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("w_user")
public class User implements Serializable {
    /**
     * 主键
     */

    private int id;
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
    user.setId(1).setPassword("1").setPassword("1");
}
}
