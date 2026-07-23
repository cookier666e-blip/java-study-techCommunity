# Day 1：工程初始化与用户模块准备

- 状态：已完成（3 个功能点均已确认）
- 预计时间：5 小时 40 分钟
- 前置条件：安装 Java 17、Maven、MySQL、Node.js 和 IDE
- 用户确认：功能点 1、2、3 均已确认

## 功能点进度

| 功能点 | 状态 | Review 结论 |
|---|---|---|
| 1. 后端工程与健康检查 | 已确认 | 2026-07-20 用户确认通过 |
| 2. 用户表设计与数据库脚本 | 已确认 | 2026-07-20 用户确认通过 |
| 3. Vue 工程与前后端连通 | 已确认 | 2026-07-21 用户确认通过 |

## 今日目标

完成 Spring Boot 与 Vue 项目初始化，建立可运行的前后端骨架、基础分层、开发配置和用户表设计，为 Day 2 注册登录开发做好准备。今天不实现完整注册登录。

## 时间安排

- 项目开发：3 小时 20 分钟，完成工程、配置、数据库脚本和前后端连通。
- 技术学习：1 小时 10 分钟，理解 Spring Boot、Maven、Spring MVC 和 MyBatis-Plus 的职责。
- 原理理解：50 分钟，梳理启动流程、请求链路和依赖注入。
- 面试总结：20 分钟，整理 3 个基础问题。

## 今日学习知识

- Maven 的 `pom.xml`、依赖、插件和标准目录结构。
- Spring Boot 自动配置、Bean 和构造器注入。
- Spring MVC 请求从 Controller 到响应的基本流程。
- MyBatis-Plus 中 Entity、Mapper 和数据库表的关系。
- Vue 3、Vite、TypeScript 工程结构和 Axios 基础。

## 今日开发任务

1. 检查 Java、Maven、MySQL、Node.js 版本并记录结果。
2. 创建 `server` 后端工程，加入 Web、Validation、Security、MySQL、MyBatis-Plus、Swagger 和测试依赖。
3. 创建 `web` Vue 3 + TypeScript 工程，加入 Router、Pinia、Axios 和 Element Plus。
4. 配置 `application.yml` 和开发环境配置，敏感值从环境变量读取。
5. 建立 `Controller -> Service -> Mapper -> Database` 包结构和公共模块目录。
6. 设计 `user`、`role`、`user_role` 表，明确主键、唯一约束、状态和审计字段。
7. 编写版本化用户表 SQL 脚本，但今天不实现用户业务代码。
8. 实现最小健康检查接口，并从 Vue 页面请求该接口。
9. 启动 Swagger UI，确认后端接口文档可访问。
10. 完成自检后更新 `docs/DEVELOPMENT_SUMMARY.md`，提交用户 Review。

## 文件创建计划

| 文件或目录 | 作用 |
|---|---|
| `server/pom.xml` | 后端依赖和构建配置 |
| `server/src/main/java/com/study/community/CommunityApplication.java` | Spring Boot 启动类 |
| `server/src/main/java/com/study/community/common/controller/HealthController.java` | 最小连通性接口 |
| `server/src/main/resources/application.yml` | 公共配置和环境变量占位 |
| `server/src/main/resources/application-dev.yml` | 本地开发配置 |
| `server/src/main/resources/db/schema/V001__create_user_tables.sql` | 用户与角色表结构 |
| `web/src/router/index.ts` | Vue 路由 |
| `web/src/api/http.ts` | Axios 实例 |
| `web/src/views/HomeView.vue` | 健康检查展示页 |

## 代码生成提示词

使用顺序：功能点 1 Review 通过后才能使用功能点 2，功能点 2 Review 通过后才能使用功能点 3。

### 功能点 1：后端工程与健康检查

```text
使用 $spring-vue-learning-coach，执行 Day 1 功能点 1。先检查仓库，只创建 Java 17 + Spring Boot 3 + Maven 后端骨架，配置 Spring MVC、Validation、MyBatis-Plus、MySQL、Spring Security、Swagger 和测试依赖，实现最小健康检查接口并确保 Swagger 可访问。暂不创建用户业务类、数据库表和 Vue 代码。开始编码前说明依赖作用、目录设计、修改文件和验收标准；完成后运行后端测试或启动验证，更新 Day 1 文档和 DEVELOPMENT_SUMMARY.md，给出学习建议，将本批次标记为“待用户 Review”，然后停止，不执行功能点 2，也不提交 Git。
```

