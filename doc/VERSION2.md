# VERSION2
> 开始搞项目
## springboot整合druid

> Druid连接池是阿里巴巴开源的数据库连接池项目。Druid连接池为监控而生，内置强大的监控功能，监控特性不影响性能。功能强大，能防SQL注入，内置Loging能诊断Hack应用行为。

### 为什么选择druid连接池

| 功能类别           | 功能            | Druid        | HikariCP    | DBCP | Tomcat-jdbc     | C3P0 |
| ------------------ | --------------- | ------------ | ----------- | ---- | --------------- | ---- |
| 性能               | PSCache         | 是           | 否          | 是   | 是              | 是   |
| LRU                | 是              | 否           | 是          | 是   | 是              |      |
| SLB负载均衡支持    | 是              | 否           | 否          | 否   | 否              |      |
| 稳定性             | ExceptionSorter | 是           | 否          | 否   | 否              | 否   |
| 扩展               | 扩展            | Filter       |             |      | JdbcIntercepter |      |
| 监控               | 监控方式        | jmx/log/http | jmx/metrics | jmx  | jmx             | jmx  |
| 支持SQL级监控      | 是              | 否           | 否          | 否   | 否              |      |
| Spring/Web关联监控 | 是              | 否           | 否          | 否   | 否              |      |
|                    | 诊断支持        | LogFilter    | 否          | 否   | 否              | 否   |
| 连接泄露诊断       | logAbandoned    | 否           | 否          | 否   | 否              |      |
| 安全               | SQL防注入       | 是           | 无          | 无   | 无              | 无   |
| 支持配置加密       | 是              | 否           | 否          | 否   | 否              |      |

