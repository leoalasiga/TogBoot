# VERSION1

> 第一版本，打算从廖雪峰老师那里，把基本的springboot的内容搞明白，完成一个基本项目的搭建
>
> https://www.liaoxuefeng.com/wiki/1252599548343744/1282386201411617

## 包目录的定义

```ascii
TogBoot
├── pom.xml
├── src
│   └── main
│       ├── java
│       └── resources
│           ├── application.yml
│           ├── logback-spring.xml
│           ├── static
│           └── templates
|           └── banner.txt
└── target
```

+ `src/main/resources/application.yml`:Spring Boot默认的配置文件，它采用[YAML](https://yaml.org/)格式而不是`.properties`格式

+ `src/main/resources/logback-spring.xml`这是Spring Boot的logback配置文件名称（也可以使用`logback.xml`）
+ `static`是静态文件目录
+ `templates`是模板文件目录



## 完成application.yml的配置

```yaml
server:
  port: 8888 # 定义自己项目启动端口
  servlet:
    context-path: tog
spring:
  application:
    name: ${APP_NAME:TogBoot}
```

（1）YAML 是 JSON 的超集，简洁而强大，是一种专门用来书写配置文件的语言，可以替代 application.properties。

（2）在创建一个 SpringBoot 项目时，引入的 spring-boot-starter-web 依赖间接地引入了 snakeyaml 依赖， snakeyaml 会实现对 YAML 配置的解析。

（3）YAML 的使用非常简单，利用缩进来表示层级关系，并且大小写敏感。

这种`${APP_NAME:TogBoot}`意思是，首先从环境变量查找`APP_NAME`，如果环境变量定义了，那么使用环境变量的值，否则，使用默认值`TogBoot`。

这使得我们在开发和部署时更加方便，因为开发时无需设定任何环境变量，直接使用默认值即本地数据库，而实际线上运行的时候，只需要传入环境变量即可：

```
$ APP_NAME=TOG  java -jar xxx.jar
```

参考：https://www.hangge.com/blog/cache/detail_2459.html



## 完成logback-spring.xml的定义

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
<!--    它主要通过<include resource="..." />引入了Spring Boot的一个缺省配置，这样我们就可以引用类似${CONSOLE_LOG_PATTERN}这样的变量。上述配置定义了一个控制台输出和文件输出，可根据需要修改。-->
    <include resource="org/springframework/boot/logging/logback/defaults.xml" />

    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
            <charset>utf8</charset>
        </encoder>
    </appender>

    <appender name="APP_LOG" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <encoder>
            <pattern>${FILE_LOG_PATTERN}</pattern>
            <charset>utf8</charset>
        </encoder>
        <file>app.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.FixedWindowRollingPolicy">
            <maxIndex>1</maxIndex>
            <fileNamePattern>app.log.%i</fileNamePattern>
        </rollingPolicy>
        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <MaxFileSize>1MB</MaxFileSize>
        </triggeringPolicy>
    </appender>

    <root level="INFO">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="APP_LOG" />
    </root>
</configuration>

```



## 定义springboot启动类

在存放源码的`src/main/java`目录中，Spring Boot对Java包的层级结构有一个要求。注意到我们的根package是`com.als.tog`，下面还有`entity`、`service`、`web`等子package。Spring Boot要求`main()`方法所在的启动类必须放到根package下，命名不做要求，这里我们以`TogApplication.java`命名，它的内容如下：

```java
@SpringBootApplication
public class TogApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(TogApplication.class, args);
    }
}
```

启动Spring Boot应用程序只需要一行代码加上一个注解`@SpringBootApplication`，该注解实际上又包含了：

- @SpringBootConfiguration
  - @Configuration
- @EnableAutoConfiguration
  - @AutoConfigurationPackage
- @ComponentScan

这样一个注解就相当于启动了自动配置和自动扫描。



## 创建初始的pom依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.18</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.als.tog</groupId>
    <artifactId>TogBoot</artifactId>
    <version>${revision}</version>
    <name>${project.artifactId}</name>
    <description>TogBoot:To Government springboot cli</description>
    <url>https://github.com/leoalasiga/TogBoot</url>

    <properties>
        <revision>1.0.0-snapshot</revision>
        <java.version>8</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
        <maven-surefire-plugin.version>3.0.0-M5</maven-surefire-plugin.version>
        <maven-compiler-plugin.version>3.8.1</maven-compiler-plugin.version>
        <flatten-maven-plugin.version>1.5.0</flatten-maven-plugin.version>
        <!-- 统一依赖管理 -->
        <spring.boot.version>2.7.18</spring.boot.version>
        <!--工具类相关-->
        <lombok.version>1.18.30</lombok.version>
        <mapstruct.version>1.5.5.Final</mapstruct.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

<!--        <dependency>-->
<!--            <groupId>org.springframework.boot</groupId>-->
<!--            <artifactId>spring-boot-starter-jdbc</artifactId>-->
<!--        </dependency>-->
    </dependencies>

    <build>
        <pluginManagement>
            <plugins>
                <!-- maven-surefire-plugin 插件，用于运行单元测试。 -->
                <!-- 注意，需要使用 3.0.X+，因为要支持 Junit 5 版本 -->
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>${maven-surefire-plugin.version}</version>
                </plugin>
                <!-- maven-compiler-plugin 插件，解决 spring-boot-configuration-processor + Lombok + MapStruct 组合 -->
                <!-- https://stackoverflow.com/questions/33483697/re-run-spring-boot-configuration-annotation-processor-to-update-generated-metada -->
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>${maven-compiler-plugin.version}</version>
                    <configuration>
                        <annotationProcessorPaths>
                            <path>
                                <groupId>org.springframework.boot</groupId>
                                <artifactId>spring-boot-configuration-processor</artifactId>
                                <version>${spring.boot.version}</version>
                            </path>
                            <path>
                                <groupId>org.projectlombok</groupId>
                                <artifactId>lombok</artifactId>
                                <version>${lombok.version}</version>
                            </path>
                            <path>
                                <groupId>org.mapstruct</groupId>
                                <artifactId>mapstruct-processor</artifactId>
                                <version>${mapstruct.version}</version>
                            </path>
                        </annotationProcessorPaths>
                    </configuration>
                </plugin>
                <plugin>
                    <groupId>org.codehaus.mojo</groupId>
                    <artifactId>flatten-maven-plugin</artifactId>
                </plugin>
            </plugins>
        </pluginManagement>

        <plugins>
            <!-- 统一 revision 版本 -->
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>flatten-maven-plugin</artifactId>
                <version>${flatten-maven-plugin.version}</version>
                <configuration>
                    <flattenMode>resolveCiFriendliesOnly</flattenMode>
                    <updatePomFile>true</updatePomFile>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>flatten</goal>
                        </goals>
                        <id>flatten</id>
                        <phase>process-resources</phase>
                    </execution>
                    <execution>
                        <goals>
                            <goal>clean</goal>
                        </goals>
                        <id>flatten.clean</id>
                        <phase>clean</phase>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```



