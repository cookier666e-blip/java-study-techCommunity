# Day 1：工程初始化与用户模块准备

- 状态：进行中（功能点 1 待用户 Review）
- 预计时间：5 小时 40 分钟
- 前置条件：安装 Java 17、Maven、MySQL、Node.js 和 IDE
- 用户确认：功能点 1 待 Review；功能点 2、3 未开始

## 功能点进度

| 功能点 | 状态 | Review 结论 |
|---|---|---|
| 1. 后端工程与健康检查 | 待用户 Review | 待填写 |
| 2. 用户表设计与数据库脚本 | 未开始 | - |
| 3. Vue 工程与前后端连通 | 未开始 | - |

## 今日目标

完成 Spring Boot 与 Vue 项目初始化，建立可运行的前后端骨架、基础分层、开发配置和用户表设计，为 Day 2 注册登录开发做好准备。今天不实现完整注册登录。

## 时间安排

- 项目开发：3 小时 20 分钟，完成工程、配置、数据库脚本和前后端连通。
- 技术学习：1 小时 10 分钟，理解 Spring Boot、Maven、Spring MVC 和 MyBatis-Plus 的职责。
- 原理理解：50 分钟，梳理启动流程、请求链路和依赖注入。
- 面试总结：20 分钟，整理 3 个基础问题。

## 今日学习知识

- Maven 的 `pom.xml`、依赖、插件和标准目录结构。
- Spring Boot 自动配置、Bean 和构造器注入。
- Spring MVC 请求从 Controller 到响应的基本流程。
- MyBatis-Plus 中 Entity、Mapper 和数据库表的关系。
- Vue 3、Vite、TypeScript 工程结构和 Axios 基础。

## 今日开发任务

1. 检查 Java、Maven、MySQL、Node.js 版本并记录结果。
2. 创建 `server` 后端工程，加入 Web、Validation、Security、MySQL、MyBatis-Plus、Swagger 和测试依赖。
3. 创建 `web` Vue 3 + TypeScript 工程，加入 Router、Pinia、Axios 和 Element Plus。
4. 配置 `application.yml` 和开发环境配置，敏感值从环境变量读取。
5. 建立 `Controller -> Service -> Mapper -> Database` 包结构和公共模块目录。
6. 设计 `user`、`role`、`user_role` 表，明确主键、唯一约束、状态和审计字段。
7. 编写版本化用户表 SQL 脚本，但今天不实现用户业务代码。
8. 实现最小健康检查接口，并从 Vue 页面请求该接口。
9. 启动 Swagger UI，确认后端接口文档可访问。
10. 完成自检后更新 `docs/DEVELOPMENT_SUMMARY.md`，提交用户 Review。

## 文件创建计划

| 文件或目录 | 作用 |
|---|---|
| `server/pom.xml` | 后端依赖和构建配置 |
| `server/src/main/java/com/study/community/CommunityApplication.java` | Spring Boot 启动类 |
| `server/src/main/java/com/study/community/common/controller/HealthController.java` | 最小连通性接口 |
| `server/src/main/resources/application.yml` | 公共配置和环境变量占位 |
| `server/src/main/resources/application-dev.yml` | 本地开发配置 |
| `server/src/main/resources/db/schema/V001__create_user_tables.sql` | 用户与角色表结构 |
| `web/src/router/index.ts` | Vue 路由 |
| `web/src/api/http.ts` | Axios 实例 |
| `web/src/views/HomeView.vue` | 健康检查展示页 |

## 代码生成提示词

使用顺序：功能点 1 Review 通过后才能使用功能点 2，功能点 2 Review 通过后才能使用功能点 3。

### 功能点 1：后端工程与健康检查

```text
使用 $spring-vue-learning-coach，执行 Day 1 功能点 1。先检查仓库，只创建 Java 17 + Spring Boot 3 + Maven 后端骨架，配置 Spring MVC、Validation、MyBatis-Plus、MySQL、Spring Security、Swagger 和测试依赖，实现最小健康检查接口并确保 Swagger 可访问。暂不创建用户业务类、数据库表和 Vue 代码。开始编码前说明依赖作用、目录设计、修改文件和验收标准；完成后运行后端测试或启动验证，更新 Day 1 文档和 DEVELOPMENT_SUMMARY.md，给出学习建议，将本批次标记为“待用户 Review”，然后停止，不执行功能点 2，也不提交 Git。
```