+ 稳定性是数据库连接池最关键的特性，他不仅是木桶原理中的短板，而是木桶的底部。由于涉及并发和数据库系统的交互，稳定性需要由内置稳定保障机制和长时间大规模验证来保证。
+ Druid连接池内置经过长期反馈验证过的[ExceptionSorter](https://github.com/alibaba/druid/wiki/ExceptionSorter_cn) 。[ExceptionSorter](https://github.com/alibaba/druid/wiki/ExceptionSorter_cn) 的作用是：在数据库服务器重启、网络抖动、连接被服务器关闭等异常情况下，连接发生了不可恢复异常，将连接从连接池中移除，保证连接池在异常发生时情况下正常工作。ExceptionSorter是连接池稳定的关键特性，没有[ExceptionSorter](https://github.com/alibaba/druid/wiki/ExceptionSorter_cn) 的连接池，不能认为是有稳定性保障的连接池。
+ Druid连接池是阿里巴巴内部唯一使用的连接池，在内部数据库相关中间件TDDL/[DRDS](https://www.aliyun.com/product/drds) 都内置使用强依赖了Druid连接池，经过阿里内部数千上万的系统大规模验证，经过历年双十一超大规模并发验证。
+ Druid连接池自从2001年开源以来，在每年的“[开源中国最受欢迎开源软件](https://www.oschina.net/project/top_cn_2017) ”评比中，基本都会排名前十。Druid在github上 star数量超过13000，Fork数量5000，Maven中央仓库月下载量超过30000。Druid连接池在中国社区使用非常广，占据统治地位，社区大规模使用也证明了Druid连接池的稳定。
+ 当连接不够用时，申请链接超时报错，Druid连接池能够报错会告诉你当前RunningSQL有哪些，当前连接池的水平信息。通过错误日志很方便知道系统瓶颈在哪里。
+ Druid连接池最初就是为监控系统采集jdbc运行信息而生的，它内置了[StatFilter](https://github.com/alibaba/druid/wiki/配置_StatFilter) 功能，能采集非常完备的连接池执行信息Druid连接池内置了能和Spring/Servlet关联监控的实现，使得监控Web应用特别方便Druid连接池内置了一个监控页面，提供了非常完备的监控信息，可以快速诊断系统的瓶颈。
  + Druid连接池的监控信息主要是通过[StatFilter](https://github.com/alibaba/druid/wiki/配置_StatFilter) 采集的，采集的信息非常全面，包括SQL执行、并发、慢查、执行时间区间分布等。具体配置可以看这个 [https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_StatFilter](https://github.com/alibaba/druid/wiki/配置_StatFilter)
  + Druid增加StatFilter之后，能采集大量统计信息，同时对性能基本没有影响。StatFilter对CPU和内存的消耗都极小，对系统的影响可以忽略不计。监控不影响性能是Druid连接池的重要特性。
  + 参数化合并监控是基于SQL Parser语法解析实现的，是Druid连接池独一无二的功能。
  + StatFilter能采集到每个SQL的执行次数、返回行数总和、更新行数总和、执行中次数和和最大并发。并发监控的统计是在SQL执行开始对计数器加一，结束后对计数器减一实现的。可以采集到每个SQL的当前并发和采集期间的最大并发。
  + 缺省执行耗时超过3秒的被认为是慢查，统计项中有包括每个SQL的最后发生的慢查的耗时和发生时的参数。
  + 如果SQL执行时抛出了Exception，SQL统计项上会Exception有最后的发生时间、堆栈和Message，根据这些信息可以很容易定位错误原因。
  + SQL监控项上，执行时间、读取行数、更新行数都有区间分布，将耗时分布成8个区间：
    - 0 - 1 耗时0到1毫秒的次数
    - 1 - 10 耗时1到10毫秒的次数
    - 10 - 100 耗时10到100毫秒的次数
    - 100 - 1,000 耗时100到1000毫秒的次数
    - 1,000 - 10,000 耗时1到10秒的次数
    - 10,000 - 100,000 耗时10到100秒的次数
    - 100,000 - 1,000,000 耗时100到1000秒的次数
    - 1,000,000 - 耗时1000秒以上的次数
  + 内置监控DEMO
+ Druid连接池内置了[LogFilter](https://github.com/alibaba/druid/wiki/配置_LogFilter)，将Connection/Statement/ResultSet相关操作的日志输出，可以用于诊断系统问题，也可以用于Hack一个不熟悉的系统。
+ SQL注入攻击是黑客对数据库进行攻击的常用手段，Druid连接池内置了[WallFilter](https://github.com/alibaba/druid/wiki/简介_WallFilter) 提供防SQL注入功能，在不影响性能的同时防御SQL注入攻击。



**总结**

druid是经过大规模落地实践的，在监控等方面，遥遥领先，性能方面虽然不及hikari，但是瑕不掩瑜，值得使用。



### DruidDataSource配置属性列表

DruidDataSource配置兼容DBCP，但个别配置的语意有所区别。

| 配置                                      | 缺省值             | 说明                                                         |
| ----------------------------------------- | ------------------ | ------------------------------------------------------------ |
| name                                      |                    | 配置这个属性的意义在于，如果存在多个数据源，监控的时候可以通过名字来区分开来。如果没有配置，将会生成一个名字，格式是："DataSource-" + System.identityHashCode(this). 另外配置此属性至少在1.0.5版本中是不起作用的，强行设置name会出错。[详情-点此处](http://blog.csdn.net/lanmo555/article/details/41248763)。 |
| url                                       |                    | 连接数据库的url，不同数据库不一样。例如： mysql : jdbc:mysql://10.20.153.104:3306/druid2 oracle : jdbc:oracle:thin:@10.20.149.85:1521:ocnauto |
| username                                  |                    | 连接数据库的用户名                                           |
| password                                  |                    | 连接数据库的密码。如果你不希望密码直接写在配置文件中，可以使用ConfigFilter。[详细看这里](https://github.com/alibaba/druid/wiki/使用ConfigFilter) |
| driverClassName                           | 根据url自动识别    | 这一项可配可不配，如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName |
| initialSize                               | 0                  | 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时 |
| maxActive                                 | 8                  | 最大连接池数量                                               |
| maxIdle                                   | 8                  | 已经不再使用，配置了也没效果                                 |
| minIdle                                   |                    | 最小连接池数量                                               |
| maxWait                                   |                    | 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。 |
| poolPreparedStatements                    | false              | 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。 |
| maxPoolPreparedStatementPerConnectionSize | -1                 | 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100 |
| validationQuery                           |                    | 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。 |
| validationQueryTimeout                    |                    | 单位：秒，检测连接是否有效的超时时间。底层调用jdbc Statement对象的void setQueryTimeout(int seconds)方法 |
| testOnBorrow                              | true               | 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 |
| testOnReturn                              | false              | 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 |
| testWhileIdle                             | false              | 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。 |
| keepAlive                                 | false （1.0.28）   | 连接池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作。 |
| timeBetweenEvictionRunsMillis             | 1分钟（1.0.14）    | 有两个含义： 1) Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于minEvictableIdleTimeMillis则关闭物理连接。 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明 |
| numTestsPerEvictionRun                    | 30分钟（1.0.14）   | 不再使用，一个DruidDataSource只支持一个EvictionRun           |
| minEvictableIdleTimeMillis                |                    | 连接保持空闲而不被驱逐的最小时间                             |
| connectionInitSqls                        |                    | 物理连接初始化的时候执行的sql                                |
| exceptionSorter                           | 根据dbType自动识别 | 当数据库抛出一些不可恢复的异常时，抛弃连接                   |
| filters                                   |                    | 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有： 监控统计用的filter:stat 日志用的filter:log4j 防御sql注入的filter:wall |
| proxyFilters                              |                    | 类型是List<com.alibaba.druid.filter.Filter>，如果同时配置了filters和proxyFilters，是组合关系，并非替换关系 |



### Druid中使用log4j2进行日志输出

1. **pom.xml中springboot版本依赖**

```xml
<!--Spring-boot中去掉logback的依赖-->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
	<exclusions>
		<exclusion>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-logging</artifactId>
		</exclusion>
	</exclusions>
</dependency>

<!--日志-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>

<!--数据库连接池-->
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>druid-spring-boot-starter</artifactId>
	<version>1.2.2</version>
</dependency>
```

2. **log4j2.xml文件中的日志配置(完整，可直接拷贝使用),这边按照阿里druid的官方配置，优化日志文件**

添加log4j2.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration status="OFF">
    <appenders>

        <Console name="Console" target="SYSTEM_OUT">
            <!--只接受程序中DEBUG级别的日志进行处理-->
            <ThresholdFilter level="DEBUG" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{HH:mm:ss.SSS}] %-5level %class{36} %L %M - %msg%xEx%n"/>
        </Console>

        <!--处理DEBUG级别的日志，并把该日志放到logs/debug.log文件中-->
        <!--打印出DEBUG级别日志，每次大小超过size，则这size大小的日志会自动存入按年份-月份建立的文件夹下面并进行压缩，作为存档-->
        <RollingFile name="RollingFileDebug" fileName="./logs/debug.log"
                     filePattern="logs/$${date:yyyy-MM}/debug-%d{yyyy-MM-dd}-%i.log.gz">
            <Filters>
                <ThresholdFilter level="DEBUG"/>
                <ThresholdFilter level="INFO" onMatch="DENY" onMismatch="NEUTRAL"/>
            </Filters>
            <PatternLayout
                    pattern="[%d{yyyy-MM-dd HH:mm:ss}] %-5level %class{36} %L %M - %msg%xEx%n"/>
            <Policies>
                <SizeBasedTriggeringPolicy size="500 MB"/>
                <TimeBasedTriggeringPolicy/>
            </Policies>
        </RollingFile>

        <!--处理INFO级别的日志，并把该日志放到logs/info.log文件中-->
        <RollingFile name="RollingFileInfo" fileName="./logs/info.log"
                     filePattern="logs/$${date:yyyy-MM}/info-%d{yyyy-MM-dd}-%i.log.gz">
            <Filters>
                <!--只接受INFO级别的日志，其余的全部拒绝处理-->
                <ThresholdFilter level="INFO"/>
                <ThresholdFilter level="WARN" onMatch="DENY" onMismatch="NEUTRAL"/>
            </Filters>
            <PatternLayout
                    pattern="[%d{yyyy-MM-dd HH:mm:ss}] %-5level %class{36} %L %M - %msg%xEx%n"/>
            <Policies>
                <SizeBasedTriggeringPolicy size="500 MB"/>
                <TimeBasedTriggeringPolicy/>
            </Policies>
        </RollingFile>

        <!--处理WARN级别的日志，并把该日志放到logs/warn.log文件中-->
        <RollingFile name="RollingFileWarn" fileName="./logs/warn.log"
                     filePattern="logs/$${date:yyyy-MM}/warn-%d{yyyy-MM-dd}-%i.log.gz">
            <Filters>
                <ThresholdFilter level="WARN"/>
                <ThresholdFilter level="ERROR" onMatch="DENY" onMismatch="NEUTRAL"/>
            </Filters>
            <PatternLayout
                    pattern="[%d{yyyy-MM-dd HH:mm:ss}] %-5level %class{36} %L %M - %msg%xEx%n"/>
            <Policies>
                <SizeBasedTriggeringPolicy size="500 MB"/>
                <TimeBasedTriggeringPolicy/>
            </Policies>
        </RollingFile>

        <!--处理error级别的日志，并把该日志放到logs/error.log文件中-->
        <RollingFile name="RollingFileError" fileName="./logs/error.log"
                     filePattern="logs/$${date:yyyy-MM}/error-%d{yyyy-MM-dd}-%i.log.gz">
            <ThresholdFilter level="ERROR"/>
            <PatternLayout
                    pattern="[%d{yyyy-MM-dd HH:mm:ss}] %-5level %class{36} %L %M - %msg%xEx%n"/>
            <Policies>
                <SizeBasedTriggeringPolicy size="500 MB"/>
                <TimeBasedTriggeringPolicy/>
            </Policies>
        </RollingFile>

        <!--druid的日志记录追加器-->
        <RollingFile name="druidSqlRollingFile" fileName="./logs/druid-sql.log"
                     filePattern="logs/$${date:yyyy-MM}/api-%d{yyyy-MM-dd}-%i.log.gz">
            <PatternLayout pattern="[%d{yyyy-MM-dd HH:mm:ss}] %-5level %L %M - %msg%xEx%n"/>
            <Policies>
                <SizeBasedTriggeringPolicy size="500 MB"/>
                <TimeBasedTriggeringPolicy/>
            </Policies>
        </RollingFile>
    </appenders>

    <loggers>
        <root level="DEBUG">
            <appender-ref ref="Console"/>
            <appender-ref ref="RollingFileInfo"/>
            <appender-ref ref="RollingFileWarn"/>
            <appender-ref ref="RollingFileError"/>
            <appender-ref ref="RollingFileDebug"/>
        </root>

        <!--记录druid-sql的记录-->
        <logger name="druid.sql.Statement" level="debug" additivity="false">
            <appender-ref ref="druidSqlRollingFile"/>
        </logger>
        <logger name="druid.sql.Statement" level="debug" additivity="false">
            <appender-ref ref="druidSqlRollingFile"/>
        </logger>

        <!--log4j2 自带过滤日志-->
        <Logger name="org.apache.catalina.startup.DigesterFactory" level="error" />
        <Logger name="org.apache.catalina.util.LifecycleBase" level="error" />
        <Logger name="org.apache.coyote.http11.Http11NioProtocol" level="warn" />
        <logger name="org.apache.sshd.common.util.SecurityUtils" level="warn"/>
        <Logger name="org.apache.tomcat.util.net.NioSelectorPool" level="warn" />
        <Logger name="org.crsh.plugin" level="warn" />
        <logger name="org.crsh.ssh" level="warn"/>
        <Logger name="org.eclipse.jetty.util.component.AbstractLifeCycle" level="error" />
        <Logger name="org.hibernate.validator.internal.util.Version" level="warn" />
        <logger name="org.springframework.boot.actuate.autoconfigure.CrshAutoConfiguration" level="warn"/>
        <logger name="org.springframework.boot.actuate.endpoint.jmx" level="warn"/>
        <logger name="org.thymeleaf" level="warn"/>
    </loggers>
</configuration>
```

+ **Console Appender:**
  - `Console` appender用于将日志输出到控制台。
  - 只接受程序中`DEBUG`级别的日志进行处理。
+ **RollingFile Appenders:**
  - 分别定义了`RollingFileDebug`, `RollingFileInfo`, `RollingFileWarn`, `RollingFileError`, `druidSqlRollingFile`这几个文件追加器。
  - 这些文件追加器分别处理DEBUG、INFO、WARN、ERROR、druidSql级别的日志。
  - 使用`PatternLayout`来定义日志输出格式。
  - 设置了`SizeBasedTriggeringPolicy`，当日志文件大小超过500 MB时，进行滚动。
  - 使用`TimeBasedTriggeringPolicy`，按时间进行滚动。
+ **Logger Configuration:**
  - 针对不同的Logger进行配置，例如`druid.sql.Statement`的日志会被记录到`druidSqlRollingFile`文件追加器中。
  - 自带的过滤日志，例如`org.apache.catalina.startup.DigesterFactory`的日志级别被设置为`error`。
+ **Root Logger:**
  - 根Logger配置，将日志输出到`Console`和不同的文件追加器。

3. **配置application.properties**

```yaml
spring:
    druid: # Druid 【监控】相关的全局配置
        # 配置日志输出
        slf4j:
          enabled: true
          connection-close-after-log-enabled: false
          statement-close-after-log-enabled: false
          result-set-open-after-log-enabled: false
          result-set-close-after-log-enabled: false
```

这个日志输出，有点不太喜欢，还是用之前`logback-spring.xml`的日志输出，现将这部分功能停止掉



### pom文件修改

```xml
 <!--DB 相关-->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>${mysql.version}</version>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>${druid.version}</version>
</dependency>
 <!--补充jdbc连接类型-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>${mybatis-plus.version}</version>
</dependency>
```

### yaml文件修改

```yaml
spring:  
  datasource:
    url: ${DB_URL:jdbc:jdbc:mysql://127.0.0.1:3306/tog?useUnicode=true&characterEncoding=UTF-8&useSSL=false&nullCatalogMeansCurrent=false}
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: ${DB_USER:tog}
    password: ${DB_PWD:Tog@6666}
    druid: # Druid 【监控】相关的全局配置
      web-stat-filter:
        enabled: true
      stat-view-servlet:
        enabled: true
        allow: # 设置白名单，不填则允许所有访问
        url-pattern: /druid/*
        login-username: tog # 控制台管理用户名和密码
        login-password: Tog@6666
      filter:
        stat:
          enabled: true
          log-slow-sql: true # 慢 SQL 记录
          slow-sql-millis: 1000
          merge-sql: true # 合并sql
        wall:
          config:
            multi-statement-allow: true
        # 配置日志输出
        slf4j:
          enabled: true
          connection-close-after-log-enabled: false
          statement-close-after-log-enabled: false
          result-set-open-after-log-enabled: false
          result-set-close-after-log-enabled: false
```



**参考**：

**数据库连接池选型 Druid vs HikariCP性能对比**    https://blog.csdn.net/weixin_39098944/article/details/109228618

**SpringBoot中关于 HikariCP、Druid及常用连接池的比较及druid连接池泄漏问题参考**     https://blog.csdn.net/wh445306/article/details/112057955

**druid连接池介绍**：  https://github.com/alibaba/druid/wiki/Druid%E8%BF%9E%E6%8E%A5%E6%B1%A0%E4%BB%8B%E7%BB%8D

**HikariCP连接池及其在springboot中的配置：**  https://blog.csdn.net/qq_32953079/article/details/81502237





## 引入undertow

### 为什么要选择undertow呢?

+ **性能**：在相同的用例及并发请求下，Undertow稍微比Tomcat和Jetty好一点
+ **资源消耗**:JETY启动时内存占用最大，使用311 MB。Tomcat和Undertow的初始脚印相似，在120 MB左右，Undertow出现在114 MB的最低水平。响应头中的关键差异在于默认情况下默认情况下包括HTTP持久连接。该头将在支持持久连接的客户端中使用，以通过重用连接细节来优化性能。

**结论：**

Undertow在性能和内存使用方面是最好的。令人鼓舞的是，Undertow 正在接受最新的能力，并默认为持久的连接。这些数字并不表示在这个例子中使用的负载有显著的性能差异，但我想它们会缩放，如果性能是最重要的因素，则Undertow 是应用程序的正确匹配。认为一个组织可能因为熟悉它的能力而喜欢嵌入的servlet容器也是合理的。很多时候默认设置将不得不改变，因为应用程序要求包括性能、内存使用和功能。



### pom文件修改

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-undertow</artifactId>
</dependency>
```

### yaml文件编辑

```properties
server.undertow.accesslog.dir= # Undertow access log directory.
server.undertow.accesslog.enabled=false # Enable access log.
server.undertow.accesslog.pattern=common # Format pattern for access logs.
server.undertow.accesslog.prefix=access_log. # Log file name prefix.
server.undertow.accesslog.rotate=true # Enable access log rotation.
server.undertow.accesslog.suffix=log # Log file name suffix.
server.undertow.buffer-size= # Size of each buffer in bytes.
server.undertow.buffers-per-region= # Number of buffer per region.
server.undertow.direct-buffers= # Allocate buffers outside the Java heap.
server.undertow.io-threads= # Number of I/O threads to create for the worker.
server.undertow.max-http-post-size=0 # Maximum size in bytes of the HTTP post content.
server.undertow.worker-threads= # Number of worker threads.
```

```properties
# 设置IO线程数, 它主要执行非阻塞的任务,它们会负责多个连接, 默认设置每个CPU核心一个线程
# 不要设置过大，如果过大，启动项目会报错：打开文件数过多
server.undertow.io-threads=16

# 阻塞任务线程池, 当执行类似servlet请求阻塞IO操作, undertow会从这个线程池中取得线程
# 它的值设置取决于系统线程执行任务的阻塞系数，默认值是IO线程数*8
server.undertow.worker-threads=256

# 以下的配置会影响buffer,这些buffer会用于服务器连接的IO操作,有点类似netty的池化内存管理
# 每块buffer的空间大小,越小的空间被利用越充分，不要设置太大，以免影响其他应用，合适即可
server.undertow.buffer-size=1024

# 每个区分配的buffer数量 , 所以pool的大小是buffer-size * buffers-per-region
server.undertow.buffers-per-region=1024

# 是否分配的直接内存(NIO直接分配的堆外内存)
server.undertow.direct-buffers=true
```

完成后启动项目，会发现

```
...
2024-02-29 09:27:13.522  INFO 131556 --- [main] o.s.b.a.e.w.EndpointLinksResolver: Exposing 5 endpoint(s) beneath base path '/actuator'
2024-02-29 09:27:13.561  INFO 131556 --- [main] i.undertow: starting server: Undertow - 2.2.28.Final
...
```



**参考：**

https://blog.51cto.com/u_12660945/5162703



## 引入mybatis-plus

### 特性

- **无侵入**：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑
- **损耗小**：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作
- **强大的 CRUD 操作**：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
- **支持 Lambda 形式调用**：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错
- **支持主键自动生成**：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配置，完美解决主键问题
- **支持 ActiveRecord 模式**：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大的 CRUD 操作
- **支持自定义全局通用操作**：支持全局通用方法注入（ Write once, use anywhere ）
- **内置代码生成器**：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用
- **内置分页插件**：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询
- **分页插件支持多种数据库**：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer 等多种数据库
- **内置性能分析插件**：可输出 SQL 语句以及其执行时间，建议开发测试时启用该功能，能快速揪出慢查询
- **内置全局拦截插件**：提供全表 delete 、 update 操作智能分析阻断，也可自定义拦截规则，预防误操作



### 支持数据库

> 任何能使用 `MyBatis` 进行 CRUD, 并且支持标准 SQL 的数据库，具体支持情况如下，如果不在下列表查看分页部分教程 PR 您的支持。

- MySQL，Oracle，DB2，H2，HSQL，SQLite，PostgreSQL，SQLServer，Phoenix，Gauss ，ClickHouse，Sybase，OceanBase，Firebird，Cubrid，Goldilocks，csiidb，informix，TDengine，redshift
- 达梦数据库，虚谷数据库，人大金仓数据库，南大通用(华库)数据库，南大通用数据库，神通数据库，瀚高数据库，优炫数据库，星瑞格数据库



### 框架结构

![mybatis-plus框架](../img/mybatis-plus-framework.jpg)



### pom文件修改

```xml
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.5.5</version>
    </dependency>
```

### 配置

在 Spring Boot 启动类中添加 `@MapperScan` 注解，扫描 Mapper 文件夹：

```java
@SpringBootApplication
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

配置yaml文件

```yaml
spring:  
  datasource:
    url: ${DB_URL:jdbc:jdbc:mysql://127.0.0.1:3306/tog?useUnicode=true&characterEncoding=UTF-8&useSSL=false&nullCatalogMeansCurrent=false}
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: ${DB_USER:tog}
    password: ${DB_PWD:Tog@6666}
```



### 测试

现有一张 `User` 表，其表结构如下：

| id   | name   | age  | email              |
| ---- | ------ | ---- | ------------------ |
| 1    | Jone   | 18   | test1@baomidou.com |
| 2    | Jack   | 20   | test2@baomidou.com |
| 3    | Tom    | 28   | test3@baomidou.com |
| 4    | Sandy  | 21   | test4@baomidou.com |
| 5    | Billie | 24   | test5@baomidou.com |

其对应的数据库 Schema 脚本如下：

```sql
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    id BIGINT NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);
```

其对应的数据库 Data 脚本如下：

```sql
DELETE FROM `user`;

INSERT INTO `user` (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
```

### 编码

编写实体类 `User.java`（此处使用了 [Lombok (opens new window)](https://www.projectlombok.org/)简化代码）

```java
@Data
@TableName("`user`")
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

编写 Mapper 包下的 `UserMapper`接口

```java
public interface UserMapper extends BaseMapper<User> {

}
```

### 开始使用

添加测试类，进行功能测试：

```java
@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }

}
```

提示UserMapper 中的 `selectList()` 方法的参数为 MP 内置的条件封装器 `Wrapper`，所以不填写就是无任何条件控制台输出：

```log
User(id=1, name=Jone, age=18, email=test1@baomidou.com)
User(id=2, name=Jack, age=20, email=test2@baomidou.com)
User(id=3, name=Tom, age=28, email=test3@baomidou.com)
User(id=4, name=Sandy, age=21, email=test4@baomidou.com)
User(id=5, name=Billie, age=24, email=test5@baomidou.com)
```

<font style="color:red">注意，因为我yaml文件里用了占位符`${DB_USER:tog}`,所以我启动项目的时候，需要idea配置`VM options`,配置实际的idea数据库连接地址,配置方法： idea>run>edit configurations>build and run>add vmoptions,然后将实际的连接地址，当做占位符填进去</font>

### 注解

#### @TableName

- 描述：表名注解，标识实体类对应的表
- 使用位置：实体类

```java
@TableName("sys_user")
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

| 属性             | 类型     | 必须指定 | 默认值 | 描述                                                         |
| :--------------- | :------- | :------- | :----- | :----------------------------------------------------------- |
| value            | String   | 否       | ""     | 表名                                                         |
| schema           | String   | 否       | ""     | schema                                                       |
| keepGlobalPrefix | boolean  | 否       | false  | 是否保持使用全局的 tablePrefix 的值（当全局 tablePrefix 生效时） |
| resultMap        | String   | 否       | ""     | xml 中 resultMap 的 id（用于满足特定类型的实体类对象绑定）   |
| autoResultMap    | boolean  | 否       | false  | 是否自动构建 resultMap 并使用（如果设置 resultMap 则不会进行 resultMap 的自动构建与注入） |
| excludeProperty  | String[] | 否       | {}     | 需要排除的属性名 @since 3.3.1                                |

**关于 `autoResultMap` 的说明：**

MP 会自动构建一个 `resultMap` 并注入到 MyBatis 里（一般用不上），请注意以下内容：

因为 MP 底层是 MyBatis，所以 MP 只是帮您注入了常用 CRUD 到 MyBatis 里，注入之前是动态的（根据您的 Entity 字段以及注解变化而变化），但是注入之后是静态的（等于 XML 配置中的内容）。

而对于 `typeHandler` 属性，MyBatis 只支持写在 2 个地方:

1. 定义在 resultMap 里，作用于查询结果的封装
2. 定义在 `insert` 和 `update` 语句的 `#{property}` 中的 `property` 后面（例：`#{property,typehandler=xxx.xxx.xxx}`），并且只作用于当前 `设置值`

除了以上两种直接指定 `typeHandler` 的形式，MyBatis 有一个全局扫描自定义 `typeHandler` 包的配置，原理是根据您的 `property` 类型去找其对应的 `typeHandler` 并使用。

#### @TableId

- 描述：主键注解
- 使用位置：实体类主键字段

```java
@TableName("sys_user")
public class User {
    @TableId
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

| 属性  | 类型   | 必须指定 | 默认值      | 描述         |
| :---- | :----- | :------- | :---------- | :----------- |
| value | String | 否       | ""          | 主键字段名   |
| type  | Enum   | 否       | IdType.NONE | 指定主键类型 |

##### IdType

| 值            | 描述                                                         |
| :------------ | :----------------------------------------------------------- |
| AUTO          | 数据库 ID 自增                                               |
| NONE          | 无状态，该类型为未设置主键类型（注解里等于跟随全局，全局里约等于 INPUT） |
| INPUT         | insert 前自行 set 主键值                                     |
| ASSIGN_ID     | 分配 ID(主键类型为 Number(Long 和 Integer)或 String)(since 3.3.0),使用接口`IdentifierGenerator`的方法`nextId`(默认实现类为`DefaultIdentifierGenerator`雪花算法) |
| ASSIGN_UUID   | 分配 UUID,主键类型为 String(since 3.3.0),使用接口`IdentifierGenerator`的方法`nextUUID`(默认 default 方法) |
| ID_WORKER     | 分布式全局唯一 ID 长整型类型(please use `ASSIGN_ID`)         |
| UUID          | 32 位 UUID 字符串(please use `ASSIGN_UUID`)                  |
| ID_WORKER_STR | 分布式全局唯一 ID 字符串类型(please use `ASSIGN_ID`)         |



## @TableField

- 描述：字段注解（非主键）

```java
@TableName("sys_user")
public class User {
    @TableId
    private Long id;
    @TableField("nickname")
    private String name;
    private Integer age;
    private String email;
}
```

1
2
3
4
5
6
7
8
9

| 属性             | 类型                         | 必须指定 | 默认值                   | 描述                                                         |
| :--------------- | :--------------------------- | :------- | :----------------------- | :----------------------------------------------------------- |
| value            | String                       | 否       | ""                       | 数据库字段名                                                 |
| exist            | boolean                      | 否       | true                     | 是否为数据库表字段                                           |
| condition        | String                       | 否       | ""                       | 字段 `where` 实体查询比较条件，有值设置则按设置的值为准，没有则为默认全局的 `%s=#{%s}`，[参考(opens new window)](https://github.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-annotation/src/main/java/com/baomidou/mybatisplus/annotation/SqlCondition.java) |
| update           | String                       | 否       | ""                       | 字段 `update set` 部分注入，例如：当在version字段上注解`update="%s+1"` 表示更新时会 `set version=version+1` （该属性优先级高于 `el` 属性） |
| insertStrategy   | Enum                         | 否       | FieldStrategy.DEFAULT    | 举例：NOT_NULL `insert into table_a(<if test="columnProperty != null">column</if>) values (<if test="columnProperty != null">#{columnProperty}</if>)` |
| updateStrategy   | Enum                         | 否       | FieldStrategy.DEFAULT    | 举例：IGNORED `update table_a set column=#{columnProperty}`  |
| whereStrategy    | Enum                         | 否       | FieldStrategy.DEFAULT    | 举例：NOT_EMPTY `where <if test="columnProperty != null and columnProperty!=''">column=#{columnProperty}</if>` |
| fill             | Enum                         | 否       | FieldFill.DEFAULT        | 字段自动填充策略                                             |
| select           | boolean                      | 否       | true                     | 是否进行 select 查询                                         |
| keepGlobalFormat | boolean                      | 否       | false                    | 是否保持使用全局的 format 进行处理                           |
| jdbcType         | JdbcType                     | 否       | JdbcType.UNDEFINED       | JDBC 类型 (该默认值不代表会按照该值生效)                     |
| typeHandler      | Class<? extends TypeHandler> | 否       | UnknownTypeHandler.class | 类型处理器 (该默认值不代表会按照该值生效)                    |
| numericScale     | String                       | 否       | ""                       | 指定小数点后保留的位数                                       |



关于`jdbcType`和`typeHandler`以及`numericScale`的说明:

`numericScale`只生效于 update 的 sql. `jdbcType`和`typeHandler`如果不配合`@TableName#autoResultMap = true`一起使用,也只生效于 update 的 sql. 对于`typeHandler`如果你的字段类型和 set 进去的类型为`equals`关系,则只需要让你的`typeHandler`让 Mybatis 加载到即可,不需要使用注解

### [#](https://baomidou.com/pages/223848/#fieldstrategy)[FieldStrategy(opens new window)](https://github.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-annotation/src/main/java/com/baomidou/mybatisplus/annotation/FieldStrategy.java)

| 值                | 描述                                                        |
| :---------------- | :---------------------------------------------------------- |
| IGNORED（已弃用） | 忽略判断，效果等同于"ALWAYS"                                |
| ALWAYS            | 总是加入SQL，无论字段值是否为NULL                           |
| NOT_NULL          | 非 NULL 判断                                                |
| NOT_EMPTY         | 非空判断(只对字符串类型字段,其他类型字段依然为非 NULL 判断) |
| DEFAULT           | 追随全局配置                                                |
| NEVER             | 不加入SQL                                                   |



### [#](https://baomidou.com/pages/223848/#fieldfill)[FieldFill(opens new window)](https://github.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-annotation/src/main/java/com/baomidou/mybatisplus/annotation/FieldFill.java)

| 值            | 描述                 |
| :------------ | :------------------- |
| DEFAULT       | 默认不处理           |
| INSERT        | 插入时填充字段       |
| UPDATE        | 更新时填充字段       |
| INSERT_UPDATE | 插入和更新时填充字段 |



## [#](https://baomidou.com/pages/223848/#version)[@Version(opens new window)](https://github.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-annotation/src/main/java/com/baomidou/mybatisplus/annotation/Version.java)

- 描述：乐观锁注解、标记 `@Version` 在字段上

## [#](https://baomidou.com/pages/223848/#enumvalue)[@EnumValue(opens new window)](https://github.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-annotation/src/main/java/com/baomidou/mybatisplus/annotation/EnumValue.java)

- 描述：普通枚举类注解(注解在枚举字段上)

## [#](https://baomidou.com/pages/223848/#tablelogic)[@TableLogic(opens new window)](https://github.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-annotation/src/main/java/com/baomidou/mybatisplus/annotation/TableLogic.java)

- 描述：表字段逻辑处理注解（逻辑删除）

| 属性   | 类型   | 必须指定 | 默认值 | 描述         |
| :----- | :----- | :------- | :----- | :----------- |
| value  | String | 否       | ""     | 逻辑未删除值 |
| delval | String | 否       | ""     | 逻辑删除值   |



## [#](https://baomidou.com/pages/223848/#sqlparser)[@SqlParser (opens new window)](https://gitee.com/baomidou/mybatis-plus/blob/v3.4.0/mybatis-plus-annotation/src/main/java/com/baomidou/mybatisplus/annotation/SqlParser.java)Deprecated

> see [@InterceptorIgnore](https://baomidou.com/pages/223848/#InterceptorIgnore)

## [#](https://baomidou.com/pages/223848/#keysequence)[@KeySequence(opens new window)](https://github.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-annotation/src/main/java/com/baomidou/mybatisplus/annotation/KeySequence.java)

- 描述：序列主键策略 `oracle`
- 属性：value、dbType

| 属性   | 类型   | 必须指定 | 默认值       | 描述                                                         |
| :----- | :----- | :------- | :----------- | :----------------------------------------------------------- |
| value  | String | 否       | ""           | 序列名                                                       |
| dbType | Enum   | 否       | DbType.OTHER | 数据库类型，未配置默认使用注入 IKeyGenerator 实现，多个实现必须指定 |



## [#](https://baomidou.com/pages/223848/#interceptorignore)[@InterceptorIgnore(opens new window)](https://gitee.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-annotation/src/main/java/com/baomidou/mybatisplus/annotation/InterceptorIgnore.java)

- `value` 值为 `1` | `yes` | `on` 视为忽略，例如 `@InterceptorIgnore(tenantLine = "1")`
- `value` 值为 `0` | `false` | `off` | `空值不变` 视为正常执行。

> see [插件主体](https://baomidou.com/pages/2976a3/)

## [#](https://baomidou.com/pages/223848/#orderby)[@OrderBy(opens new window)](https://gitee.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-annotation/src/main/java/com/baomidou/mybatisplus/annotation/OrderBy.java)

- 描述：内置 SQL 默认指定排序，优先级低于 wrapper 条件查询

| 属性 | 类型    | 必须指定 | 默认值          | 描述           |
| :--- | :------ | :------- | :-------------- | :------------- |
| asc  | boolean | 否       | true            | 是否倒序查询   |
| sort | short   | 否       | Short.MAX_VALUE | 数字越小越靠前 |


**参考**

**mybatis-plus官方文档：** https://baomidou.com/pages/24112f/