## Banner.txt

我们可以自定义自己的banner，用于替换启动项目时候显示的spring banner

https://www.bootschool.net/ascii

然后将生成的banner.txt放到`src/main/resources/`下



## 启动项目

```txt
 ██████████   ███████     ████████  ██████     ███████     ███████   ██████████
░░░░░██░░░   ██░░░░░██   ██░░░░░░██░█░░░░██   ██░░░░░██   ██░░░░░██ ░░░░░██░░░ 
    ░██     ██     ░░██ ██      ░░ ░█   ░██  ██     ░░██ ██     ░░██    ░██    
    ░██    ░██      ░██░██         ░██████  ░██      ░██░██      ░██    ░██    
    ░██    ░██      ░██░██    █████░█░░░░ ██░██      ░██░██      ░██    ░██    
    ░██    ░░██     ██ ░░██  ░░░░██░█    ░██░░██     ██ ░░██     ██     ░██    
    ░██     ░░███████   ░░████████ ░███████  ░░███████   ░░███████      ░██    
    ░░       ░░░░░░░     ░░░░░░░░  ░░░░░░░    ░░░░░░░     ░░░░░░░       ░░     

2024-02-21 15:30:57.206  INFO 17972 --- [           main] com.als.tog.TogApplication               : Starting TogApplication using Java 1.8.0_202 on DESKTOP-MMH4KQO with PID 17972 (D:\develop\my\TogBoot\target\classes started by Administrator in D:\develop\my\TogBoot)
2024-02-21 15:30:57.208  INFO 17972 --- [           main] com.als.tog.TogApplication               : No active profile set, falling back to 1 default profile: "default"
2024-02-21 15:30:57.622  INFO 17972 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8888 (http)
2024-02-21 15:30:57.626  INFO 17972 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-02-21 15:30:57.626  INFO 17972 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.83]
2024-02-21 15:30:57.690  INFO 17972 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/tog]    : Initializing Spring embedded WebApplicationContext
2024-02-21 15:30:57.690  INFO 17972 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 458 ms
2024-02-21 15:30:57.857  INFO 17972 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8888 (http) with context path '/tog'
2024-02-21 15:30:57.862  INFO 17972 --- [           main] com.als.tog.TogApplication               : Started TogApplication in 0.902 seconds (JVM running for 1.455)
```

