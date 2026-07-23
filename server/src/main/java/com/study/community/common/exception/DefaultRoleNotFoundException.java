package com.study.community.common.exception;

public class DefaultRoleNotFoundException extends RuntimeException {

    public DefaultRoleNotFoundException() {
        super("默认 USER 角色未配置");
    }
}

