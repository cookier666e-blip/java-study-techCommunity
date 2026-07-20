# Day 2：用户注册与登录

- 状态：未开始
- 预计时间：5 小时 40 分钟
- 前置条件：Day 1 已确认
- 用户确认：未确认

## 今日目标

完成用户注册和账号密码登录，使用 BCrypt 保存密码，登录成功后签发 JWT，并在 Vue 中完成注册登录页面。

## 时间安排

- 项目开发：3 小时 20 分钟，完成用户持久化、注册登录接口和 Vue 页面。
- 技术学习：1 小时 10 分钟，学习 DTO、参数校验、BCrypt 和 JWT。
- 原理理解：50 分钟，理解密码哈希和无状态认证。
- 面试总结：20 分钟。

## 今日学习知识

- MyBatis-Plus 的 `BaseMapper`、条件构造器和字段映射。
- `@Valid`、`@NotBlank`、`@Email` 等参数校验。
- BCrypt 加盐哈希及密码匹配。
- JWT 的 Header、Payload、Signature 和过期时间。
- Vue 表单、Pinia 用户状态和 Axios 请求封装。

## 今日开发任务

1. 根据 Day 1 SQL 创建 User、Role 和 UserRole Entity/DO。
2. 创建 UserMapper，并完成按用户名、邮箱查询。
3. 定义注册、登录请求 DTO 和登录响应 DTO。
4. 实现注册业务：参数校验、用户名和邮箱唯一检查、BCrypt 加密、默认角色分配。
5. 实现登录业务：查找用户、校验状态、匹配密码、生成 JWT。
6. 创建注册登录 Controller 和统一成功响应。
7. 添加重复用户、参数错误和认证失败的统一异常处理。
8. Vue 实现注册页、登录页、Auth API 和 Pinia Auth Store。
9. 使用 Postman/Apifox 和前端页面分别验证接口。
10. 添加 Service 单元测试，更新开发总结并提交用户 Review。

## 文件创建计划

| 文件或目录 | 作用 |
|---|---|
| `user/entity/User.java` | 用户表映射 |
| `user/entity/Role.java` | 角色表映射 |
| `user/mapper/UserMapper.java` | 用户数据访问 |
| `auth/dto/RegisterRequest.java` | 注册参数和校验 |
| `auth/dto/LoginRequest.java` | 登录参数和校验 |
| `auth/dto/LoginResponse.java` | Token 和用户摘要 |
| `auth/service/AuthService.java` | 注册登录业务 |
| `auth/controller/AuthController.java` | 认证接口 |
| `security/JwtTokenProvider.java` | JWT 生成和解析 |
| `web/src/views/auth/*.vue` | 注册登录页面 |
| `web/src/stores/auth.ts` | 登录状态 |

## 代码生成提示词

使用顺序：每个功能点单独生成、测试和 Review，不允许合并。

### 功能点 1：用户持久化与注册

```text
使用 $spring-vue-learning-coach，执行 Day 2 功能点 1。先检查 Day 1 已确认的代码，只实现用户持久化和注册：User/Role/UserRole Entity、UserMapper、注册 DTO、参数校验、用户名邮箱唯一检查、BCrypt 密码保存、默认 USER 角色、统一异常和 Service 测试。不要实现登录、JWT、Security 过滤器或 Vue 页面。开始前说明调用链和文件计划；完成后运行测试，更新 Day 2 文档和 DEVELOPMENT_SUMMARY.md，给出 BCrypt、唯一约束和 DTO 的学习建议，标记“待用户 Review”并停止。
```

### 功能点 2：账号登录与 JWT 签发

```text
使用 $spring-vue-learning-coach，执行 Day 2 功能点 2。前提是功能点 1 已由我确认。只实现账号密码登录、用户状态检查、BCrypt 匹配、JWT 生成、过期时间配置、登录响应 DTO 和登录失败异常；补充成功与失败测试。今天只签发 Token，不实现 JWT 请求过滤器和接口权限保护。完成后更新 Day 2 文档和 DEVELOPMENT_SUMMARY.md，提供接口请求示例与 JWT 学习建议，标记“待用户 Review”并停止。
```

### 功能点 3：Vue 注册登录页面

```text
使用 $spring-vue-learning-coach，执行 Day 2 功能点 3。前提是功能点 2 已由我确认。只实现 Vue 注册页、登录页、Auth API、Pinia Auth Store、基础表单校验和登录成功后的 Token 保存与跳转。不要实现全局 JWT 拦截器、路由权限守卫和个人中心，这些属于 Day 3。完成后运行前端构建或类型检查，更新 Day 2 文档和 DEVELOPMENT_SUMMARY.md，给出 Pinia 状态管理学习建议，标记“待用户 Review”并停止。
```

## 原理学习

1. 密码为什么只能保存不可逆哈希，不能加密后保存或明文保存。
2. JWT 签名能保证什么，不能保证什么。
3. DTO 与数据库 Entity 分离如何保护接口契约。

## 面试问题

问题：为什么使用 BCrypt 保存密码？  
回答：BCrypt 自带随机盐并且计算成本可调，能降低彩虹表和暴力破解风险。  
追问：用户忘记密码时为什么不能还原原密码？

问题：JWT 由哪三部分组成？  
回答：Header 描述算法，Payload 保存声明，Signature 校验内容是否被篡改。  
追问：敏感信息能放在 Payload 中吗？

问题：注册接口如何防止重复用户？  
回答：业务层先检查以提供友好提示，数据库唯一约束作为并发情况下的最终保证。  
追问：只做一次 `select` 检查为什么不够？

## 验收标准

- 完成注册和登录 2 个接口。
- 密码字段保存为 BCrypt 哈希，不出现明文日志。
- 重复用户名、重复邮箱和错误密码返回明确错误码。
- 登录成功返回带过期时间的 JWT。
- Vue 能完成注册、登录并保存登录状态。
- 至少完成 2 个成功测试和 2 个失败测试。
- 更新开发总结并等待用户确认。

## Git提交建议

```text
feat: add user registration and jwt login
```

## 今日总结模板

```text
实际用时：
完成内容：
注册流程：
登录流程：
遇到问题：
解决方法：
JWT与Session区别：
面试表达：
用户确认结果：
```