Spring Boot自动启动了嵌入式Tomcat，当看到`Started Application in XXX seconds`时，Spring Boot应用启动成功。

然后浏览器输入`localhost:8080`就可以直接访问页面



## AutoConfiguration

自动创建的Bean就是Spring Boot的特色：AutoConfiguration。

当我们引入`spring-boot-starter-web`时，自动创建了：

- `ServletWebServerFactoryAutoConfiguration`：自动创建一个嵌入式Web服务器，默认是Tomcat；
- `DispatcherServletAutoConfiguration`：自动创建一个`DispatcherServlet`；
- `HttpEncodingAutoConfiguration`：自动创建一个`CharacterEncodingFilter`；
- `WebMvcAutoConfiguration`：自动创建若干与MVC相关的Bean。
- ...

Spring Boot大量使用`XxxAutoConfiguration`来使得许多组件被自动化配置并创建，而这些创建过程又大量使用了Spring的Conditional功能。例如，我们观察`JdbcTemplateAutoConfiguration`，它的代码如下：

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ DataSource.class, JdbcTemplate.class })
@ConditionalOnSingleCandidate(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(JdbcProperties.class)
@Import({ JdbcTemplateConfiguration.class, NamedParameterJdbcTemplateConfiguration.class })
public class JdbcTemplateAutoConfiguration {
}
```

当满足条件：

- `@ConditionalOnClass`：在classpath中能找到`DataSource`和`JdbcTemplate`；
- `@ConditionalOnSingleCandidate(DataSource.class)`：在当前Bean的定义中能找到唯一的`DataSource`；

该`JdbcTemplateAutoConfiguration`就会起作用。实际创建由导入的`JdbcTemplateConfiguration`完成：

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(JdbcOperations.class)
class JdbcTemplateConfiguration {
    @Bean
    @Primary
    JdbcTemplate jdbcTemplate(DataSource dataSource, JdbcProperties properties) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcProperties.Template template = properties.getTemplate();
        jdbcTemplate.setFetchSize(template.getFetchSize());
        jdbcTemplate.setMaxRows(template.getMaxRows());
        if (template.getQueryTimeout() != null) {
            jdbcTemplate.setQueryTimeout((int) template.getQueryTimeout().getSeconds());
        }
        return jdbcTemplate;
    }
}
```

创建`JdbcTemplate`之前，要满足`@ConditionalOnMissingBean(JdbcOperations.class)`，即不存在`JdbcOperations`的Bean。

如果我们自己创建了一个`JdbcTemplate`，例如，在`Application`中自己写个方法：

```java
@SpringBootApplication
public class Application {
    ...
    @Bean
    JdbcTemplate createJdbcTemplate(@Autowired DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
```

那么根据条件`@ConditionalOnMissingBean(JdbcOperations.class)`，Spring Boot就不会再创建一个重复的`JdbcTemplate`（因为`JdbcOperations`是`JdbcTemplate`的父类）。

可见，Spring Boot自动装配功能是通过自动扫描+条件装配实现的，这一套机制在默认情况下工作得很好，但是，如果我们要手动控制某个Bean的创建，就需要详细地了解Spring Boot自动创建的原理，很多时候还要跟踪`XxxAutoConfiguration`，以便设定条件使得某个Bean不会被自动创建。

