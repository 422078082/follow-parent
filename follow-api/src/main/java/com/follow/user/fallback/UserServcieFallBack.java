package com.follow.user.fallback;

import com.follow.entity.User;
import com.follow.result.DefaultFallBack;
import com.follow.result.ResponseResult;
import com.follow.user.api.UserServiceApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServcieFallBack implements FallbackFactory<UserServiceApi> {
    @Override
    public UserServiceApi create(Throwable throwable) {
        return new UserServiceApi(){

            @Override
            public List<User> selectList() {
                return null;
            }

            @Override
            public boolean insertUser(User user) {
                return false;
            }

            @Override
            public User getOneUser(String id) {
                return new User().setId(Long.valueOf(id)).setUsername("没有这个用户##").setPassword("密码不能告诉你");
            }

            @Override
            public ResponseResult<User> selectListPage(int pageNo, int pageSize) {
                return DefaultFallBack.defaultFallBack();
            }
        };
    }
}
