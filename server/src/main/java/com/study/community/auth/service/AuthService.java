package com.study.community.auth.service;

import com.study.community.auth.dto.RegisterRequest;
import com.study.community.auth.dto.RegisterResponse;
import com.study.community.common.exception.DefaultRoleNotFoundException;
import com.study.community.common.exception.DuplicateUserException;
import com.study.community.common.exception.RegistrationException;
import com.study.community.user.entity.Role;
import com.study.community.user.entity.User;
import com.study.community.user.entity.UserRole;
import com.study.community.user.mapper.RoleMapper;
import com.study.community.user.mapper.UserMapper;
import com.study.community.user.mapper.UserRoleMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class AuthService {

    private static final String DEFAULT_ROLE_CODE = "USER";
    private static final int ENABLED_STATUS = 1;

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    private final PasswordEncoder passwordEncoder;

    // 下面是 构造器注入（Constructor Injection）：Spring 创建 AuthService 时，通过这个构造器把依赖塞进来。
    // 构造器必须与类同名（Java 语法强制要求）,用来创建对象并初始化字段；逻辑上每个类都能被构造，但你不一定要自己写,不写的话构造器自动生成。
    // 无返回类型声明；调用方式特殊，只能通过 new 关键字隐式触发；构造器没有 return 语句，但会自动返回新创建的对象引用；
    public AuthService(
            UserMapper userMapper,
            RoleMapper roleMapper,
            UserRoleMapper userRoleMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        String username = request.username().trim();
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        String nickname = request.nickname().trim();

        ensureUsernameAvailable(username);
        ensureEmailAvailable(email);

        Role defaultRole = roleMapper.findEnabledByCode(DEFAULT_ROLE_CODE);
        if (defaultRole == null) {
            throw new DefaultRoleNotFoundException();
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setNickname(nickname);
        user.setStatus(ENABLED_STATUS);

        try {
            if (userMapper.insert(user) != 1) {
                throw new RegistrationException("创建用户失败");
            }
        } catch (DuplicateKeyException exception) {
            throw new DuplicateUserException("USER_ALREADY_EXISTS", "用户名或邮箱已被使用");
        }

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(defaultRole.getId());
        if (userRoleMapper.insert(userRole) != 1) {   // MyBatis 的 insert 方法默认返回 int 类型，表示受影响的行数。
            throw new RegistrationException("分配默认角色失败");
        }

        return new RegisterResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getNickname()
        );
    }

    private void ensureUsernameAvailable(String username) {
        if (userMapper.existsByUsername(username)) {
            throw new DuplicateUserException("USERNAME_ALREADY_EXISTS", "用户名已被使用");
        }
    }

    private void ensureEmailAvailable(String email) {
        if (userMapper.existsByEmail(email)) {
            throw new DuplicateUserException("EMAIL_ALREADY_EXISTS", "邮箱已被使用");
        }
    }
}

