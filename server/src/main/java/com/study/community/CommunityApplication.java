package com.study.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// SpringBootApplication  告诉 Spring Boot 这是一个主配置类，它会自动完成组件扫描、自动配置等初始化工作。main 方法：程序从这里开始运行
@SpringBootApplication
public class CommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }
}

