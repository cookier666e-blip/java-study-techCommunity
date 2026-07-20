# Day 4：文章草稿、发布与编辑

- 状态：未开始
- 预计时间：5 小时 40 分钟
- 前置条件：Day 3 已确认
- 用户确认：未确认

## 今日目标

完成文章草稿、发布、详情、编辑和删除主流程，并实现文章作者的数据权限和 Vue Markdown 编辑页面。

## 时间安排

- 项目开发：3 小时 20 分钟。
- 技术学习：1 小时 10 分钟，学习事务、枚举状态和 DTO 映射。
- 原理理解：50 分钟，理解文章状态机和资源所有权。
- 面试总结：20 分钟。

## 今日学习知识

- MyBatis-Plus CRUD 与自定义业务 Service。
- `DRAFT`、`PUBLISHED`、`HIDDEN` 状态设计。
- `@Transactional` 的使用边界和回滚条件。
- 创建、修改、详情 DTO 的职责差异。
- Vue Markdown 编辑、表单校验和预览。

## 今日开发任务

1. 设计并创建 article 表，包含作者、标题、摘要、内容、状态和时间字段。
2. 创建 Article Entity、Mapper、DTO 和 Service。
3. 实现创建草稿和发布文章接口。
4. 实现文章详情、编辑和删除接口。
5. 校验只有作者或管理员可以修改、删除文章。
6. 校验草稿只能由作者查看，隐藏文章只允许管理员和作者查看。
7. 添加统一参数校验和文章不存在异常。
8. Vue 实现文章编辑器、Markdown 预览和详情页。
9. 完成 Service 与 Controller 关键测试。
10. 更新开发总结并提交用户 Review。

## 文件创建计划

| 文件或目录 | 作用 |
|---|---|
| `db/schema/V002__create_article_table.sql` | 文章表 |
| `article/entity/Article.java` | 文章数据映射 |
| `article/mapper/ArticleMapper.java` | 文章持久化 |
| `article/dto/ArticleCreateRequest.java` | 创建参数 |
| `article/dto/ArticleUpdateRequest.java` | 修改参数 |
| `article/dto/ArticleDetailResponse.java` | 详情响应 |
| `article/service/ArticleService.java` | 状态和权限业务 |
| `article/controller/ArticleController.java` | 文章接口 |
| `web/src/views/article/ArticleEditorView.vue` | 编辑器 |
| `web/src/views/article/ArticleDetailView.vue` | 详情页 |

## 代码生成提示词

使用顺序：先数据层，再业务接口，最后 Vue 页面；每一步均需 Review。

### 功能点 1：文章表与数据访问层

```text
使用 $spring-vue-learning-coach，执行 Day 4 功能点 1。只设计并生成 article 表版本化 SQL、Article Entity、状态枚举、ArticleMapper 和最小 Mapper 测试，字段包含作者、标题、摘要、Markdown 内容、状态、计数和审计时间。不要实现 Service、Controller 或 Vue 页面。开始前解释状态字段和索引设计；完成后验证 SQL 与映射，更新 Day 4 文档和 DEVELOPMENT_SUMMARY.md，给出表结构与枚举映射学习建议，标记“待用户 Review”并停止。
```

### 功能点 2：文章草稿、发布与权限接口

```text
使用 $spring-vue-learning-coach，执行 Day 4 功能点 2。前提是功能点 1 已由我确认。只实现文章创建草稿、发布、详情、编辑、删除的 DTO、Service、Controller、参数校验、事务和作者/管理员权限。草稿只能作者查看，隐藏内容只允许作者或管理员查看。不要修改 Vue，也不要实现标签、列表搜索和缓存。完成后运行关键 Service/Controller 测试，更新 Day 4 文档和 DEVELOPMENT_SUMMARY.md，提供接口示例和事务学习建议，标记“待用户 Review”并停止。
```

### 功能点 3：Vue Markdown 编辑与详情

```text
使用 $spring-vue-learning-coach，执行 Day 4 功能点 3。前提是功能点 2 已由我确认。只实现文章编辑器、Markdown 预览、保存草稿、发布、编辑和详情页面，处理加载、校验、无权限和请求失败状态。不要实现首页列表、标签或搜索。完成后运行前端构建或类型检查并验证完整发布流程，更新 Day 4 文档和 DEVELOPMENT_SUMMARY.md，给出表单状态与接口联调学习建议，标记“待用户 Review”并停止。
```

## 原理学习

1. 为什么文章状态不能只使用一个 `deleted` 布尔值表示。
2. `@Transactional` 应放在 Controller、Service 还是 Mapper。
3. 数据权限为什么必须和文章查询、更新操作放在同一业务流程中。

## 面试问题

问题：Spring 事务失效的常见原因有哪些？  
回答：同类内部调用、方法不可代理、异常被吞掉、异常类型不触发默认回滚、对象不是 Spring Bean 等。  
追问：为什么同类内部调用可能绕过代理？

问题：文章详情为什么不直接返回 Entity？  
回答：Entity 代表数据库结构，直接返回会暴露字段并使接口契约与表结构强耦合。  
追问：DTO 映射代码较多时如何处理？

问题：如何保证用户不能修改他人的文章？  
回答：Service 获取当前用户，查询文章后校验作者或管理员身份，再在事务内更新。  
追问：能否把作者条件直接写进更新 SQL？

## 验收标准

- 完成至少 5 个文章接口。
- 草稿、发布和隐藏状态行为符合设计。
- 普通用户不能修改或删除他人文章。
- 非作者不能查看草稿。
- Vue 能创建、预览、发布和查看文章。
- 至少覆盖作者成功修改和非作者失败两类测试。
- 更新开发总结并等待用户确认。

## Git提交建议

```text
feat: add article publishing workflow
```

## 今日总结模板

```text
实际用时：
完成内容：
文章状态设计：
事务边界：
权限校验：
遇到问题：
解决方法：
面试表达：
用户确认结果：
```
