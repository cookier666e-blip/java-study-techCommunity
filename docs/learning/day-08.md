# Day 8：站内消息通知

- 状态：未开始
- 预计时间：5 小时 40 分钟
- 前置条件：Day 7 已确认
- 用户确认：未确认

## 今日目标

在评论、回复和点赞成功后生成站内通知，实现通知列表、未读数量、单条已读和全部已读，并完成 Vue 通知中心。

## 时间安排

- 项目开发：3 小时 20 分钟。
- 技术学习：1 小时 10 分钟，学习领域事件和事务提交事件。
- 原理理解：50 分钟，分析业务解耦与消息可靠性。
- 面试总结：20 分钟。

## 今日学习知识

- 站内通知表和通知类型设计。
- Spring ApplicationEvent 与事务提交后的事件监听。
- 同步调用、应用内事件和消息队列的区别。
- 未读状态、分页和批量更新。
- Vue 轮询未读数和通知列表交互。

## 今日开发任务

1. 创建 notification 表，保存接收者、触发者、类型、业务对象、内容摘要和已读状态。
2. 定义评论、回复、点赞对应的应用事件。
3. 在业务事务成功提交后创建通知，避免主事务回滚却产生通知。
4. 用户操作自己的内容时不生成无意义通知。
5. 实现通知分页列表、未读数量、单条已读和全部已读接口。
6. 校验用户只能查看和修改自己的通知。
7. Vue 实现顶部未读数量和通知中心。
8. 为事件触发、事务失败和越权访问编写测试。
9. 记录当前同步/应用内事件方案的可靠性限制，不提前引入 RabbitMQ。
10. 更新开发总结并提交用户 Review。

## 文件创建计划

| 文件或目录 | 作用 |
|---|---|
| `db/schema/V006__create_notification_table.sql` | 通知表和索引 |
| `notification/entity/Notification.java` | 通知映射 |
| `notification/event/InteractionEvent.java` | 互动事件 |
| `notification/listener/NotificationEventListener.java` | 事务后生成通知 |
| `notification/mapper/NotificationMapper.java` | 通知数据访问 |
| `notification/service/NotificationService.java` | 查询和已读业务 |
| `notification/controller/NotificationController.java` | 通知接口 |
| `web/src/stores/notification.ts` | 未读状态 |
| `web/src/views/NotificationView.vue` | 通知中心 |

## 代码生成提示词

使用顺序：通知模型、事件与接口、Vue 通知中心分别 Review。

### 功能点 1：通知表与领域事件模型

```text
使用 $spring-vue-learning-coach，执行 Day 8 功能点 1。只设计并生成 notification 表版本化 SQL、Notification Entity、通知类型枚举、Mapper，以及评论、回复、点赞所需的应用事件对象。不要修改已有评论点赞 Service，不要创建监听器、Controller 或 Vue 页面。完成后验证索引、接收者与业务对象字段设计，更新 Day 8 文档和 DEVELOPMENT_SUMMARY.md，给出通知建模和事件解耦学习建议，标记“待用户 Review”并停止。
```

### 功能点 2：事务后通知生成与通知接口

```text
使用 $spring-vue-learning-coach，执行 Day 8 功能点 2。前提是功能点 1 已由我确认。只在评论、回复和点赞成功时发布事件，使用事务提交后的监听器生成通知；实现通知分页、未读数量、单条已读和全部已读，并校验用户只能操作自己的通知。自己操作自己的内容不生成通知。不要引入 RabbitMQ，也不要修改 Vue。完成后测试事务回滚、越权和自操作场景，更新 Day 8 文档和 DEVELOPMENT_SUMMARY.md，提供事件链路与可靠性限制学习建议，标记“待用户 Review”并停止。
```

### 功能点 3：Vue 通知中心

```text
使用 $spring-vue-learning-coach，执行 Day 8 功能点 3。前提是功能点 2 已由我确认。只实现顶部未读数量、通知分页列表、单条已读、全部已读和跳转到相关内容；使用简单轮询或页面刷新加载，不实现 WebSocket。完成后运行前端检查并验证未读状态变化，更新 Day 8 文档和 DEVELOPMENT_SUMMARY.md，给出通知状态管理学习建议，标记“待用户 Review”并停止。
```

## 原理学习

1. 为什么通知逻辑不应直接散落在评论和点赞 Service 中。
2. 为什么要关注事件发布与数据库事务提交的先后关系。
3. 应用内事件与 RabbitMQ 在可靠性和跨服务能力上的区别。

## 面试问题

问题：Spring 事件有什么作用？  
回答：通过发布者和监听者解耦同一应用中的后续处理，让主业务不直接依赖通知实现。  
追问：Spring 事件默认是同步还是异步？

问题：为什么使用事务提交后的监听器？  
回答：确保主业务成功提交后再执行后续动作，减少事务回滚但通知已经创建的不一致。  
追问：监听器执行失败如何保证最终成功？

问题：什么时候需要引入消息队列？  
回答：需要跨服务通信、削峰、异步处理、重试和更强可靠性时考虑；当前单体教学项目先掌握边界。  
追问：消息队列会引入哪些新问题？

## 验收标准

- 至少 3 种业务动作可以产生通知。
- 自己操作自己的内容不会产生通知。
- 完成列表、未读数量、单条已读和全部已读接口。
- 用户不能读取或修改他人通知。
- Vue 能展示未读数量并更新已读状态。
- 覆盖事务失败不产生通知的测试或明确验证。
- 更新开发总结并等待用户确认。

## Git提交建议

```text
feat: add in-app interaction notifications
```

## 今日总结模板

```text
实际用时：
完成内容：
事件调用链：
事务与通知一致性：
当前可靠性限制：
遇到问题：
解决方法：
面试表达：
用户确认结果：
```
