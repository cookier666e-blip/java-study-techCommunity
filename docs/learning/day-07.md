# Day 7：文章点赞与收藏

- 状态：未开始
- 预计时间：5 小时 40 分钟
- 前置条件：Day 6 已确认
- 用户确认：未确认

## 今日目标

完成文章点赞、取消点赞、收藏、取消收藏和个人收藏列表，重点解决接口幂等、唯一约束和计数一致性。

## 时间安排

- 项目开发：3 小时 20 分钟。
- 技术学习：1 小时 10 分钟，学习唯一约束、事务和原子更新。
- 原理理解：50 分钟，分析重复请求与并发竞争。
- 面试总结：20 分钟。

## 今日学习知识

- 联合唯一索引与数据库最终约束。
- 幂等接口和重复请求处理。
- `like_count = like_count + 1` 原子更新。
- 事务、并发竞争和计数冗余。
- Vue 乐观交互与失败回滚。

## 今日开发任务

1. 创建 article_like 和 article_favorite 表，为 `(user_id, article_id)` 添加联合唯一约束。
2. 为 article 表增加点赞数和收藏数冗余字段，并编写版本化 SQL。
3. 实现点赞、取消点赞，重复点赞返回当前状态而不是新增重复数据。
4. 实现收藏、取消收藏和当前用户收藏列表。
5. 在同一事务中维护关系记录和文章计数。
6. 使用原子 SQL 增减计数，并保证计数不小于 0。
7. 返回文章详情时包含当前用户是否点赞和收藏。
8. Vue 实现点赞收藏按钮、计数和收藏列表，失败时恢复 UI 状态。
9. 编写重复请求、数据库唯一冲突和无权限测试。
10. 更新开发总结并提交用户 Review。

## 文件创建计划

| 文件或目录 | 作用 |
|---|---|
| `db/schema/V005__create_article_interactions.sql` | 点赞收藏表和计数字段 |
| `interaction/entity/ArticleLike.java` | 点赞关系 |
| `interaction/entity/ArticleFavorite.java` | 收藏关系 |
| `interaction/mapper/InteractionMapper.java` | 互动数据访问 |
| `interaction/mapper/InteractionMapper.xml` | 原子计数 SQL |
| `interaction/service/InteractionService.java` | 幂等和事务逻辑 |
| `interaction/controller/InteractionController.java` | 点赞收藏接口 |
| `web/src/components/article/ArticleActions.vue` | 互动按钮 |
| `web/src/views/user/FavoritesView.vue` | 收藏列表 |

## 代码生成提示词

使用顺序：先建立数据库最终约束，再实现事务接口，最后实现前端交互。

### 功能点 1：点赞收藏表与唯一约束

```text
使用 $spring-vue-learning-coach，执行 Day 7 功能点 1。只生成 article_like、article_favorite 表版本化 SQL、Entity、Mapper，并为 user_id + article_id 添加联合唯一约束；为 article 增加点赞数和收藏数冗余字段及原子增减 Mapper SQL。不要实现 Service、Controller 或 Vue。完成后验证唯一约束、索引和计数不小于 0 的 SQL，更新 Day 7 文档和 DEVELOPMENT_SUMMARY.md，给出幂等与冗余计数学习建议，标记“待用户 Review”并停止。
```

### 功能点 2：点赞收藏幂等接口

```text
使用 $spring-vue-learning-coach，执行 Day 7 功能点 2。前提是功能点 1 已由我确认。只实现点赞、取消点赞、收藏、取消收藏、收藏列表和文章详情互动状态，关系写入与计数更新放在同一事务，正确处理重复请求和唯一冲突。不要使用 Redis，不要修改 Vue。完成后测试重复点赞、重复取消、唯一冲突和计数一致性，更新 Day 7 文档和 DEVELOPMENT_SUMMARY.md，提供接口示例和并发一致性学习建议，标记“待用户 Review”并停止。
```

### 功能点 3：Vue 点赞收藏交互

```text
使用 $spring-vue-learning-coach，执行 Day 7 功能点 3。前提是功能点 2 已由我确认。只实现文章详情点赞收藏按钮、数量展示、当前状态和个人收藏列表；可以使用乐观 UI，但请求失败必须回滚状态并展示错误。不要实现通知。完成后运行前端检查并验证重复点击与失败回滚，更新 Day 7 文档和 DEVELOPMENT_SUMMARY.md，给出乐观更新与后端幂等边界学习建议，标记“待用户 Review”并停止。
```

## 原理学习

1. 业务层查重为什么不能代替数据库唯一约束。
2. 冗余计数提高读取效率时会引入什么一致性问题。
3. 原子更新、乐观锁和分布式锁分别适合什么场景。

## 面试问题

问题：如何保证点赞接口幂等？  
回答：以用户和文章建立唯一约束，业务层把重复请求转换为稳定结果，并正确处理数据库唯一冲突。  
追问：前端按钮防抖能否代替后端幂等？

问题：点赞数为什么可能不准确？  
回答：关系写入和计数更新不在同一事务、并发覆盖、重试或异常回滚不完整都可能造成偏差。  
追问：如何进行数据修复？

问题：为什么使用原子自增 SQL？  
回答：避免先读后写导致多个请求用旧值相互覆盖，由数据库在一条更新语句中完成增减。  
追问：原子自增是否能解决关系表重复问题？

## 验收标准

- 完成点赞、取消点赞、收藏、取消收藏和收藏列表接口。
- 重复点赞或取消不会产生重复记录和错误计数。
- 数据库存在两个必要的联合唯一约束。
- 文章详情正确返回当前用户互动状态。
- Vue 失败时不会留下错误的点赞或收藏状态。
- 至少完成 3 类幂等或并发相关测试。
- 更新开发总结并等待用户确认。

## Git提交建议

```text
feat: add idempotent likes and favorites
```

## 今日总结模板

```text
实际用时：
完成内容：
幂等设计：
唯一约束：
计数一致性方案：
遇到问题：
解决方法：
面试表达：
用户确认结果：
```
