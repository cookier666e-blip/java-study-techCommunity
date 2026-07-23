package com.study.community.auth.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT}) // 限定注解只能标注在 字段、方法参数、Record 组件 上。
@Retention(RetentionPolicy.RUNTIME) //注解在运行时保留，可以通过反射获取。 若未声明此注解，注解默认仅保留到 CLASS 阶段（编译后字节码中存在，但 JVM 运行时丢弃），导致验证逻辑失效。
@Constraint(validatedBy = BCryptCompatibleValidator.class) // 指定验证器。
public @interface BCryptCompatible {

    String message() default "密码的 UTF-8 编码不能超过 72 字节";

    // Java 自定义注解中定义属性的标准语法，
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

