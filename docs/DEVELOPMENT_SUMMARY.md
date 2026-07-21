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

## 2026-07-21 - Day 1 - Vue 工程与前后端连通

- 功能点：Day 1 功能点 3。
- 代码批次：Vue 3 前端骨架、基础工程配置和健康检查联调。
- 状态：待用户 Review。
- 目标：建立可构建、可运行的 Vue 3 + TypeScript 前端，并通过首页完成一次真实后端请求，不进入注册登录开发。
- 完成内容：配置 Vite、Vue Router、Pinia、Axios、Element Plus、TypeScript 和环境变量；增加统一 Axios 实例、健康检查 API、加载/成功/失败/重试页面状态和响应式基础样式；Element Plus 采用按需导入。
- 主要文件：`web/package.json`、`web/pnpm-lock.yaml`、`web/vite.config.ts`、`web/src/main.ts`、`web/src/router/index.ts`、`web/src/api/http.ts`、`web/src/api/health.ts`、`web/src/views/HomeView.vue`、`web/src/styles/main.css`。
- 接口变化：后端接口无变化；前端调用已有 `GET /api/health`。
- 数据库变化：无。
- 测试结果：`pnpm build` 完成 TypeScript 检查和生产构建；构建产物 JavaScript 约 186 KB；从前端端口 `3000` 经 Vite 代理请求后端端口 `8080`，真实返回 `UP / community-server`。
- 问题与处理：TypeScript 7 与当前 `vue-tsc` 不兼容，固定到 TypeScript 5.9.3；Element Plus 全量注册造成约 1 MB 主包，改为四个组件按需导入后消除体积警告。
- 学习建议：重点掌握组件生命周期触发请求、响应式状态、Axios 实例、环境变量和开发代理组成的完整调用链。
- 面试表达：开发代理解决本地前后端端口不同的问题，Axios 实例统一超时和错误转换；局部页面状态保留在组件中，Pinia 只承载真正跨页面共享的数据。
- Review 关注点：目录职责、请求错误处理、加载状态、环境配置、依赖版本锁定和前端包体积。
- 用户反馈与结论：待填写。

## 2026-07-20 - Day 1 - 用户表设计与数据库脚本

- 功能点：Day 1 功能点 2。
- 代码批次：用户、角色和用户角色关系表设计。
- 状态：已确认。
- 目标：只完成三张用户相关表和版本化 MySQL 脚本，不进入 Java 用户业务与 Vue 开发。
- 完成内容：新增 `user`、`role`、`user_role` 建表脚本；设计主键、唯一约束、查询索引、状态检查、审计时间和外键关系；修正开发配置中的 MySQL 默认端口 `330 -> 3306`。
- 主要文件：`server/src/main/resources/db/schema/V001__create_user_tables.sql`、`server/src/main/resources/application-dev.yml`、`docs/learning/day-01.md`、`docs/PROJECT_OVERVIEW.md`。
- 接口变化：无。
- 数据库变化：新增 3 张表的版本化定义；`user_role` 使用联合主键，两个外键采用 `ON DELETE CASCADE`。
- 测试结果：脚本在隔离的 MySQL 8.0.43 实例执行成功；3 张表、2 个外键、2 个 CHECK 约束均存在；4 个关键非法写入均被拒绝；级联删除验证通过。`mvn -B test` 构建成功，3 个测试全部通过。
- 学习建议：重点区分应用层校验与数据库约束，理解多对多中间表、联合主键、外键和索引各自负责的问题。
- 面试表达：用户与角色通过关联表建模多对多关系，以联合主键保证授权幂等，以唯一约束和外键作为数据库层最后一道一致性防线。
- Review 关注点：字段长度与空值设计、约束和索引是否服务真实查询、级联删除策略、关键字表名的处理、是否严格保持功能点范围。
- 用户反馈与结论：2026-07-21 用户确认功能点 2 通过，同意进入 Day 1 功能点 3。@L : 主键 保证每条数据的可读性，唯一约束 方便代码判断唯一性，普通索引 方便排序 提高查询性能，外键保证引用完整性

## 2026-07-20 - Day 1 - 后端工程与健康检查

- 功能点：Day 1 功能点 1。
- 代码批次：后端 Maven 骨架、基础依赖、健康检查与 Swagger。
- 状态：已确认。
- 目标：创建可运行的 Java 17 + Spring Boot 3 后端骨架，不进入用户、数据库和 Vue 功能。
- 完成内容：配置 Spring MVC、Validation、MyBatis-Plus、MySQL、Spring Security、Swagger 和测试依赖；增加启动类、健康检查 DTO/Controller、开发配置、临时 Security 放行配置和集成测试。
- 主要文件：`server/pom.xml`、`CommunityApplication.java`、`SecurityConfig.java`、`HealthController.java`、`HealthResponse.java`、`application.yml`、`application-dev.yml`、`HealthControllerIntegrationTest.java`。
- 接口变化：新增 `GET /api/health`，返回 `{"status":"UP","application":"community-server"}`；新增 `/v3/api-docs` 和 `/swagger-ui/index.html`。
- 数据库变化：无。只配置 MySQL 连接环境变量，未创建表和 Mapper。
- 测试结果：Java 17.0.14、Maven 3.9.16；`mvn -B clean test` 构建成功，3 个测试全部通过；真实 HTTP 验证健康检查、OpenAPI 和 Swagger UI 均成功。
- 学习建议：先沿着 `HTTP -> Controller -> DTO -> JSON` 调用链理解 Spring MVC；阅读 `pom.xml` 时区分 Starter、运行时依赖和测试依赖；注意当前 Security 全放行只是 Day 1 的临时边界。
- 面试表达：Spring Boot Starter 负责聚合兼容依赖和自动配置；健康检查使用 Controller 暴露 REST 接口，Spring MVC 通过 Jackson 将 DTO 序列化为 JSON。
- Review 关注点：是否保持功能点 1 范围、依赖选择是否清晰、配置是否泄露敏感信息、Swagger 和健康检查是否有自动化测试。
- 用户反馈与结论：2026-07-20 用户确认功能点 1 通过，同意进入 Day 1 功能点 2。

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