### 功能点 2：用户表设计与数据库脚本

```text
使用 $spring-vue-learning-coach，执行 Day 1 功能点 2。前提是功能点 1 已由我确认。只设计 user、role、user_role 三张表，说明字段、主键、唯一约束、索引、状态和审计字段，并生成版本化 MySQL SQL 脚本；可以补充必要配置，但不要生成 User Entity、Mapper、注册登录接口或 Vue 页面。完成后验证 SQL 语法和表关系，更新 Day 1 文档和 DEVELOPMENT_SUMMARY.md，给出数据库设计学习建议，标记“待用户 Review”并停止，不执行功能点 3。
```

### 功能点 3：Vue 工程与前后端连通

```text
使用 $spring-vue-learning-coach，执行 Day 1 功能点 3。前提是功能点 2 已由我确认。只创建 Vue 3 + TypeScript + Vite 前端骨架，配置 Router、Pinia、Axios、Element Plus、开发环境 API 地址和错误处理，在首页调用后端健康检查接口。不要实现注册登录页面。完成后运行前端构建或类型检查，并验证前后端连通；更新 Day 1 文档和 DEVELOPMENT_SUMMARY.md，给出 Vue 与后端联调学习建议，标记“待用户 Review”并停止。等待我确认后再考虑当天 Git commit。
```

## 功能点 1 执行记录

- 执行日期：2026-07-20
- 状态：已确认
- 完成内容：创建 Java 17、Spring Boot 3.4.5、Maven 后端骨架；配置 Spring MVC、Validation、MyBatis-Plus、MySQL、Spring Security、Swagger 和测试依赖；实现健康检查接口。
- 接口：`GET /api/health`
- Swagger：`/swagger-ui/index.html`，OpenAPI JSON 为 `/v3/api-docs`。
- 测试：`mvn -B clean test` 通过，3 个集成测试全部成功。
- 运行验收：健康检查返回 `UP`，OpenAPI `3.1.0`，Swagger UI 返回 HTTP 200。
- 当前限制：尚未创建数据库表、Mapper、用户业务和 Vue；MyBatis-Plus 启动时提示未发现 Mapper，符合当前功能点范围。
- Review 重点：依赖版本与范围、临时 Security 全放行策略、健康检查 DTO、配置中的环境变量、测试覆盖。
- Review 结果：2026-07-20 用户确认功能点 1 通过，同意进入功能点 2。

## 功能点 2 执行记录

- 执行日期：2026-07-20
- 状态：已确认
- 完成内容：设计 `user`、`role`、`user_role` 三张表并生成 MySQL 8 版本化 SQL 脚本；修正开发环境默认数据库端口 `330 -> 3306`。
- 脚本：`server/src/main/resources/db/schema/V001__create_user_tables.sql`
- 业务代码：未创建 Entity、Mapper、Service、注册登录接口或 Vue 页面。

### 表设计

| 表 | 主要字段 | 主键与唯一约束 | 索引与关系 |
|---|---|---|---|
| `user` | 用户名、邮箱、BCrypt 密码哈希、昵称、头像、简介、状态、创建/更新时间 | 自增主键；用户名和邮箱分别唯一 | 创建时间普通索引；状态限制为 `0/1` |
| `role` | 角色编码、名称、描述、状态、创建/更新时间 | 自增主键；角色编码唯一 | 状态限制为 `0/1` |
| `user_role` | 用户 ID、角色 ID、授权时间 | `(user_id, role_id)` 联合主键，天然阻止重复授权 | 角色 ID 反向查询索引；两个外键均支持级联删除 |

### 验证结果

