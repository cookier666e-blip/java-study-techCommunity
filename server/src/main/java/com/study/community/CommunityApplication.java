package com.study.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// SpringBootApplication  告诉 Spring Boot 这是一个主配置类，它会自动完成组件扫描、自动配置等初始化工作。main 方法：程序从这里开始运行
// 启动类  SpringApplication.run(...) 创建 Spring 容器，扫描到 HealthController、SecurityConfig 等，根据依赖自动创建 Tomcat、DispatcherServlet、Jackson 等 Bean，默认监听 8080（你项目在 application.yml 里写了 server.port）
@SpringBootApplication
public class CommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }
}

