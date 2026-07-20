# Day 5：文章列表、标签与搜索

- 状态：未开始
- 预计时间：5 小时 40 分钟
- 前置条件：Day 4 已确认
- 用户确认：未确认

## 今日目标

完成文章分页列表、标签分类、最新与热门排序、MySQL 关键词搜索，并实现 Vue 首页和搜索页。

## 时间安排

- 项目开发：3 小时 20 分钟。
- 技术学习：1 小时 10 分钟，学习分页、联表查询和索引。
- 原理理解：50 分钟，分析 SQL 执行和搜索边界。
- 面试总结：20 分钟。

## 今日学习知识

- MyBatis-Plus `Page`、分页插件和动态查询条件。
- 标签、多对多关系与 article_tag 中间表。
- MySQL `LIKE`、全文索引的适用范围和限制。
- `ORDER BY`、组合索引和 EXPLAIN 基础。
- Vue 列表状态、查询参数同步和分页组件。

## 今日开发任务

1. 创建 tag 和 article_tag 表及唯一约束。
2. 实现文章发布时保存标签关系。
3. 实现只返回已发布文章的分页列表。
4. 支持关键词、标签、作者、最新和热门条件。
5. 编写清晰的 Mapper SQL，避免在 Service 中拼接查询字符串。
6. 为状态、发布时间、作者和关联字段添加必要索引。
7. 使用 EXPLAIN 观察至少一条列表或搜索 SQL。
8. Vue 实现首页列表、标签筛选、搜索框和分页。
9. Swagger 补充查询参数说明和响应示例。
10. 添加查询测试，更新开发总结并提交用户 Review。

## 文件创建计划

| 文件或目录 | 作用 |
|---|---|
| `db/schema/V003__create_article_tag_tables.sql` | 标签和关联表 |
| `tag/entity/Tag.java` | 标签映射 |
| `tag/mapper/TagMapper.java` | 标签查询 |
| `article/mapper/ArticleMapper.xml` | 分页、筛选和搜索 SQL |
| `article/dto/ArticleQuery.java` | 查询条件 |
| `article/dto/ArticleSummaryResponse.java` | 列表摘要 |
| `web/src/views/HomeView.vue` | 文章首页 |
| `web/src/views/SearchView.vue` | 搜索结果页 |
| `web/src/components/article/ArticleList.vue` | 通用文章列表 |

## 代码生成提示词

使用顺序：标签数据、查询接口、Vue 页面分别生成并 Review。

### 功能点 1：标签与文章标签关系

```text
使用 $spring-vue-learning-coach，执行 Day 5 功能点 1。只实现 tag、article_tag 表脚本、Entity、Mapper，以及文章发布或编辑时保存标签关系的 Service 逻辑；为标签名和文章标签关系添加唯一约束。不要实现文章列表、搜索、排序或 Vue 页面。完成后测试重复标签和事务回滚场景，更新 Day 5 文档和 DEVELOPMENT_SUMMARY.md，给出多对多关系学习建议，标记“待用户 Review”并停止。
```

### 功能点 2：分页列表、筛选与 MySQL 搜索

```text
使用 $spring-vue-learning-coach，执行 Day 5 功能点 2。前提是功能点 1 已由我确认。只实现已发布文章的分页列表，支持关键词、标签、作者、最新和热门条件；使用 MyBatis-Plus 分页和清晰的 Mapper XML SQL，补充必要索引并使用 EXPLAIN 分析一条关键 SQL。不要修改 Vue，也不要引入 Elasticsearch 或 Redis。完成后运行查询测试，更新 Day 5 文档和 DEVELOPMENT_SUMMARY.md，记录 EXPLAIN 结果和索引学习建议，标记“待用户 Review”并停止。
```

### 功能点 3：Vue 首页与搜索页

```text
使用 $spring-vue-learning-coach，执行 Day 5 功能点 3。前提是功能点 2 已由我确认。只实现 Vue 首页文章列表、分页、标签筛选、排序和搜索结果页，并让查询条件与 URL 参数同步；处理加载、空数据和失败状态。不要实现评论、点赞或收藏。完成后运行前端检查并验证组合查询，更新 Day 5 文档和 DEVELOPMENT_SUMMARY.md，给出分页状态和搜索联调学习建议，标记“待用户 Review”并停止。
```

## 原理学习

1. 深分页为什么会越来越慢，十天项目中先采用什么方案。
2. 组合索引为什么要关注字段顺序和最左前缀。
3. MySQL 搜索与 Elasticsearch 的能力边界。

## 面试问题

问题：MyBatis-Plus 分页是如何生效的？  
回答：配置分页拦截器后，查询会生成分页和总数 SQL，并映射到 Page 对象。  
追问：大数据量下 `count(*)` 可能有什么问题？

问题：什么情况下索引可能失效？  
回答：函数计算、隐式类型转换、不符合组合索引顺序、前导模糊匹配等情况可能导致无法有效使用索引。  
追问：如何确认 SQL 是否使用了索引？

问题：为什么当前阶段不引入 Elasticsearch？  
回答：当前数据规模和需求可以由 MySQL 支撑，先掌握查询与索引并建立性能基线，再根据搜索能力需求引入 ES。  
追问：哪些需求出现时应考虑 ES？

## 验收标准

- 完成标签表和文章标签关联表。
- 列表接口支持分页及至少 4 类筛选或排序条件。
- 搜索结果不包含草稿和隐藏文章。
- 至少保存一份 EXPLAIN 分析结果到当天总结。
- Vue 首页和搜索页可用，并处理加载、空数据和失败状态。
- 更新开发总结并等待用户确认。

## Git提交建议

```text
feat: add article discovery and mysql search
```

## 今日总结模板

```text
实际用时：
完成内容：
最复杂SQL：
EXPLAIN结果：
新增索引及理由：
遇到问题：
解决方法：
MySQL搜索限制：
面试表达：
用户确认结果：
```
