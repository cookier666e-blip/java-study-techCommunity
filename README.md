# java-study-techCommunity
这是一个用于学习 Java 后端开发的技术社区项目，目标是通过可运行功能逐步达到企业项目开发和校招面试要求。
项目放在 D:\code\studyLyt\java_study
使用codex学习

# 项目
8080：Spring Boot 后端服务端口，访问健康检查和 Swagger，例如 http://localhost:8080。
3000：通常用于前端开发服务器，例如 React、Vue 的 Vite 开发服务。
3306：MySQL 数据库默认端口，后端通过它连接 MySQL。

 目录：
com.study.community
├── auth/      ← 认证流程（注册、以后登录/JWT）
├── user/      ← 用户领域数据（用户、角色、关系）
├── common/    ← 跨模块共用能力
└── CommunityApplication.java