- 在隔离的 MySQL 8.0.43 临时实例中完整执行脚本，成功创建 3 张表、2 个外键和 2 个 CHECK 约束。
- 成功插入合法用户、角色及关联记录。
- 重复用户名、非法状态 `2`、重复用户角色关系和不存在的用户外键均被数据库拒绝。
- 删除用户后，对应 `user_role` 记录剩余数量为 `0`，级联删除符合设计。
- 后端回归测试：`mvn -B test` 构建成功，3 个测试全部通过。
- Review 重点：表名使用反引号处理 MySQL 关键字、唯一约束能否支持注册幂等、联合主键是否适合关联表、外键级联删除的边界、审计字段是否够用。
- Review 结果：2026-07-20 用户确认功能点 2 通过，同意进入功能点 3。

### 学习建议

1. 手动画出 `user 1 -> N user_role N <- 1 role`，并说明为什么用户与角色是多对多关系。
2. 分清唯一约束、普通索引和外键：它们分别解决数据不重复、查询性能和引用完整性问题。
3. 尝试解释为什么数据库约束不能只由 Java 参数校验替代：并发请求或其他写入入口仍可能绕过应用层检查。

### 面试表达

用户与角色使用中间表建立多对多关系，联合主键保证同一用户不会被重复授予同一角色；用户名、邮箱和角色编码由数据库唯一约束兜底。状态使用 CHECK 限定取值，关联表使用外键维护引用完整性，并为从角色反查用户的查询方向增加索引。

## 功能点 3 执行记录

- 执行日期：2026-07-21
- 状态：已确认
- 完成内容：创建 Vue 3 + TypeScript + Vite 前端骨架，接入 Vue Router、Pinia、Axios 和 Element Plus；首页调用后端健康检查接口并覆盖加载、成功、失败和重试状态。
- 请求链路：`HomeView -> health.ts -> Axios -> /api/health -> Vite proxy -> Spring Boot:8080`。
- 环境配置：前端开发端口为 `3000`；`VITE_API_BASE_URL=/api`；代理目标默认为 `http://localhost:8080`。
- 业务范围：未创建注册、登录或其他用户业务页面，未修改后端接口与数据库结构。
- 依赖问题：TypeScript 7 与 `vue-tsc 3.3.7` 的编译入口不兼容，已将 TypeScript 固定为兼容的 `5.9.3` 并更新锁文件。
- 构建优化：Element Plus 从全量注册改为按需导入，构建后的 JavaScript 从约 `1038 KB` 降至约 `186 KB`。
- 验证结果：`pnpm build` 通过 TypeScript 检查和 Vite 生产构建；通过 `http://127.0.0.1:3000/api/health` 实际返回 `UP / community-server`。
- 当前环境：使用工作区提供的 Node.js 24.14.0 和 pnpm 11.9.0 完成验证；系统 PATH 尚未安装 Node.js，日常开发前仍需安装 Node.js LTS。
- Review 重点：API 请求是否集中、错误状态是否完整、Vite 代理与 Axios `baseURL` 的职责、Pinia 是否避免保存页面局部状态、Element Plus 是否按需加载。
- Review 结果：2026-07-21 用户确认功能点 3 通过，同意进入 Day 2。

### 学习建议

1. 沿着 `onMounted -> loadHealth -> getHealth -> http.get -> Vite proxy -> Controller` 手动画一次调用链。
2. 分清构建工具 Vite、UI 框架 Vue、路由 Vue Router、状态库 Pinia 和 HTTP 客户端 Axios 的职责。
3. 分别关闭后端和重新启动后端，观察错误提示及“重新检查”按钮，理解前端为什么必须处理失败路径。

### 面试表达

前端采用 API 模块统一管理 Axios，页面只负责状态和交互；开发环境使用 Vite 代理把同源 `/api` 请求转发到 Spring Boot 8080，从而完成本地联调并避免为开发环境散落跨域配置。Pinia 已接入应用，但健康状态属于页面局部数据，因此使用 `ref` 而不是存入全局状态。

## 原理学习

1. Spring Boot 为什么能通过启动类和依赖自动配置 Web 应用。
  @L:   Spring Boot 用 @SpringBootApplication 开启自动配置和组件扫描；再根据 classpath 上的 starter（如 spring-boot-starter-web）条件装配内嵌 Tomcat、DispatcherServlet、Jackson 等。开发者只需声明依赖和业务 Controller，不必手写传统 XML/Servlet 初始化。
