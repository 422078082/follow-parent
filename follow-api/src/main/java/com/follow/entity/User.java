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
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户名
     */

    @TableField(value = "username")
    private String username;
    /**
     * 用户登陆密码
     */
    @TableField(value = "password")
    private String    password;


public static void main(String args[]){
    User user= new User();
    user.setId(1l).setPassword("1").setPassword("1");
}
}
