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



## Flatten插件的用处

自 Maven 3.5.0-beta-1 开始，可以使用 ${revision}, ${sha1} and/or ${changelist} 这样的变量作为版本占位符

如我们项目的pom.xml中

```xml
   ...
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.als.tog</groupId>
    <artifactId>TogBoot</artifactId>
    <version>${revision}</version>
   ...
```

**可以使用这样的命令：**

```
mvn -Drevision=1.0.0 clean package
```

**缺点：**

Install / Deploy 时，版本占位符将不能被替换。这将导致 Install / Deploy 后， maven 不能识别。

使用 flatten-maven-plugin 解决这个问题。

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>flatten-maven-plugin</artifactId>
            <version>${flatten-maven-plugin.version}</version>
            <configuration>
                <updatePomFile>true</updatePomFile>
                <flattenMode>resolveCiFriendliesOnly</flattenMode>
            </configuration>
            <executions>
                <execution>
                    <id>flatten</id>
                    <phase>process-resources</phase>
                    <goals>
                        <goal>flatten</goal>
                    </goals>
                </execution>
                <execution>
                    <id>flatten-clean</id>
                    <phase>clean</phase>
                    <goals>
                        <goal>clean</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

参考：https://www.jb51.net/program/2920513wz.htm



## 项目打包