3. IOC 容器为什么比手动 `new` 更适合管理企业项目对象。
@L:  手动 new 适合小脚本；企业项目对象多、依赖复杂、要可测可替换。IOC 把创建和装配交给容器，业务类只关心协作关系，便于统一生命周期、替换实现、挂事务/安全和写单测。
面试可补一句：IOC 解决「怎么创建」；DI（依赖注入）是手段；AOP 等能力通常也建立在「对象由容器管理」之上。
4. 一次 HTTP 请求如何经过 DispatcherServlet、Controller 并返回 JSON。
@L: dependencies（直接依赖）直接引入并下载指定的 JAR 包到当前项目中。
dependencyManagement（依赖管理）仅仅是一个“版本控制字典”或“采购目录”。它不会真正下载任何 JAR 包。


## 面试问题

问题：Spring Boot 和 Spring Framework 的关系是什么？  
回答：Spring Boot 基于 Spring，通过自动配置、Starter 和内嵌服务器降低配置成本；核心 IOC、AOP 等能力仍来自 Spring。  
追问：自动配置是否意味着不能自定义配置？

问题：为什么企业项目需要 Controller、Service、Mapper 分层？  
回答：分层隔离协议处理、业务规则和数据访问，便于测试、复用和修改，避免一个类同时承担多个职责。  
追问：简单 CRUD 是否也必须建立 Service？
@L:  简单 CRUD 必须建立 Service 层，这是为了守住架构的底线（职责分离、事务管理、集中校验）。但在小体量项目中，你可以采用“Just Enough Architecture（刚好足够的架构）”原则，省略 Service 接口，直接写实现类，在规范与效率之间找到最佳平衡。

问题：Maven 的作用是什么？  
回答：统一管理依赖、生命周期、插件和构建产物，使不同环境可以重复构建项目。  
追问：`dependencyManagement` 和 `dependencies` 有什么区别？

## 验收标准

- Java 17、Maven、MySQL 和 Node.js 版本检查通过。
- 后端和 Vue 项目均可启动，启动过程无未处理异常。
- Vue 能成功显示健康检查接口返回结果。
- Swagger UI 可访问并展示健康检查接口。
- 完成 3 张用户相关表的设计和 1 个版本化 SQL 文件。
- 更新开发总结，代码处于待用户确认状态。

## Git提交建议

用户确认后执行一次提交：

```text
chore: initialize community backend and frontend
```

## 今日总结模板

```text
实际用时：
完成内容：
未完成内容：
遇到问题：
问题原因：
解决方法：
学到知识：
请求调用链：
面试表达：
明日调整：
用户确认结果：
``` 

# 笔记
## 代理
proxy: {
        "/api": {
          target: env.VITE_DEV_PROXY_TARGET || "http://localhost:8080",
          changeOrigin: true,
        },
      },
代理只在 vite 开发服生效（pnpm dev）。pnpm build 后的静态资源没有这个 proxy，生产要自己配 Nginx/网关，或把 API 指到真实域名。
changeOrigin: true 在多数后端场景建议开启；本项目后端在本机时通常也能正常工作。
changeOrigin 写在 server.proxy 里，只作用于 Vite 开发服务器（pnpm dev）。pnpm build 之后产物是静态文件，线上一般由 Nginx / 网关 / CDN 提供，不会再跑这段 Vite proxy，所以这段配置生产里等于不存在。
环境	              谁做转发	                              类似能力
开发           Vite server.proxy                        changeOrigin: true
生产            Nginx / 网关等                          proxy_set_header Host ... 等

浏览器  ──请求──►  Vite(:3000)  ──再请求──►  后端(:8080)
                        ↑                                              ↑
 浏览器只参与这一段          这是服务器之间的转发
