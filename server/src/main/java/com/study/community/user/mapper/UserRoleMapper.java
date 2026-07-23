package com.study.community.user.mapper;

import com.study.community.user.entity.UserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {

    @Insert("""
            INSERT INTO `user_role` (`user_id`, `role_id`)
            VALUES (#{userId}, #{roleId})
            """)
    int insert(UserRole userRole);
}