我们在Maven的[使用插件](https://www.liaoxuefeng.com/wiki/1252599548343744/1309301217951777)一节中介绍了如何使用`maven-shade-plugin`打包一个可执行的jar包。在Spring Boot应用中，打包更加简单，因为Spring Boot自带一个更简单的`spring-boot-maven-plugin`插件用来打包，我们只需要在`pom.xml`中加入以下配置：

```xml
<project ...>
    ...
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

无需任何配置，Spring Boot的这款插件会自动定位应用程序的入口Class，我们执行以下Maven命令即可打包：

```shell
$ mvn clean package
```

以`springboot-exec-jar`项目为例，打包后我们在`target`目录下可以看到两个jar文件：

```shell
$ ls
classes
generated-sources
maven-archiver
maven-status
TogBoot-1.0.0-snapshot.jar
TogBoot-1.0.0-SNAPSHOT.jar.original
```

其中，`springboot-exec-jar-1.0-SNAPSHOT.jar.original`是Maven标准打包插件打的jar包，它只包含我们自己的Class，不包含依赖，而`springboot-exec-jar-1.0-SNAPSHOT.jar`是Spring Boot打包插件创建的包含依赖的jar，可以直接运行：

```shell
$ java -jar springboot-exec-jar-1.0-SNAPSHOT.jar
```

这样，部署一个Spring Boot应用就非常简单，无需预装任何服务器，只需要上传jar包即可。

在打包的时候，因为打包后的Spring Boot应用不会被修改，因此，默认情况下，`spring-boot-devtools`这个依赖不会被打包进去。但是要注意，使用早期的Spring Boot版本时，需要配置一下才能排除`spring-boot-devtools`这个依赖：

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <excludeDevtools>true</excludeDevtools>
    </configuration>
</plugin>
```

如果不喜欢默认的项目名+版本号作为文件名，可以加一个配置指定文件名：

```xml
<project ...>
    ...
    <build>
        <finalName>togboot</finalName>
        ...
    </build>
</project>
```

这样打包后的文件名就是`togboot.jar`。



## 使用Actuator

在生产环境中，需要对应用程序的状态进行监控。前面我们已经介绍了使用JMX对Java应用程序包括JVM进行监控，使用JMX需要把一些监控信息以MBean的形式暴露给JMX Server，而Spring Boot已经内置了一个监控功能，它叫Actuator。

使用Actuator非常简单，只需添加如下依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

然后正常启动应用程序，Actuator会把它能收集到的所有信息都暴露给JMX。此外，Actuator还可以通过URL`/actuator/`挂载一些监控点，例如，输入`http://localhost:8888/tog/actuator/health`，我们可以查看应用程序当前状态：

```
{
    "status": "UP"
}
```

许多网关作为反向代理需要一个URL来探测后端集群应用是否存活，这个URL就可以提供给网关使用。

Actuator默认把所有访问点暴露给JMX，但处于安全原因，只有`health`和`info`会暴露给Web。Actuator提供的所有访问点均在官方文档列出，要暴露更多的访问点给Web，需要在`application.yml`中加上配置：

```yaml
management:
  endpoints:
    web:
      exposure:
        include: info, health, beans, env, metrics
```

要特别注意暴露的URL的安全性，例如，`/actuator/env`可以获取当前机器的所有环境变量，不可暴露给公网。

参考：https://www.liaoxuefeng.com/wiki/1252599548343744/1282386381766689

## 使用Profiles

Profile本身是Spring提供的功能，我们在[使用条件装配](https://www.liaoxuefeng.com/wiki/1252599548343744/1308043874664482)中已经讲到了，Profile表示一个环境的概念，如开发、测试和生产这3个环境：

- dev
- test
- prod

或者按git分支定义master、dev这些环境：

- master
- dev

在启动一个Spring应用程序的时候，可以传入一个或多个环境，例如：

```
-Dspring.profiles.active=test,master
```

大多数情况下，使用一个环境就足够了。

Spring Boot对Profiles的支持在于，可以在`application.yml`中为每个环境进行配置。下面是一个示例配置：

```yaml
server:
  port: 6666 # 定义自己项目启动端口
  servlet:
    context-path: /tog
spring:
  application:
    name: ${APP_NAME:TogBoot}
  profiles:
    active: dev
```

如果我们不指定任何Profile，直接启动应用程序，那么Profile实际上就是`default`，可以从Spring Boot启动日志看出：

```
...
2024-02-21 16:46:50.751  INFO 18784 --- [           main] com.als.tog.TogApplication               : No active profile set, falling back to 1 default profile: "default"
```

上述日志显示未设置Profile，使用默认的Profile为`default`。

要以`test`环境启动，可输入如下命令：

```
$ java -Dspring.profiles.active=test -jar springboot-profiles-1.0-SNAPSHOT.jar

...
2024-02-21 16:47:28.230  INFO 23252 --- [           main] com.als.tog.TogApplication               : The following 1 profile is active: "dev"
...
2024-02-21 16:47:29.518  INFO 23252 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 7777 (http) with context path '/togs'
...
```

从日志看到活动的Profile是`dev`，Tomcat的监听端口是`7777`。

通过Profile可以实现一套代码在不同环境启用不同的配置和功能。假设我们需要一个存储服务，在本地开发时，直接使用文件存储即可，但是，在测试和生产环境，需要存储到云端如S3上，如何通过Profile实现该功能？

首先，我们要定义存储接口`StorageService`：

```java
public interface StorageService {

    // 根据URI打开InputStream:
    InputStream openInputStream(String uri) throws IOException;

    // 根据扩展名+InputStream保存并返回URI:
    String store(String extName, InputStream input) throws IOException;
}
```

本地存储可通过`LocalStorageService`实现：

```java
@Component
@Profile("default")
public class LocalStorageService implements StorageService {
    @Value("${storage.local:/var/static}")
    String localStorageRootDir;

    final Logger logger = LoggerFactory.getLogger(getClass());

    private File localStorageRoot;

    @PostConstruct
    public void init() {
        logger.info("Intializing local storage with root dir: {}", this.localStorageRootDir);
        this.localStorageRoot = new File(this.localStorageRootDir);
    }

    @Override
    public InputStream openInputStream(String uri) throws IOException {
        File targetFile = new File(this.localStorageRoot, uri);
        return new BufferedInputStream(new FileInputStream(targetFile));
    }

    @Override
    public String store(String extName, InputStream input) throws IOException {
        String fileName = UUID.randomUUID().toString() + "." + extName;
        File targetFile = new File(this.localStorageRoot, fileName);
        try (OutputStream output = new BufferedOutputStream(new FileOutputStream(targetFile))) {
            input.transferTo(output);
        }
        return fileName;
    }
}
```

而云端存储可通过`CloudStorageService`实现：

```java
@Component
@Profile("!default")
public class CloudStorageService implements StorageService {
    @Value("${storage.cloud.bucket:}")
    String bucket;

    @Value("${storage.cloud.access-key:}")
    String accessKey;

    @Value("${storage.cloud.access-secret:}")
    String accessSecret;

    final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void init() {
        // TODO:
        logger.info("Initializing cloud storage...");
    }

    @Override
    public InputStream openInputStream(String uri) throws IOException {
        // TODO:
        throw new IOException("File not found: " + uri);
    }

    @Override
    public String store(String extName, InputStream input) throws IOException {
        // TODO:
        throw new IOException("Unable to access cloud storage.");
    }
}
```

注意到`LocalStorageService`使用了条件装配`@Profile("default")`，即默认启用`LocalStorageService`，而`CloudStorageService`使用了条件装配`@Profile("!default")`，即非`default`环境时，自动启用`CloudStorageService`。这样，一套代码，就实现了不同环境启用不同的配置。

参考：https://www.liaoxuefeng.com/wiki/1252599548343744/1282388483112993

## 使用Conditional

使用Profile能根据不同的Profile进行条件装配，但是Profile控制比较糙，如果想要精细控制，例如，配置本地存储，AWS存储和阿里云存储，将来很可能会增加Azure存储等，用Profile就很难实现。

Spring本身提供了条件装配`@Conditional`，但是要自己编写比较复杂的`Condition`来做判断，比较麻烦。Spring Boot则为我们准备好了几个非常有用的条件：

- @ConditionalOnProperty：如果有指定的配置，条件生效；
- @ConditionalOnBean：如果有指定的Bean，条件生效；
- @ConditionalOnMissingBean：如果没有指定的Bean，条件生效；
- @ConditionalOnMissingClass：如果没有指定的Class，条件生效；
- @ConditionalOnWebApplication：在Web环境中条件生效；
- @ConditionalOnExpression：根据表达式判断条件是否生效。

我们以最常用的`@ConditionalOnProperty`为例，把上一节的`StorageService`改写如下。首先，定义配置`storage.type=xxx`，用来判断条件，默认为`local`：

```yaml
storage:
  type: ${STORAGE_TYPE:local}
```

设定为`local`时，启用`LocalStorageService`：

```java
@Component
@ConditionalOnProperty(value = "storage.type", havingValue = "local", matchIfMissing = true)
public class LocalStorageService implements StorageService {
    ...
}
```

设定为`aws`时，启用`AwsStorageService`：

```java
@Component
@ConditionalOnProperty(value = "storage.type", havingValue = "aws")
public class AwsStorageService implements StorageService {
    ...
}
```

设定为`aliyun`时，启用`AliyunStorageService`：

```java
@Component
@ConditionalOnProperty(value = "storage.type", havingValue = "aliyun")
public class AliyunStorageService implements StorageService {
    ...
}
```

注意到`LocalStorageService`的注解，当指定配置为`local`，或者配置不存在，均启用`LocalStorageService`。

可见，Spring Boot提供的条件装配使得应用程序更加具有灵活性。

参考：https://www.liaoxuefeng.com/wiki/1252599548343744/1282386318852129

## 加载配置文件

加载配置文件可以直接使用注解`@Value`，例如，我们定义了一个最大允许上传的文件大小配置：

```yaml
storage:
  local:
    max-size: 102400
```

在某个FileUploader里，需要获取该配置，可使用`@Value`注入：

```java
@Component
public class FileUploader {
    @Value("${storage.local.max-size:102400}")
    int maxSize;

    ...
}
```

在另一个`UploadFilter`中，因为要检查文件的MD5，同时也要检查输入流的大小，因此，也需要该配置：

```java
@Component
public class UploadFilter implements Filter {
    @Value("${storage.local.max-size:100000}")
    int maxSize;

    ...
}
```

多次引用同一个`@Value`不但麻烦，而且`@Value`使用字符串，缺少编译器检查，容易造成多处引用不一致（例如，`UploadFilter`把缺省值误写为`100000`）。

为了更好地管理配置，Spring Boot允许创建一个Bean，持有一组配置，并由Spring Boot自动注入。

假设我们在`application.yml`中添加了如下配置：

```yaml
storage:
  local:
    # 文件存储根目录:
    root-dir: ${STORAGE_LOCAL_ROOT:/var/storage}
    # 最大文件大小，默认100K:
    max-size: ${STORAGE_LOCAL_MAX_SIZE:102400}
    # 是否允许空文件:
    allow-empty: false
    # 允许的文件类型:
    allow-types: jpg, png, gif
```

可以首先定义一个Java Bean，持有该组配置：

```java
public class StorageConfiguration {

    private String rootDir;
    private int maxSize;
    private boolean allowEmpty;
    private List<String> allowTypes;

    // TODO: getters and setters
}
```

保证Java Bean的属性名称与配置一致即可。然后，我们添加两个注解：

```java
@Configuration
@ConfigurationProperties("storage.local")
public class StorageConfiguration {
    ...
}
```

注意到`@ConfigurationProperties("storage.local")`表示将从配置项`storage.local`读取该项的所有子项配置，并且，`@Configuration`表示`StorageConfiguration`也是一个Spring管理的Bean，可直接注入到其他Bean中：

```java
@Component
public class StorageService {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    StorageConfiguration storageConfig;

    @PostConstruct
    public void init() {
        logger.info("Load configuration: root-dir = {}", storageConfig.getRootDir());
        logger.info("Load configuration: max-size = {}", storageConfig.getMaxSize());
        logger.info("Load configuration: allowed-types = {}", storageConfig.getAllowTypes());
    }
}
```

这样一来，引入`storage.local`的相关配置就很容易了，因为只需要注入`StorageConfiguration`这个Bean，这样可以由编译器检查类型，无需编写重复的`@Value`注解。

参考：https://www.liaoxuefeng.com/wiki/1252599548343744/1304267426103329

## 禁用自动配置

Spring Boot大量使用自动配置和默认配置，极大地减少了代码，通常只需要加上几个注解，并按照默认规则设定一下必要的配置即可。例如，配置JDBC，默认情况下，只需要配置一个`spring.datasource`：

```yaml
spring:
  datasource:
    url: jdbc:hsqldb:file:testdb
    username: sa
    password:
    dirver-class-name: org.hsqldb.jdbc.JDBCDriver
```

Spring Boot就会自动创建出`DataSource`、`JdbcTemplate`、`DataSourceTransactionManager`，非常方便。

但是，有时候，我们又必须要禁用某些自动配置。例如，系统有主从两个数据库，而Spring Boot的自动配置只能配一个，怎么办？

这个时候，针对`DataSource`相关的自动配置，就必须关掉。我们需要用`exclude`指定需要关掉的自动配置：

```java
@SpringBootApplication
// 启动自动配置，但排除指定的自动配置:
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class Application {
    ...
}
```

现在，Spring Boot不再给我们自动创建`DataSource`、`JdbcTemplate`和`DataSourceTransactionManager`了，要实现主从数据库支持，怎么办？

让我们一步一步开始编写支持主从数据库的功能。首先，我们需要把主从数据库配置写到`application.yml`中，仍然按照Spring Boot默认的格式写，但`datasource`改为`datasource-master`和`datasource-slave`：

```yaml
spring:
  datasource-master:
    url: jdbc:hsqldb:file:testdb
    username: sa
    password:
    dirver-class-name: org.hsqldb.jdbc.JDBCDriver
  datasource-slave:
    url: jdbc:hsqldb:file:testdb
    username: sa
    password:
    dirver-class-name: org.hsqldb.jdbc.JDBCDriver
```

注意到两个数据库实际上是同一个库。如果使用MySQL，可以创建一个只读用户，作为`datasource-slave`的用户来模拟一个从库。

下一步，我们分别创建两个HikariCP的`DataSource`：

```java
public class MasterDataSourceConfiguration {
    @Bean("masterDataSourceProperties")
    @ConfigurationProperties("spring.datasource-master")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("masterDataSource")
    DataSource dataSource(@Autowired @Qualifier("masterDataSourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }
}

public class SlaveDataSourceConfiguration {
    @Bean("slaveDataSourceProperties")
    @ConfigurationProperties("spring.datasource-slave")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("slaveDataSource")
    DataSource dataSource(@Autowired @Qualifier("slaveDataSourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }
}
```

注意到上述class并未添加`@Configuration`和`@Component`，要使之生效，可以使用`@Import`导入：

```java
@SpringBootApplication
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@Import({ MasterDataSourceConfiguration.class, SlaveDataSourceConfiguration.class})
public class Application {
    ...
}
```

此外，上述两个`DataSource`的Bean名称分别为`masterDataSource`和`slaveDataSource`，我们还需要一个最终的`@Primary`标注的`DataSource`，它采用Spring提供的`AbstractRoutingDataSource`，代码实现如下：

```java
class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        // 从ThreadLocal中取出key:
        return RoutingDataSourceContext.getDataSourceRoutingKey();
    }
}
```

`RoutingDataSource`本身并不是真正的`DataSource`，它通过Map关联一组`DataSource`，下面的代码创建了包含两个`DataSource`的`RoutingDataSource`，关联的key分别为`masterDataSource`和`slaveDataSource`：

```java
public class RoutingDataSourceConfiguration {
    @Primary
    @Bean
    DataSource dataSource(
            @Autowired @Qualifier("masterDataSource") DataSource masterDataSource,
            @Autowired @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        var ds = new RoutingDataSource();
        // 关联两个DataSource:
        ds.setTargetDataSources(Map.of(
                "masterDataSource", masterDataSource,
                "slaveDataSource", slaveDataSource));
        // 默认使用masterDataSource:
        ds.setDefaultTargetDataSource(masterDataSource);
        return ds;
    }

    @Bean
    JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    DataSourceTransactionManager dataSourceTransactionManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
```

仍然需要自己创建`JdbcTemplate`和`PlatformTransactionManager`，注入的是标记为`@Primary`的`RoutingDataSource`。

这样，我们通过如下的代码就可以切换`RoutingDataSource`底层使用的真正的`DataSource`：

```java
RoutingDataSourceContext.setDataSourceRoutingKey("slaveDataSource");
jdbcTemplate.query(...);
```

只不过写代码切换DataSource即麻烦又容易出错，更好的方式是通过注解配合AOP实现自动切换，这样，客户端代码实现如下：

```java
@Controller
public class UserController {
	@RoutingWithSlave // <-- 指示在此方法中使用slave数据库
	@GetMapping("/profile")
	public ModelAndView profile(HttpSession session) {
        ...
    }
}
```

实现上述功能需要编写一个`@RoutingWithSlave`注解，一个AOP织入和一个`ThreadLocal`来保存key。由于代码比较简单，这里我们不再详述。

如果我们想要确认是否真的切换了`DataSource`，可以覆写`determineTargetDataSource()`方法并打印出`DataSource`的名称：

```
class RoutingDataSource extends AbstractRoutingDataSource {
    ...

    @Override
    protected DataSource determineTargetDataSource() {
        DataSource ds = super.determineTargetDataSource();
        logger.info("determin target datasource: {}", ds);
        return ds;
    }
}
```

访问不同的URL，可以在日志中看到两个`DataSource`，分别是`HikariPool-1`和`hikariPool-2`：

```
2020-06-14 17:55:21.676  INFO 91561 --- [nio-8080-exec-7] c.i.learnjava.config.RoutingDataSource   : determin target datasource: HikariDataSource (HikariPool-1)
2020-06-14 17:57:08.992  INFO 91561 --- [io-8080-exec-10] c.i.learnjava.config.RoutingDataSource   : determin target datasource: HikariDataSource (HikariPool-2)
```

我们用一个图来表示创建的DataSource以及相关Bean的关系：

```ascii
┌────────────────────┐       ┌──────────────────┐
│@Primary            │<──────│   JdbcTemplate   │
│RoutingDataSource   │       └──────────────────┘
│ ┌────────────────┐ │       ┌──────────────────┐
│ │MasterDataSource│ │<──────│DataSource        │
│ └────────────────┘ │       │TransactionManager│
│ ┌────────────────┐ │       └──────────────────┘
│ │SlaveDataSource │ │
│ └────────────────┘ │
└────────────────────┘
```

注意到`DataSourceTransactionManager`和`JdbcTemplate`引用的都是`RoutingDataSource`，所以，这种设计的一个限制就是：在一个请求中，一旦切换了内部数据源，在同一个事务中，不能再切到另一个，否则，`DataSourceTransactionManager`和`JdbcTemplate`操作的就不是同一个数据库连接。

参考：https://www.liaoxuefeng.com/wiki/1252599548343744/1282389045149729

## 添加filter

我们在Spring中已经学过了[集成Filter](https://www.liaoxuefeng.com/wiki/1252599548343744/1282384114745378)，本质上就是通过代理，把Spring管理的Bean注册到Servlet容器中，不过步骤比较繁琐，需要配置`web.xml`。

在Spring Boot中，添加一个`Filter`更简单了，可以做到零配置。我们来看看在Spring Boot中如何添加`Filter`。

Spring Boot会自动扫描所有的`FilterRegistrationBean`类型的Bean，然后，将它们返回的`Filter`自动注册到Servlet容器中，无需任何配置。

我们还是以`AuthFilter`为例，首先编写一个`AuthFilterRegistrationBean`，它继承自`FilterRegistrationBean`：

```java
@Component
public class AuthFilterRegistrationBean extends FilterRegistrationBean<Filter> {
    @Autowired
    UserService userService;

    @Override
    public Filter getFilter() {
        setOrder(10);
        return new AuthFilter();
    }

    class AuthFilter implements Filter {
        ...
    }
}
```

`FilterRegistrationBean`本身不是`Filter`，它实际上是`Filter`的工厂。Spring Boot会调用`getFilter()`，把返回的`Filter`注册到Servlet容器中。因为我们可以在`FilterRegistrationBean`中注入需要的资源，然后，在返回的`AuthFilter`中，这个内部类可以引用外部类的所有字段，自然也包括注入的`UserService`，所以，整个过程完全基于Spring的IoC容器完成。

再注意到`AuthFilterRegistrationBean`使用了`setOrder(10)`，因为Spring Boot支持给多个`Filter`排序，数字小的在前面，所以，多个`Filter`的顺序是可以固定的。

我们再编写一个`ApiFilter`，专门过滤`/api/*`这样的URL。首先编写一个`ApiFilterRegistrationBean`

```
@Component
public class ApiFilterRegistrationBean extends FilterRegistrationBean<Filter> {
    @PostConstruct
    public void init() {
        setOrder(20);
        setFilter(new ApiFilter());
        setUrlPatterns(List.of("/api/*"));
    }

    class ApiFilter implements Filter {
        ...
    }
}
```

这个`ApiFilterRegistrationBean`和`AuthFilterRegistrationBean`又有所不同。因为我们要过滤URL，而不是针对所有URL生效，因此，在`@PostConstruct`方法中，通过`setFilter()`设置一个`Filter`实例后，再调用`setUrlPatterns()`传入要过滤的URL列表。

参考：https://www.liaoxuefeng.com/wiki/1252599548343744/1282389221310497