### 功能点 2：用户表设计与数据库脚本

```text
使用 $spring-vue-learning-coach，执行 Day 1 功能点 2。前提是功能点 1 已由我确认。只设计 user、role、user_role 三张表，说明字段、主键、唯一约束、索引、状态和审计字段，并生成版本化 MySQL SQL 脚本；可以补充必要配置，但不要生成 User Entity、Mapper、注册登录接口或 Vue 页面。完成后验证 SQL 语法和表关系，更新 Day 1 文档和 DEVELOPMENT_SUMMARY.md，给出数据库设计学习建议，标记“待用户 Review”并停止，不执行功能点 3。
```

### 功能点 3：Vue 工程与前后端连通

```text
使用 $spring-vue-learning-coach，执行 Day 1 功能点 3。前提是功能点 2 已由我确认。只创建 Vue 3 + TypeScript + Vite 前端骨架，配置 Router、Pinia、Axios、Element Plus、开发环境 API 地址和错误处理，在首页调用后端健康检查接口。不要实现注册登录页面。完成后运行前端构建或类型检查，并验证前后端连通；更新 Day 1 文档和 DEVELOPMENT_SUMMARY.md，给出 Vue 与后端联调学习建议，标记“待用户 Review”并停止。等待我确认后再考虑当天 Git commit。
```

## 功能点 1 执行记录

- 执行日期：2026-07-20
- 状态：待用户 Review
- 完成内容：创建 Java 17、Spring Boot 3.4.5、Maven 后端骨架；配置 Spring MVC、Validation、MyBatis-Plus、MySQL、Spring Security、Swagger 和测试依赖；实现健康检查接口。
- 接口：`GET /api/health`
- Swagger：`/swagger-ui/index.html`，OpenAPI JSON 为 `/v3/api-docs`。
- 测试：`mvn -B clean test` 通过，3 个集成测试全部成功。
- 运行验收：健康检查返回 `UP`，OpenAPI `3.1.0`，Swagger UI 返回 HTTP 200。
- 当前限制：尚未创建数据库表、Mapper、用户业务和 Vue；MyBatis-Plus 启动时提示未发现 Mapper，符合当前功能点范围。
- Review 重点：依赖版本与范围、临时 Security 全放行策略、健康检查 DTO、配置中的环境变量、测试覆盖。

## 原理学习

1. Spring Boot 为什么能通过启动类和依赖自动配置 Web 应用。
2. IOC 容器为什么比手动 `new` 更适合管理企业项目对象。
3. 一次 HTTP 请求如何经过 DispatcherServlet、Controller 并返回 JSON。

## 面试问题

问题：Spring Boot 和 Spring Framework 的关系是什么？  
回答：Spring Boot 基于 Spring，通过自动配置、Starter 和内嵌服务器降低配置成本；核心 IOC、AOP 等能力仍来自 Spring。  
追问：自动配置是否意味着不能自定义配置？

问题：为什么企业项目需要 Controller、Service、Mapper 分层？  
回答：分层隔离协议处理、业务规则和数据访问，便于测试、复用和修改，避免一个类同时承担多个职责。  
追问：简单 CRUD 是否也必须建立 Service？

问题：Maven 的作用是什么？  
回答：统一管理依赖、生命周期、插件和构建产物，使不同环境可以重复构建项目。  
追问：`dependencyManagement` 和 `dependencies` 有什么区别？

## 验收标准

- Java 17、Maven、MySQL 和 Node.js 版本检查通过。
- 后端和 Vue 项目均可启动，启动过程无未处理异常。
- Vue 能成功显示健康检查接口返回结果。
- Swagger UI 可访问并展示健康检查接口。
- 完成 3 张用户相关表的设计和 1 个版本化 SQL 文件。
- 更新开发总结，代码处于待用户确认状态。

## Git提交建议

用户确认后执行一次提交：

```text
chore: initialize community backend and frontend
```

## 今日总结模板

```text
实际用时：
完成内容：
未完成内容：
遇到问题：
问题原因：
解决方法：
学到知识：
请求调用链：
面试表达：
明日调整：
用户确认结果：
```
