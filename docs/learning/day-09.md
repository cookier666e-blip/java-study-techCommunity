# Day 9：Redis 缓存与热门文章

- 状态：未开始
- 预计时间：5 小时 40 分钟
- 前置条件：Day 8 已确认
- 用户确认：未确认

## 今日目标

使用 Redis 缓存文章详情和热门文章列表，掌握 Cache Aside、缓存失效、穿透处理和基本可观测性。

## 时间安排

- 项目开发：3 小时 20 分钟。
- 技术学习：1 小时 10 分钟，学习 Redis 数据类型、序列化和 TTL。
- 原理理解：50 分钟，分析缓存一致性和异常降级。
- 面试总结：20 分钟。

## 今日学习知识

- Redis String、Hash、ZSet 的适用场景。
- Cache Aside 读取和更新流程。
- 缓存穿透、击穿、雪崩的区别。
- TTL、随机过期时间和空值缓存。
- Redis 不可用时的数据库降级策略。

## 今日开发任务

1. 配置 Redis 连接、JSON 序列化和统一 Key 命名。
2. 为已发布文章详情实现 Cache Aside：先查缓存，未命中查数据库并回填。
3. 文章修改、隐藏或删除后清除对应缓存。
4. 对不存在文章短时间缓存空结果，减少重复穿透。
5. 为 TTL 增加合理随机范围，避免大量 Key 同时失效。
6. 使用 ZSet 或定时重建方式实现热门文章列表。
7. 添加缓存命中、未命中和降级日志，不记录敏感数据。
8. 测试首次查询、再次查询、更新后查询和 Redis 不可用场景。
9. Vue 保持接口契约不变，确认缓存对前端透明。
10. 更新开发总结并提交用户 Review。

## 文件创建计划

| 文件或目录 | 作用 |
|---|---|
| `config/RedisConfig.java` | Redis 连接与序列化 |
| `common/cache/CacheKeys.java` | Key 命名和 TTL 常量 |
| `article/service/ArticleCacheService.java` | 文章缓存读写 |
| `article/service/HotArticleService.java` | 热门列表维护 |
| `article/dto/HotArticleResponse.java` | 热门文章摘要 |
| `application-dev.yml` | 本地 Redis 配置 |
| `article/service/ArticleCacheServiceTest.java` | 缓存行为测试 |

## 代码生成提示词

使用顺序：Redis 基础缓存、缓存一致性与降级、热门排行分别 Review。

### 功能点 1：Redis 配置与文章详情缓存

```text
使用 $spring-vue-learning-coach，执行 Day 9 功能点 1。只配置 Redis 连接、JSON 序列化、统一 Key 命名和 TTL，并为已发布文章详情实现 Cache Aside：先查缓存，未命中查询 MySQL 后回填。不要实现缓存删除、空值缓存、热门列表或限流。完成后通过测试或日志证明首次未命中、第二次命中，更新 Day 9 文档和 DEVELOPMENT_SUMMARY.md，给出 Redis 数据类型、序列化和 Cache Aside 学习建议，标记“待用户 Review”并停止。
```

### 功能点 2：缓存一致性、穿透与降级

```text
使用 $spring-vue-learning-coach，执行 Day 9 功能点 2。前提是功能点 1 已由我确认。只实现文章更新、隐藏、删除后的缓存清理，不存在文章的短 TTL 空值缓存、TTL 随机化，以及 Redis 不可用时回退 MySQL 的降级处理。不要实现热门列表和限流。完成后测试旧数据清理、空值命中和 Redis 异常场景，更新 Day 9 文档和 DEVELOPMENT_SUMMARY.md，给出一致性、穿透、击穿和雪崩学习建议，标记“待用户 Review”并停止。
```

### 功能点 3：Redis 热门文章排行

```text
使用 $spring-vue-learning-coach，执行 Day 9 功能点 3。前提是功能点 2 已由我确认。只使用 Redis ZSet 或清晰的定时重建方案实现热门文章列表，定义分数来源、Key、更新方式、过期策略和数据库回退；保持现有前端接口契约，必要时只增加热门列表展示。完成后验证排序、数据缺失和 Redis 降级，更新 Day 9 文档和 DEVELOPMENT_SUMMARY.md，给出 ZSet 与热点数据学习建议，标记“待用户 Review”并停止。
```

## 原理学习

1. Cache Aside 为什么通常采用“更新数据库后删除缓存”。
2. 缓存穿透、击穿和雪崩分别如何产生。
3. Redis 缓存为什么不能成为唯一数据源。

## 面试问题

问题：Cache Aside 的标准读取流程是什么？  
回答：先读缓存，命中直接返回；未命中查询数据库，随后写入缓存并返回。  
追问：更新数据时先删缓存还是先更新数据库？

问题：如何防止缓存穿透？  
回答：可以缓存空结果、使用布隆过滤器、校验非法参数；当前项目使用短 TTL 空值缓存。  
追问：空值缓存有什么副作用？

问题：Redis 宕机时系统如何处理？  
回答：缓存层捕获可预期异常并降级到数据库，同时监控告警；还要防止请求全部打向数据库导致雪崩。  
追问：如何保护数据库？

## 验收标准

- 文章详情和热门列表接入 Redis。
- 通过日志或测试证明第二次详情查询命中缓存。
- 修改文章后不会持续返回旧内容。
- 不存在文章使用短 TTL 空值缓存。
- Redis 不可用时核心详情查询可降级到数据库。
- 至少完成 4 个缓存场景测试。
- 更新开发总结并等待用户确认。

## Git提交建议

```text
feat: cache article details and hot ranking with redis
```

## 今日总结模板

```text
实际用时：
完成内容：
缓存Key设计：
TTL设计：
缓存读写流程：
一致性策略：
异常降级：
测试证据：
面试表达：
用户确认结果：
```
