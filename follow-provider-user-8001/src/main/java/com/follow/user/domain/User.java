package com.follow.user.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("w_user")
public class User {
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
}
