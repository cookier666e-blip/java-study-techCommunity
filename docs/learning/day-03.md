# Day 3：JWT 鉴权、权限与个人信息

- 状态：未开始
- 预计时间：5 小时 40 分钟
- 前置条件：Day 2 已确认
- 用户确认：未确认

## 今日目标

将 Day 2 的 JWT 接入 Spring Security，保护业务接口，实现当前用户信息、角色权限和前端路由保护。

## 时间安排

- 项目开发：3 小时 20 分钟。
- 技术学习：1 小时 10 分钟，学习 SecurityFilterChain 和认证上下文。
- 原理理解：50 分钟，梳理过滤器链与 401/403。
- 面试总结：20 分钟。

## 今日学习知识

- Spring Security 过滤器链和 `SecurityFilterChain`。
- JWT 解析、Authentication 和 SecurityContext。
- `USER`、`ADMIN` 角色和方法级授权。
- 资源所有者权限与角色权限的区别。
- Vue Router 守卫和 Axios Token 拦截器。

## 今日开发任务

1. 实现 JWT 认证过滤器，从 Authorization Header 提取 Token。
2. 校验签名和过期时间，加载用户与角色并写入 SecurityContext。
3. 配置注册、登录、Swagger 放行，其余接口默认认证。
4. 统一处理未登录 401 和无权限 403。
5. 实现 `GET /api/users/me` 和个人资料修改接口。
6. 添加管理员测试接口或最小管理能力，验证角色规则。
7. Vue Axios 拦截器自动携带 Token，收到 401 时清理登录状态。
8. 添加前端路由守卫和个人中心页面。
9. 覆盖无 Token、无效 Token、过期 Token、角色不足测试。
10. 更新开发总结并提交用户 Review。

## 文件创建计划

| 文件或目录 | 作用 |
|---|---|
| `security/JwtAuthenticationFilter.java` | 解析请求 Token |
| `security/SecurityConfig.java` | 安全规则和过滤器链 |
| `security/CustomUserDetailsService.java` | 加载用户及角色 |
| `security/RestAuthenticationEntryPoint.java` | 返回 401 |
| `security/RestAccessDeniedHandler.java` | 返回 403 |
| `user/controller/UserController.java` | 当前用户接口 |
| `user/dto/UserProfileResponse.java` | 用户资料响应 |
| `web/src/router/guards.ts` | 路由鉴权 |
| `web/src/views/user/ProfileView.vue` | 个人中心 |

## 代码生成提示词

使用顺序：功能点 1、2、3 分别 Review，任何一个未确认都不能继续。

### 功能点 1：JWT 过滤器与安全配置

```text
使用 $spring-vue-learning-coach，执行 Day 3 功能点 1。只实现 JwtAuthenticationFilter、CustomUserDetailsService、SecurityConfig，以及统一的 401 AuthenticationEntryPoint 和 403 AccessDeniedHandler。放行注册、登录、健康检查和 Swagger，其余接口要求认证；不要实现个人资料接口、管理员业务接口或 Vue 改动。完成后覆盖无 Token、无效 Token、过期 Token 和有效 Token 测试，更新 Day 3 文档和 DEVELOPMENT_SUMMARY.md，给出 Spring Security 过滤器链学习建议，标记“待用户 Review”并停止。
```

### 功能点 2：当前用户与角色权限

```text
使用 $spring-vue-learning-coach，执行 Day 3 功能点 2。前提是功能点 1 已由我确认。只实现获取当前登录用户、GET /api/users/me、个人资料修改和最小 USER/ADMIN 角色授权示例；Service 中校验用户状态和数据权限。不要修改 Vue。完成后测试普通用户成功访问、普通用户访问管理员能力返回 403、未登录返回 401，更新 Day 3 文档和 DEVELOPMENT_SUMMARY.md，给出角色权限与数据权限学习建议，标记“待用户 Review”并停止。
```

### 功能点 3：前端认证拦截与个人中心

```text
使用 $spring-vue-learning-coach，执行 Day 3 功能点 3。前提是功能点 2 已由我确认。只实现 Axios 自动携带 Token、401 清理状态、Vue Router 登录守卫、刷新后调用 /api/users/me 恢复用户信息，以及个人中心查看和修改资料。不要开发文章功能。完成后运行前端检查并验证有效 Token、无效 Token 和刷新恢复场景，更新 Day 3 文档和 DEVELOPMENT_SUMMARY.md，给出前后端权限边界学习建议，标记“待用户 Review”并停止。
```

## 原理学习

1. JWT 请求为什么仍然需要经过 Spring Security 认证过程。
2. 401 与 403 的语义区别。
3. 前端隐藏按钮为什么不能替代后端权限检查。

## 面试问题

问题：Spring Security 的认证信息保存在哪里？  
回答：当前请求线程通常通过 SecurityContextHolder 访问 SecurityContext 中的 Authentication。  
追问：请求结束后为什么要清理上下文？

问题：JWT 过滤器应该放在过滤器链什么位置？  
回答：通常放在 UsernamePasswordAuthenticationFilter 之前，让后续授权逻辑能获取已认证身份。  
追问：过滤器中发现无 Token 是否一定返回错误？

问题：角色权限和数据权限有什么区别？  
回答：角色决定用户能执行哪类操作，数据权限决定用户能操作哪些具体资源。  
追问：文章作者修改文章属于哪一种？

## 验收标准

- 完成当前用户查询和资料修改接口。
- 公开接口无需 Token，受保护接口必须携带有效 Token。
- 401、403 返回不同且稳定的错误响应。
- 普通用户不能访问管理员接口。
- Vue 刷新后可恢复用户信息，Token 失效会跳转登录页。
- 至少覆盖 4 类鉴权失败测试。
- 更新开发总结并等待用户确认。

## Git提交建议

```text
feat: secure api with jwt and role authorization
```

## 今日总结模板

```text
实际用时：
完成内容：
过滤器调用链：
401与403区别：
遇到问题：
解决方法：
权限设计不足：
面试表达：
用户确认结果：
```
