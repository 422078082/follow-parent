package com.follow;

import static org.junit.Assert.assertTrue;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.follow.entity.User;
import com.follow.mapper.UserMapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest 
{
    @Autowired
    private UserMapper userMapper;



    @Test
    public void testSelectList() {
       //EntityWrapper<User> ew = new EntityWrapper<>();
        List<User> userList = userMapper.selectList(null);
        System.out.println(userList);
    }

    @Test
    public void save() {
      /*  //EntityWrapper<User> ew = new EntityWrapper<>();
        List<User> userList = userMapper.selectList(null);
        System.out.println(userList);*/
      User  user = new User();

      user.setId(1);
      user.setUsername("admin");
      user.setPassword("admin");
        userMapper.insert(user);
   }

    @Test
    public void dete(){
        QueryWrapper<User> ew = new QueryWrapper<User>();
        ew.eq("id","1");
       // EntityWrapper<User> ew = new EntityWrapper<>();
     //   EntityWrapper<User> ew = new EntityWrapper<>();
        userMapper.delete(ew);
    }


}
