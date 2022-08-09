# Java微服务框架
集成Spring Security OAuth2实现用户名密码、手机短信、微信等多种方式登录，token采用redis存储方式，通过Spring Cloud Gateway网关统一鉴权。

# 系统架构图
![系统架构图](图/系统架构图.jpg)

# 项目组织结构
```
simple-platform
├─common -- 公共模块
│  ├─simple-common-auth -- 认证服务（授权服务器）和网关服务（资源服务器）公共配置模块
│  ├─simple-common-base -- 通用常量和模型代码模块
│  ├─simple-common-core -- Web项目通用核心配置模块
│  └─simple-common-parent -- 统一依赖版本父级模块
├─simple-auth -- 基于 Spring Cloud Oauth2 实现的统一认证服务
├─simple-gateway -- 基于 Spring Cloud Gateway 实现的统一网关服务
├─simple-monitor -- 基于 Spring Boot Admin 实现的应用监控服务
├─simple-user -- 用户、角色、权限管理服务
├─simple-user-api -- 用户服务微服务接口客户端
└─update -- 更新记录
   ├─nacos -- Nacos配置更新记录
   └─sql -- SQL更新脚本
```

# 技术选型
| 技术                   | 说明                 | 官网                                                 |
| ---------------------- | -------------------- | ---------------------------------------------------- |
| Spring Cloud           | 微服务框架           | https://spring.io/projects/spring-cloud              |
| Spring Cloud Alibaba   | 微服务框架           | https://github.com/alibaba/spring-cloud-alibaba      |
| Spring Cloud Gateway   | 微服务网关           | https://spring.io/projects/spring-cloud-gateway      |
| Spring Cloud OpenFeign | 服务远程调用         | https://spring.io/projects/spring-cloud-openfeign    |
| Spring Cloud LoadBalancer | 服务负载均衡         | https://spring.io/guides/gs/spring-cloud-loadbalancer |
| Resilience4j           | 断路器              | https://resilience4j.readme.io/                      |
| Spring Boot            | IOC+AOP+MVC框架     | https://spring.io/projects/spring-boot               |
| Spring Security OAuth2 | 认证授权框架         | https://spring.io/projects/spring-security-oauth     |
| MyBatis                | ORM框架             | http://www.mybatis.org/mybatis-3/zh/index.html       |
| Redisson               | Redis客户端         | https://github.com/redisson/redisson                 |
| Hutool                 | 工具类库             | https://hutool.cn/docs                               |
| Caffeine               | 本地缓存             | https://github.com/ben-manes/caffeine                |
| Spring Boot Admin      | 服务监控             | https://github.com/codecentric/spring-boot-admin     |
| Smart-Doc              | 接口文档生成工具      | https://smart-doc-group.github.io/#/zh-cn/           |
| Nacos                  | 服务注册和配置中心    | https://nacos.io/zh-cn/                               |
| Sentinel               | 网关流控降级         | https://github.com/alibaba/Sentinel/                  |
| MySQL                  | 数据库              | https://www.mysql.com/                                |
| Redis                  | 分布式缓存           | https://redis.io/                                    |
| RabbitMQ               | 消息队列             | https://www.rabbitmq.com/                            |

> Spring Cloud Alibaba,Spring Cloud,Spring Boot 版本选择参考：[Spring Cloud Alibaba 版本说明](https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E)