8080 是 Vite 进程自己去连的，属于服务端转发，不是浏览器发的跨域请求。CORS 只约束浏览器里的跨域，不约束服务器之间互相调。
rewrite 是代理里的路径改写：把浏览器请求的 URL 路径改一下，再转发给后端。前后端库路径不一致时需要改路径。
## 数据库
AUTO_INCREMENT  自增
COMMENT   注释
TINYINT UNSIGNED：极小整数（范围 0-255），非常适合用来存储状态值（如 0 和 1），比 INT 节省大量空间。
DEFAULT 1：设置默认值为 1。如果插入数据时不传 status，数据库会自动将其设为 1（启用状态）。   
DATETIME(3)：毫秒级精度的时间类型。
DEFAULT CURRENT_TIMESTAMP(3)：记录创建时间。插入数据时自动填入当前时间。
ON UPDATE CURRENT_TIMESTAMP(3)：极其好用的特性。每次执行 UPDATE 语句修改该行数据时，MySQL 会自动将 updated_at 更新为当前时间，无需在 Java 代码里手动赋值。
PRIMARY KEY  主键 ，唯一。一张表只能有 1 个，绝对不允许为 NULL。
UNIQUE KEY    唯一索引，提供了主键无法替代的灵活性。一张表可以有多个，允许为 NULL（且多个 NULL 不冲突），加快查询速度。
KEY    可以重复，加快查询速度
FOREIGN KEY   外键
CONSTRAINT `fk_user_role_user`
        FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
声明当前表的 user_id 字段引用了 user 表的 id 字段。这保证了数据的参照完整性（不能给不存在的用户分配角色）。
ON DELETE CASCADE   级联删除。当 user 表中的某个用户被删除时，MySQL 会自动删除 user_role 表中所有包含该 user_id 的记录，防止产生孤儿脏数据。
ENGINE = InnoDB：指定存储引擎为 InnoDB。InnoDB 是 MySQL 默认的引擎，支持事务（ACID）、行级锁和外键约束。
DEFAULT CHARACTER SET = utf8mb4：设置表的默认字符集为 utf8mb4，支持存储完整的 Unicode 字符（包括 Emoji 表情）。
COLLATE = utf8mb4_0900_ai_ci：设置排序规则。这是 MySQL 8.0 引入的基于 Unicode 9.0 的排序规则，ai 表示不区分重音（accent insensitive），ci 表示不区分大小写（case insensitive）。
COMMENT = '...'：为整张表添加注释说明。
## spring boot 
请求实际链路：
浏览器/Vite → http://localhost:8080/api/health
                    ↓
            内嵌 Tomcat（starter-web 自动配）
                    ↓
            DispatcherServlet（MVC 自动配）
                    ↓
            HealthController.health()（@ComponentScan 扫到）
                    ↓
            Jackson 把 HealthResponse 写成 JSON（自动配）
你自己写的只有 Controller；Tomcat、Servlet、JSON 转换都是 starter + 自动配置给的。

Spring Boot、Maven、Spring MVC 和 MyBatis-Plus 的职责
Spring Boot：快速整合工具，负责搭建和管理整个项目（自动配置 Spring MVC 等组件，省去手动配置）。
Spring MVC：Web 层实现框架（处理http请求/响应）。
MyBatis-Plus：房子里的数据库管理员（负责操作数据库）

Maven：仓库管理员（负责下载和管理项目依赖）

                 用户
                  │
             HTTP 请求
                  │
                  ▼
               Tomcat
        	        │
         	        ▼
            Spring MVC
                  │
              Controller
                  │
                  ▼
              Service
                  │
                  ▼
           MyBatis-Plus
                  │
              MyBatis
                  │
                  ▼
               MySQL

Tomcat 是一个 Web 服务器（准确来说，是 Servlet 容器），负责接收浏览器的 HTTP 请求，监听网络端口，解析 HTTP 协议。并把请求交给你的 Java 程序（把请求交给 Spring MVC）处理。把处理结果返回给浏览器。

Spring Boot 是应用框架。Tomcat 是 Web 服务器。Spring Boot 默认会内嵌Tomcat。

JWT（全称 JSON Web Token）是目前互联网应用中最主流的身份验证和信息交换标准。

结合你之前了解的 Tomcat 和 Servlet，JWT 通常用于解决“服务器怎么知道当前请求是谁发的”这个问题。

MockMvc 是 Spring 测试框架（Spring Test）提供的核心组件，专门用于对 Spring MVC 的 Controller 层（Web 层） 进行单元测试。

    mockMvc.perform(请求)       // 1. 执行请求：模拟发送一个 GET/POST 请求
        .andExpect(断言)        // 2. 预期断言：验证返回的状态码、JSON内容是否符合预期
        .andDo(操作);           // 3. 辅助操作：比如把请求和响应的详细信息打印到控制台，方便调试


