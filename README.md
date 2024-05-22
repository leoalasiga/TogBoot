# TogBoot
> 政府类项目的springboot版本
>
> 基于springboot+tdesign的快速开发框架，100%开源免费，学习优秀的项目经验，提升自己，方便大家

## 项目所用技术栈

**后端**

| 技术栈     | 版本  | 备注 |
| ---------- | ----- | ---------- |
| springboot | 2.7.18 |  |
| java       | 8     | jdk |
| spring-security | 2.7.18 | 安全框架 |
| lombok | 1.18.30 | 生成getter/setter，构造函数等 |
| mapstruct | 1.5.5.Final | bean转换 |
|  |             |  |
|  |  |  |



**前端**

| 技术栈  | 版本  | 备注     |
| ------- | ----- | -------- |
| tdesign | 1.9.1 | 前端框架 |
| vue     |       |          |



## 项目目录结构

**后端**

```
TogBoot
├── pom.xml
├── src
│   └── main
│       ├── java
│           └── com.als.tog
│               └── common 常量类
│               └── config 全局配置
│               └── enums 全局枚举
│               └── exception 全局异常
│               └── filter 全局过滤
│               └── generator 代码生成器
│               └── handler 处理器
│               └── log 日志相关
│               └── utils 工具类
│               └── web 业务代码
│                   └── xxx 模块名
│                       └── form 表单类
│                       └── controller 
│                       └── vo 前端展示vo类
│                       └── convert mapstruct转换类
│                       └── dto 数据传输对象
│                       └── entity 实体类，映射数据库（do）
│                       └── service 服务
│                       └── mapper mapper映射
│       └── resources
│           ├── application.yml
│           ├── logback-spring.xml
│           ├── static 静态文件
│           └── templates 模板
|           └── banner.txt
└── target
```



**前端**

```
cqjy_web
├── public
│   └── favicon.ico 图标
├── src
│   └── api 
│   └── assets
│   └── components
│   └── config
│   └── constants
│   └── layouts
│   └── pages
│   └── router
│   └── service
│   └── store
│   └── style
│   └── utils
│   └── App.vue
│   └── interface.ts
│   └── main.jsx
│   └── permission.js
├── .gitignore
├── package.json
```



## 代码编写规范

**Alibaba java coding guidelines**

idea安装插件`Alibaba java coding guidelines`,用阿里巴巴设定的开发规范要求自己



## 目录的命名规范

目录名全部使用小写，`kebab-case`形式命名，如果需要有多个单词表达，使用中划线连接。如`new-page`、`components`。



## 分支规范

- 主干分支 -- `main`
- 功能分支 -- `feature`
- 修复分支 -- `hotfix`

`main`分支只接受通过 `Merge Request `合入功能分支。

为保证提交的记录干净整洁，其他分支合入之前需要先 `rebase main`分支。

**分支命名规则**：`feature/20210401_功能名称`。



## 项目的图片



## 版本

| 版本                        | 内容                      |
|---------------------------|-------------------------|
| [v0.0.1](doc/VERSION1.md) | 廖雪峰那边完成springboot项目的搭建  |
| [v0.0.2](doc/VERSION2.md) | 来源若依，引入连接池等，完成基本的用户登录认证 |
|                           |                         |



# 待办列表

[ToDoList](doc/TODOLIST.md)



# bug记录

[bug记录](doc/BUG.md)



# 数据字典

[数据字典](doc/DICT.md)
