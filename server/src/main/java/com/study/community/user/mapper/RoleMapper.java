package com.study.community.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.study.community.user.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    default Role findEnabledByCode(String code) {
        return selectOne(Wrappers.<Role>lambdaQuery()
                .eq(Role::getCode, code)
                .eq(Role::getStatus, 1));
    }
}

