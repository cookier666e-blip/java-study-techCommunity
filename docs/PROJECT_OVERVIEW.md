# 技术社区项目总概述

## 项目目标

本项目是一个类似掘金/CSDN 的教学版技术社区。通过十天、每天 5~6 小时的项目实践，完成从前端开发向 Java 后端开发的第一轮转型训练。

学习目标：

- 能独立完成一个前后端分离的 Spring Boot 项目。
- 理解常见企业后端的分层、认证、权限、缓存和数据一致性设计。
- 能围绕项目功能回答 Java 后端校招常见问题。
- 为后续 AI 应用后端、Elasticsearch、RabbitMQ 和微服务学习建立基础。

## 学习方式

每项任务使用同一流程：

```text
目标功能
  -> 分析所需技术
  -> 学习必要知识
  -> 设计数据与接口
  -> 完成代码
  -> 测试与 Review
  -> 总结原理
  -> 整理面试表达
  -> 更新学习文档与开发总结
  -> 用户 Review 当前代码批次
  -> 用户确认后进入下一个功能点
```

每天会拆成多个可独立验收的功能点。每个功能点使用当天文档中的专属代码生成提示词；一次只执行一个提示词，生成代码后立即停止，等待用户 Review。一天内所有功能点均确认后，才执行当天 Git commit。

每天约 5~6 小时，其中项目开发 60%、技术学习 20%、原理理解 15%、面试总结 5%。

## 技术基线

后端：Java 17、Spring Boot 3、Maven、Spring MVC、MyBatis-Plus、MySQL、Redis、Spring Security、JWT、Swagger/OpenAPI、Docker。

前端：Vue 3、TypeScript、Vite、Pinia、Vue Router、Axios、Element Plus。

测试与工具：JUnit 5、Mockito、MockMvc、Postman/Apifox、JMeter 或 k6、Git。

说明：本项目以 `backend-learning-planner.md` 为最终规划基线，因此持久层采用 MyBatis-Plus，不同时引入 Spring Data JPA；Java 版本使用 17。

## 系统范围

当前十天主线包含：

- 用户注册、登录、个人信息。
- JWT 登录认证和 `USER/ADMIN` 权限管理。
- 文章草稿、发布、编辑、删除、标签和搜索。
- 两级评论。
- 点赞、收藏及幂等控制。
- 站内消息通知和未读状态。
- Redis 文章缓存与热门列表。
- 基础压测、索引优化、限流和 Docker 启动。

暂不包含：Elasticsearch、RabbitMQ、短信/邮件通知、无限层级评论、微服务拆分和生产级分布式部署。

## 总体架构

```text
Vue 3
  -> Axios / REST API
  -> Spring Security / JWT
  -> Controller
  -> Service / Transaction
  -> Mapper / MyBatis-Plus
  -> MySQL
  -> Redis Cache
```

后端建议按业务模块组织，每个模块内部包含 Controller、Service、Mapper、Entity/DO 和 DTO；公共模块承载统一响应、异常、安全和配置。

## 核心模块

| 模块 | 主要职责 | 计划日 |
|---|---|---|
| 工程基础 | 项目初始化、配置、统一规范、数据库设计 | Day 1 |
| 用户认证 | 注册、登录、BCrypt、JWT | Day 2~3 |
| 权限管理 | 当前用户、角色、资源所有权 | Day 3 |
| 文章 | 草稿、发布、编辑、详情 | Day 4 |
| 内容发现 | 分页、标签、排序、搜索 | Day 5 |
| 评论 | 两级评论、回复、删除权限 | Day 6 |
| 互动 | 点赞、收藏、幂等和计数 | Day 7 |
| 通知 | 站内通知、未读状态 | Day 8 |
| 缓存 | 文章详情、热门列表、缓存一致性 | Day 9 |
| 性能与交付 | 压测、优化、限流、Docker、文档 | Day 10 |

## 文档体系

- `backend-learning-planner.md`：学习方法和每日任务生成依据。
- `AGENTS.md`：协作、编码、验证、确认和文档规则。
- `docs/PROJECT_OVERVIEW.md`：项目范围、架构和总体进度。
- `docs/DEVELOPMENT_SUMMARY.md`：每次代码变更的持续总结。
- `docs/learning/day-XX.md`：每天的学习任务和完成记录。
- 每份 Day 文档中的“代码生成提示词”：按功能点驱动代码生成和逐次 Review。

## 当前状态

- 当前阶段：Day 2 功能点 1 待用户 Review。
- 已完成代码：Day 1 工程骨架和前后端联调，以及用户持久化、注册接口、BCrypt 密码保存、默认角色分配和统一异常处理。
- 当前数据模型：`user` 与 `role` 通过 `user_role` 建立多对多关系；用户名、邮箱、角色编码和用户角色组合均由数据库保证唯一。
- 当前前端链路：Vue 页面通过集中式 Axios API 模块请求 `/api/health`，开发环境由 Vite 将请求代理到 Spring Boot 8080。
- 当前注册链路：`POST /api/auth/register -> DTO 校验 -> AuthService 事务 -> User/Role/UserRole Mapper -> MySQL`。
- 当前任务：Day 2 功能点 1 待用户 Review。
- 下一验收点：功能点 1 经用户确认后，才能开始功能点 2 的账号登录与 JWT 签发。
