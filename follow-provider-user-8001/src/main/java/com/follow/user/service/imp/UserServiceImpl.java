package com.follow.user.service.imp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.follow.entity.User;

import com.follow.user.mapper.UserMapper;
import com.follow.common.result.ResponseData;
import com.follow.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    public ResponseData login(String username, String password)  {

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



    }

}
