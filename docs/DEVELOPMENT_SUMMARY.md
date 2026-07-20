# 持续开发总结

## 使用规则

每次添加或修改代码后，在本文档顶部追加一条记录。一次记录至少包含：

- 日期和对应 Day。
- 当前功能点和代码生成批次。
- 本次目标与实际完成内容。
- 主要新增或修改文件。
- 接口和数据库变化。
- 测试命令与结果。
- 学习建议和面试表达。
- 用户确认状态：`待确认`、`已确认` 或 `需修改`。

每个代码生成批次都必须单独 Review。用户确认前不执行当天的下一个功能点；当天所有批次确认前不执行 Git commit。

## 记录模板

```markdown
## YYYY-MM-DD - Day X - 功能名称

- 功能点：
- 代码批次：
- 状态：待用户 Review
- 目标：
- 完成内容：
- 主要文件：
- 接口变化：
- 数据库变化：
- 测试结果：
- 学习建议：
- 面试表达：
- Review 关注点：
- 用户反馈与结论：
```

## 2026-07-20 - Day 1 - 后端工程与健康检查

- 功能点：Day 1 功能点 1。
- 代码批次：后端 Maven 骨架、基础依赖、健康检查与 Swagger。
- 状态：待用户 Review。
- 目标：创建可运行的 Java 17 + Spring Boot 3 后端骨架，不进入用户、数据库和 Vue 功能。
- 完成内容：配置 Spring MVC、Validation、MyBatis-Plus、MySQL、Spring Security、Swagger 和测试依赖；增加启动类、健康检查 DTO/Controller、开发配置、临时 Security 放行配置和集成测试。
- 主要文件：`server/pom.xml`、`CommunityApplication.java`、`SecurityConfig.java`、`HealthController.java`、`HealthResponse.java`、`application.yml`、`application-dev.yml`、`HealthControllerIntegrationTest.java`。
- 接口变化：新增 `GET /api/health`，返回 `{"status":"UP","application":"community-server"}`；新增 `/v3/api-docs` 和 `/swagger-ui/index.html`。
- 数据库变化：无。只配置 MySQL 连接环境变量，未创建表和 Mapper。
- 测试结果：Java 17.0.14、Maven 3.9.16；`mvn -B clean test` 构建成功，3 个测试全部通过；真实 HTTP 验证健康检查、OpenAPI 和 Swagger UI 均成功。
- 学习建议：先沿着 `HTTP -> Controller -> DTO -> JSON` 调用链理解 Spring MVC；阅读 `pom.xml` 时区分 Starter、运行时依赖和测试依赖；注意当前 Security 全放行只是 Day 1 的临时边界。
- 面试表达：Spring Boot Starter 负责聚合兼容依赖和自动配置；健康检查使用 Controller 暴露 REST 接口，Spring MVC 通过 Jackson 将 DTO 序列化为 JSON。
- Review 关注点：是否保持功能点 1 范围、依赖选择是否清晰、配置是否泄露敏感信息、Swagger 和健康检查是否有自动化测试。
- 用户反馈与结论：待填写。

## 2026-07-15 - 规划与文档基线

- 状态：已完成文档初始化，未涉及业务代码确认。
- 目标：建立规范开发流程和十天学习文档体系。
- 完成内容：确定技术基线、项目范围、文档更新规则和用户确认流程；将 Day 1~10 各拆分为 3 个独立代码生成提示词，每个批次均需单独 Review。
- 主要文件：`AGENTS.md`、`docs/PROJECT_OVERVIEW.md`、`docs/DEVELOPMENT_SUMMARY.md`、`docs/learning/day-XX.md`。
- 接口变化：无。
- 数据库变化：无。
- 测试结果：仅进行文档结构与内容检查。
- 学习建议：开始 Day 1 前准备 Java 17、Maven、MySQL、Node.js 和 IDE，并确认版本。
- 面试表达：能够说明项目采用前后端分离、分层架构以及选择 MyBatis-Plus 的原因。
- 用户反馈：等待开始 Day 1。
