# Day 2：用户注册与登录

- 状态：进行中（功能点 1 待用户 Review）
- 预计时间：5 小时 40 分钟
- 前置条件：Day 1 已确认
- 用户确认：Day 1 已确认；Day 2 功能点 1 待 Review

## 功能点进度

| 功能点 | 状态 | Review 结论 |
|---|---|---|
| 1. 用户持久化与注册 | 待用户 Review | 待填写 |
| 2. 账号登录与 JWT 签发 | 未开始 | - |
| 3. Vue 注册登录页面 | 未开始 | - |

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

## 功能点 1 执行记录

- 执行日期：2026-07-21
- 状态：待用户 Review
- 完成内容：实现 `User`、`Role`、`UserRole` 持久化模型，三个 Mapper、注册 DTO、注册 Service、注册 Controller、BCrypt 密码编码、默认角色脚本、统一成功响应和统一异常处理。
- 注册接口：`POST /api/auth/register`，成功返回 HTTP 201；响应只包含用户 ID、用户名、邮箱和昵称，不包含密码或密码哈希。
- 数据脚本：新增 `V002__seed_default_user_role.sql`，提供注册所需的启用状态 `USER` 角色。
- 事务边界：`AuthService.register` 使用 `@Transactional`，用户创建和默认角色分配作为一个事务执行。
- 唯一性策略：Service 先检查用户名和邮箱并返回明确错误；MySQL 唯一约束处理并发请求下的最终冲突。
- 联合主键处理：MyBatis-Plus 不原生支持 `user_role` 联合主键，因此关联写入使用明确的 Mapper `@Insert`，不错误声明单一 `@TableId`。
- 自动化测试：`mvn -B test` 构建成功，12 个测试全部通过，包括 5 个 Service 测试、4 个注册 Controller 测试和 3 个原有健康检查测试。
- 真实验证：在隔离的 MySQL 8.0.43 中执行 `V001 + V002` 后调用注册接口；确认合法注册成功、重复用户名返回 409、非法参数返回 400、密码为 60 位 BCrypt 哈希、用户状态为 1、默认角色为 `USER`。
- 当前限制：未实现登录、JWT、Security 请求过滤器或 Vue 注册页面，这些属于后续功能点。
- Review 重点：DTO 与 Entity 是否隔离、密码是否可能泄露、预检查与数据库唯一约束的分工、事务边界、默认角色缺失时的处理、异常响应是否稳定。
- BCrypt 边界：增加 UTF-8 72 字节校验，避免多字节密码通过字符数校验后在 BCrypt 编码阶段失败。
- 学习重点是：DTO 保护接口边界；Service 预检查提供友好错误；数据库唯一约束处理并发兜底；@Transactional 保证用户和角色关系同时成功或回滚。

### 接口示例

```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "alice",
  "email": "alice@example.com",
  "password": "StrongPass123",
  "nickname": "Alice"
}
```

成功响应：

```json
{
  "code": "SUCCESS",
  "message": "注册成功",
  "data": {
    "id": 1,
    "username": "alice",
    "email": "alice@example.com",
    "nickname": "Alice"
  }
}
```

### 学习建议

1. 对照 `RegisterRequest` 和 `User`，说清 DTO 为什么不等于数据库 Entity，以及响应中为什么绝不能包含 `passwordHash`。
2. 理解 BCrypt 每次生成的哈希通常不同，登录时应使用 `matches`，不能重新编码后比较字符串；同时注意 BCrypt 输入最多 72 字节。
3. 用“Service 预检查改善错误提示，唯一索引保证并发一致性”回答注册防重复问题。

### 面试表达

注册流程在 Controller 层使用 `@Valid` 校验 DTO，Service 层规范化数据并检查唯一性，随后用 BCrypt 生成密码哈希，在同一事务中写入用户和默认角色。应用层检查用于友好提示，数据库唯一约束用于并发兜底；Entity 只负责表映射，接口使用独立 DTO 防止敏感字段泄露。

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
# 笔记
BCrypt 是一种专门为密码存储设计的单向哈希算法，它是目前业界公认的安全密码存储标准方案，旨在解决传统哈希算法（如 MD5、SHA）在应对现代计算能力时暴露出的安全短板。
持久化是指将程序数据在持久状态和瞬时状态间转换的机制。通俗地讲，就是把内存中的瞬时数据（如刚注册的用户信息）保存到能够长久保存的存储设备中（如数据库、磁盘文件）。
Record 是 Java 中的一种特殊类，专门用来充当“不可变的数据载体”。在 Spring Boot 开发中，我们通常用它来接收前端传来的 JSON 请求参数（即 DTO，数据传输对象）。
@Operation：描述操作本身（功能、参数、成功响应的通用类型），不直接定义具体状态码。
@ApiResponse：专门描述特定 HTTP 状态码的响应（如 404 错误、201 成功创建）。
@Valid	触发 JSR-303 校验：若 RegisterRequest 中的字段校验失败（如邮箱格式错误），直接返回 400 Bad Request
@RequestBody	将 JSON 请求体 → RegisterRequest 对象（依赖 Jackson 反序列化）
@RestController	组合注解：@Controller + @ResponseBody，自动序列化返回值为 JSON
@Valid 本身不定义校验规则，仅作为“开关”触发校验。
HttpStatus 和 ResponseEntity 是 Spring Framework 提供的核心类/枚举，用于处理 HTTP 协议中的状态码和响应结构。它们属于 Spring Web 模块（spring-web 组件），是 Java 开发中构建 RESTful API 的标准工具。
@interface 关键字	Java 中定义自定义注解的专用语法（区别于普通 interface）
@Autowired private MockMvc mockMvc;    是 Spring Boot 测试框架中用于模拟 HTTP 请求的核心配置，常见于 单元测试/集成测试类（如 *Test.java）
MockMvc 是 Spring Test 模块提供的模拟 HTTP 客户端
@Autowired MockMvc mockMvc 是 Spring Boot 测试 Web 层的标准配置**。必须搭配 @WebMvcTest（推荐）或 @SpringBootTest 才能生效。
@Mock private UserMapper userMapper; 是 在单元测试中创建 MyBatis Mapper 接口的模拟对象，用于隔离数据库依赖，确保测试仅验证业务逻辑而非真实数据库操作

我的问题>
>RegisterResponse为什么放在dto下，dto不是入参吗?
       DTO 不只是入参。 全称是 Data Transfer Object（数据传输对象），指在层与层、前后端之间传递的数据结构——请求体和响应体都算。
       常见两种放法，本项目用第一种：
                    dto/ 下放 XxxRequest + XxxResponse（或 XxxVO）
                   或拆成 request/、response/ 两个子包（本质一样）
                  所以：RegisterResponse 放在 dto 下是正确的——它是出参 DTO，不是「只有入参才能叫 DTO」。













