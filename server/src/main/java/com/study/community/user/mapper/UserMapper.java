package com.study.community.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.study.community.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    default boolean existsByUsername(String username) {
        Long count = selectCount(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        return count != null && count > 0;
    }

    default boolean existsByEmail(String email) {
        Long count = selectCount(Wrappers.<User>lambdaQuery().eq(User::getEmail, email));
        return count != null && count > 0;
    }
}

