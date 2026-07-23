package com.study.community.auth.service;

import com.study.community.auth.dto.RegisterRequest;
import com.study.community.auth.dto.RegisterResponse;
import com.study.community.common.exception.DefaultRoleNotFoundException;
import com.study.community.common.exception.DuplicateUserException;
import com.study.community.user.entity.Role;
import com.study.community.user.entity.User;
import com.study.community.user.entity.UserRole;
import com.study.community.user.mapper.RoleMapper;
import com.study.community.user.mapper.UserMapper;
import com.study.community.user.mapper.UserRoleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private UserRoleMapper userRoleMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(userMapper, roleMapper, userRoleMapper, passwordEncoder);
    }

    @Test
    void registerHashesPasswordAndAssignsDefaultRole() {
        RegisterRequest request = new RegisterRequest(
                " alice ",
                " Alice@Example.COM ",
                "plain-password",
                " Alice "
        );
        Role defaultRole = enabledUserRole(7L);

        when(userMapper.existsByUsername("alice")).thenReturn(false);
        when(userMapper.existsByEmail("alice@example.com")).thenReturn(false);
        when(roleMapper.findEnabledByCode("USER")).thenReturn(defaultRole);
        when(passwordEncoder.encode("plain-password")).thenReturn("bcrypt-hash");
        when(userMapper.insert(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(42L);
            return 1;
        });
        when(userRoleMapper.insert(any(UserRole.class))).thenReturn(1);

        RegisterResponse response = authService.register(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userMapper).insert(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertThat(savedUser.getUsername()).isEqualTo("alice");
        assertThat(savedUser.getEmail()).isEqualTo("alice@example.com");
        assertThat(savedUser.getPasswordHash()).isEqualTo("bcrypt-hash");
        assertThat(savedUser.getPasswordHash()).isNotEqualTo(request.password());
        assertThat(savedUser.getStatus()).isEqualTo(1);

        ArgumentCaptor<UserRole> userRoleCaptor = ArgumentCaptor.forClass(UserRole.class);
        verify(userRoleMapper).insert(userRoleCaptor.capture());
        assertThat(userRoleCaptor.getValue().getUserId()).isEqualTo(42L);
        assertThat(userRoleCaptor.getValue().getRoleId()).isEqualTo(7L);

        assertThat(response.id()).isEqualTo(42L);
        assertThat(response.email()).isEqualTo("alice@example.com");
    }

    @Test
    void registerRejectsDuplicateUsernameBeforeWriting() {
        RegisterRequest request = validRequest();
        when(userMapper.existsByUsername("alice")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(DuplicateUserException.class)
                .hasMessage("用户名已被使用");

        verify(userMapper, never()).insert(any(User.class));
        verify(userRoleMapper, never()).insert(any(UserRole.class));
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void registerRejectsDuplicateEmailBeforeWriting() {
        RegisterRequest request = validRequest();
        when(userMapper.existsByUsername("alice")).thenReturn(false);
        when(userMapper.existsByEmail("alice@example.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(DuplicateUserException.class)
                .hasMessage("邮箱已被使用");

        verify(userMapper, never()).insert(any(User.class));
        verify(userRoleMapper, never()).insert(any(UserRole.class));
    }

    @Test
    void registerFailsWhenDefaultRoleIsMissing() {
        RegisterRequest request = validRequest();
        when(userMapper.existsByUsername("alice")).thenReturn(false);
        when(userMapper.existsByEmail("alice@example.com")).thenReturn(false);
        when(roleMapper.findEnabledByCode("USER")).thenReturn(null);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(DefaultRoleNotFoundException.class);

        verify(userMapper, never()).insert(any(User.class));
        verify(userRoleMapper, never()).insert(any(UserRole.class));
    }

    @Test
    void registerConvertsConcurrentUniqueConflictToBusinessError() {
        RegisterRequest request = validRequest();
        when(userMapper.existsByUsername("alice")).thenReturn(false);
        when(userMapper.existsByEmail("alice@example.com")).thenReturn(false);
        when(roleMapper.findEnabledByCode("USER")).thenReturn(enabledUserRole(7L));
        when(passwordEncoder.encode("plain-password")).thenReturn("bcrypt-hash");
        when(userMapper.insert(any(User.class)))
                .thenThrow(new DuplicateKeyException("uk_user_username"));

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(DuplicateUserException.class)
                .hasMessage("用户名或邮箱已被使用")
                .satisfies(exception -> assertThat(((DuplicateUserException) exception).getCode())
                        .isEqualTo("USER_ALREADY_EXISTS"));

        verify(userRoleMapper, never()).insert(any(UserRole.class));
    }

    private RegisterRequest validRequest() {
        return new RegisterRequest("alice", "alice@example.com", "plain-password", "Alice");
    }

    private Role enabledUserRole(Long id) {
        Role role = new Role();
        role.setId(id);
        role.setCode("USER");
        role.setName("普通用户");
        role.setStatus(1);
        return role;
    }
}
