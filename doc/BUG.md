# BUG登记
## 1.springboot启用6666端口,chrome显示无法访问此网站，报错ERR_UNSAFE_PORT
原因
```
Google Chrome 存在默认非安全端口列表，Firefox也有类似的端口限制。
6666-6669端口是IRC协议使用的缺省端口，存在安全风险，容易被木马等程序利用，应该是出于安全考虑，谷歌，火狐浏览器给屏蔽了吧
```

解决方案

```
【一】
更换其他端口如8899等

【二】
如果一定要使用上述端口的解决办法
选中Google Chrome 快捷方式，右键属性，在”目标”对应文本框添加：
–explicitly-allowed-ports=87,6666,556,6667
```

Google Chrome 默认非安全端口列表，搭建网站要建议尽量避免以下端口：

 | 端口 | 协议 |
 | ---- | ------ |
 | | 1 | tcpmux |
 | 7 | echo |
 | 9 | discard |
 | 11 | systat |
 | 13 | daytime |
 | 15 | netstat |
 | 17 | qotd |
 | 19 | chargen |
 | 20 | ftp data |
 | 21 | ftp access |
 | 22 | ssh |
 | 23 | telnet |
 | 25 | smtp |
 | 37 | time |
 | 42 | name |
 | 43 | nicname |
 | 53 | domain |
 | 77 | priv-rjs |
 | 79 | finger |
 | 87 | ttylink |
 | 95 | supdup |
 | 101 | hostriame |
 | 102 | iso-tsap |
 | 103 | gppitnp |
 | 104 | acr-nema |
 | 109 | pop2 |
 | 110 | pop3 |
 | 111 | sunrpc |
 | 113 | auth |
 | 115 | sftp |
 | 117 | uucp-path |
 | 119 | nntp |
 | 123 | NTP |
 | 135 | loc-srv /epmap |
 | 139 | netbios |
 | 143 | imap2 |
 | 179 | BGP |
 | 389 | ldap |
 | 465 | smtp+ssl |
 | 512 | print / exec |
 | 513 | login |
 | 514 | shell |
 | 515 | printer |
 | 526 | tempo |
 | 530 | courier |
 | 531 | chat |
 | 532 | netnews |
 | 540 | uucp |
 | 556 | remotefs |
 | 563 | nntp+ssl |
 | 587 | stmp? |
 | 601 | ?? |
 | 636 | ldap+ssl |
 | 993 | ldap+ssl |
 | 995 | pop3+ssl |
 | 2049 | nfs |
 | 3659 | apple-sasl / PasswordServer |
 | 4045 | lockd |
 | 6000 | X11 |
 | 6665 | Alternate IRC [Apple addition] |
 | 6666 | Alternate IRC [Apple addition] |
 | 6667 | Standard IRC [Apple addition] |
 | 6668 | Alternate IRC [Apple addition] |
 | 6669 | Alternate IRC [Apple addition] |

参考：

https://blog.csdn.net/hbiao68/article/details/105435257/



## 2.springboot整合mybatis-plus-test报错，没有 Failed to replace DataSource with an embedded database for tests

**背景**

按照[mybaits-plus的官方文档快速测试](https://baomidou.com/pages/b7dae0/)这一章节的内容，进行快速测试demo的搭建，启动就报错` Failed to replace DataSource with an embedded database for tests`

```
Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'sqlSessionFactory' defined in class path resource [com/baomidou/mybatisplus/autoconfigure/MybatisPlusAutoConfiguration.class]: Unsatisfied dependency expressed through method 'sqlSessionFactory' parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource': Invocation of init method failed; nested exception is java.lang.IllegalStateException: Failed to replace DataSource with an embedded database for tests. If you want an embedded database please put a supported one on the classpath or tune the replace attribute of @AutoConfigureTestDatabase.
	at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:801)
	at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:536)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1352)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1195)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:955)
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:929)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:591)
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:732)
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:409)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:308)
	at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
	at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:141)
	at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:90)
	... 72 more
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource': Invocation of init method failed; nested exception is java.lang.IllegalStateException: Failed to replace DataSource with an embedded database for tests. If you want an embedded database please put a supported one on the classpath or tune the replace attribute of @AutoConfigureTestDatabase.
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1804)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
	at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1391)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1311)
	at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:911)
	at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:788)
	... 90 more
Caused by: java.lang.IllegalStateException: Failed to replace DataSource with an embedded database for tests. If you want an embedded database please put a supported one on the classpath or tune the replace attribute of @AutoConfigureTestDatabase.
	at org.springframework.util.Assert.state(Assert.java:76)
	at org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration$EmbeddedDataSourceFactory.getEmbeddedDatabase(TestDatabaseAutoConfiguration.java:185)
	at org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration$EmbeddedDataSourceFactoryBean.afterPropertiesSet(TestDatabaseAutoConfiguration.java:149)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1863)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1800)
	... 101 more
```

**分析**

其实这里已经提示了我了` If you want an embedded database please put a supported one on the classpath or tune the replace attribute of @AutoConfigureTestDatabase.`

+ 配置一个**嵌入式数据库（embedded database）**
+ 或者使用`@AutoConfigureTestDatabase`注解

但是我们还是看下他的代码，看下`TestDatabaseAutoConfiguration`类

```java
@AutoConfiguration(
    before = {DataSourceAutoConfiguration.class}
)
public class TestDatabaseAutoConfiguration {
    public TestDatabaseAutoConfiguration() {
    }

    @Bean
    @ConditionalOnProperty(
        prefix = "spring.test.database",
        name = {"replace"},
        havingValue = "AUTO_CONFIGURED"
    )
    @ConditionalOnMissingBean
    public DataSource dataSource(Environment environment) {
        return (new EmbeddedDataSourceFactory(environment)).getEmbeddedDatabase();
    }

    @Bean
    @Role(2)
    @ConditionalOnProperty(
        prefix = "spring.test.database",
        name = {"replace"},
        havingValue = "ANY",
        matchIfMissing = true
    )
    static EmbeddedDataSourceBeanFactoryPostProcessor embeddedDataSourceBeanFactoryPostProcessor() {
        return new EmbeddedDataSourceBeanFactoryPostProcessor();
    }

    static class EmbeddedDataSourceFactory {
        private final Environment environment;

        EmbeddedDataSourceFactory(Environment environment) {
            this.environment = environment;
            if (environment instanceof ConfigurableEnvironment) {
                Map<String, Object> source = new HashMap();
                source.put("spring.datasource.schema-username", "");
                source.put("spring.sql.init.username", "");
                ((ConfigurableEnvironment)environment).getPropertySources().addFirst(new MapPropertySource("testDatabase", source));
            }

        }

        EmbeddedDatabase getEmbeddedDatabase() {
            EmbeddedDatabaseConnection connection = (EmbeddedDatabaseConnection)this.environment.getProperty("spring.test.database.connection", EmbeddedDatabaseConnection.class, EmbeddedDatabaseConnection.NONE);
            if (EmbeddedDatabaseConnection.NONE.equals(connection)) {
                connection = EmbeddedDatabaseConnection.get(this.getClass().getClassLoader());
            }

            Assert.state(connection != EmbeddedDatabaseConnection.NONE, "Failed to replace DataSource with an embedded database for tests. If you want an embedded database please put a supported one on the classpath or tune the replace attribute of @AutoConfigureTestDatabase.");
            return (new EmbeddedDatabaseBuilder()).generateUniqueName(true).setType(connection.getType()).build();
        }
    }

    static class EmbeddedDataSourceFactoryBean implements FactoryBean<DataSource>, EnvironmentAware, InitializingBean {
        private EmbeddedDataSourceFactory factory;
        private EmbeddedDatabase embeddedDatabase;

        EmbeddedDataSourceFactoryBean() {
        }

        public void setEnvironment(Environment environment) {
            this.factory = new EmbeddedDataSourceFactory(environment);
        }

        public void afterPropertiesSet() throws Exception {
            this.embeddedDatabase = this.factory.getEmbeddedDatabase();
        }

        public DataSource getObject() throws Exception {
            return this.embeddedDatabase;
        }

        public Class<?> getObjectType() {
            return EmbeddedDatabase.class;
        }
    }

    @Order(Integer.MAX_VALUE)
    static class EmbeddedDataSourceBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {
        private static final Log logger = LogFactory.getLog(EmbeddedDataSourceBeanFactoryPostProcessor.class);

        EmbeddedDataSourceBeanFactoryPostProcessor() {
        }

        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            Assert.isInstanceOf(ConfigurableListableBeanFactory.class, registry, "Test Database Auto-configuration can only be used with a ConfigurableListableBeanFactory");
            this.process(registry, (ConfigurableListableBeanFactory)registry);
        }

        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        }

        private void process(BeanDefinitionRegistry registry, ConfigurableListableBeanFactory beanFactory) {
            BeanDefinitionHolder holder = this.getDataSourceBeanDefinition(beanFactory);
            if (holder != null) {
                String beanName = holder.getBeanName();
                boolean primary = holder.getBeanDefinition().isPrimary();
                logger.info("Replacing '" + beanName + "' DataSource bean with " + (primary ? "primary " : "") + "embedded version");
                registry.removeBeanDefinition(beanName);
                registry.registerBeanDefinition(beanName, this.createEmbeddedBeanDefinition(primary));
            }

        }

        private BeanDefinition createEmbeddedBeanDefinition(boolean primary) {
            BeanDefinition beanDefinition = new RootBeanDefinition(EmbeddedDataSourceFactoryBean.class);
            beanDefinition.setPrimary(primary);
            return beanDefinition;
        }

        private BeanDefinitionHolder getDataSourceBeanDefinition(ConfigurableListableBeanFactory beanFactory) {
            String[] beanNames = beanFactory.getBeanNamesForType(DataSource.class);
            if (ObjectUtils.isEmpty(beanNames)) {
                logger.warn("No DataSource beans found, embedded version will not be used");
                return null;
            } else if (beanNames.length == 1) {
                String beanName = beanNames[0];
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                return new BeanDefinitionHolder(beanDefinition, beanName);
            } else {
                String[] var3 = beanNames;
                int var4 = beanNames.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    String beanName = var3[var5];
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                    if (beanDefinition.isPrimary()) {
                        return new BeanDefinitionHolder(beanDefinition, beanName);
                    }
                }

                logger.warn("No primary DataSource found, embedded version will not be used");
                return null;
            }
        }
    }
}
```

当yaml文件里没有配置这个`spring.test.database.replace`属性的时候，他会自动配置成`ANY`，这代表要用任何情况下都替代应用程序的数据源

然后后续就调用了这个方法

```java
EmbeddedDatabase getEmbeddedDatabase() {
    EmbeddedDatabaseConnection connection = (EmbeddedDatabaseConnection)this.environment.getProperty("spring.test.database.connection", EmbeddedDatabaseConnection.class, EmbeddedDatabaseConnection.NONE);
    if (EmbeddedDatabaseConnection.NONE.equals(connection)) {
        connection = EmbeddedDatabaseConnection.get(this.getClass().getClassLoader());
    }

    Assert.state(connection != EmbeddedDatabaseConnection.NONE, "Failed to replace DataSource with an embedded database for tests. If you want an embedded database please put a supported one on the classpath or tune the replace attribute of @AutoConfigureTestDatabase.");
    return (new EmbeddedDatabaseBuilder()).generateUniqueName(true).setType(connection.getType()).build();
}
```

断点发现connection是`NONE`，因为yaml文件里没有配置`spring.test.database.connection`这个相关的属性，所以这个时候就报错`Failed to replace DataSource with an embedded database for tests. If you want an embedded database please put a supported one on the classpath or tune the replace attribute of @AutoConfigureTestDatabase.`

**`@MybatisPlusTest`注解原理**
如果你之前使用过`MyBatis-Spring-Boot-Starter-Test`中的`@MybatisTest`的话，你会发现`@MybatisPlusTest`注解原理与之类似，都是限制`spring boot`的自动配置，只需要加载特定的配置即可。我们来看一下注解源码：

```java
package com.baomidou.mybatisplus.test.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@BootstrapWith(MybatisPlusTestContextBootstrapper.class)
@ExtendWith({SpringExtension.class})
@OverrideAutoConfiguration(
    enabled = false
)
@TypeExcludeFilters({MybatisPlusTypeExcludeFilter.class})
@Transactional
@AutoConfigureCache
@AutoConfigureMybatisPlus
@AutoConfigureTestDatabase
@ImportAutoConfiguration
public @interface MybatisPlusTest {
    String[] properties() default {};

    boolean useDefaultFilters() default true;

    ComponentScan.Filter[] includeFilters() default {};

    ComponentScan.Filter[] excludeFilters() default {};

    @AliasFor(
        annotation = ImportAutoConfiguration.class,
        attribute = "exclude"
    )
    Class<?>[] excludeAutoConfiguration() default {};
}

```
`@OverrideAutoConfiguration(enabled = false)`是关键，它关闭了自动配置，而一般在spring boot项目中enable是开启的；
`@AutoConfigureMybatisPlus`注解是自定义注解，这个注解定义了加载所有所需的加载类，在spring.factories里面声明了要自动配置的类：



```factories
# AutoConfigureMybatis auto-configuration imports
com.baomidou.mybatisplus.test.autoconfigure.AutoConfigureMybatisPlus=\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration,\
org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration,\
com.baomidou.mybatisplus.autoconfigure.IdentifierGeneratorAutoConfiguration,\
com.baomidou.mybatisplus.autoconfigure.MybatisPlusLanguageDriverAutoConfiguration,\
com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration
```

`@AutoConfigureTestDatabase`注解表明使用的是内存数据库而不是真实数据库有了这些限制和规定以后，mybatis-plus在测试环境内就可以自动加载所需要的的配置了，这样就去除了非必要资源的加载。

```java
package org.springframework.boot.test.autoconfigure.jdbc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.boot.test.autoconfigure.properties.SkipPropertyMapping;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration
@PropertyMapping("spring.test.database")
public @interface AutoConfigureTestDatabase {
    @PropertyMapping(
        skip = SkipPropertyMapping.ON_DEFAULT_VALUE
    )
    Replace replace() default AutoConfigureTestDatabase.Replace.ANY;

    EmbeddedDatabaseConnection connection() default EmbeddedDatabaseConnection.NONE;

    public static enum Replace {
        ANY,
        AUTO_CONFIGURED,
        NONE;

        private Replace() {
        }
    }
}
```

用了这个注解，默认使用的`replace`是`ANY`,然后就用嵌入式数据库替代了



**解决方案**

+ 需要修改一下这个Test类，添加`@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)`注解

  `@AutoConfigureTestDatabase` 注解是Spring Boot测试中的一个注解，用于配置测试时使用的数据库。具体来说，`replace` 属性是用来指定测试环境中数据库的替代行为。

  在你提到的这个例子中，`replace = AutoConfigureTestDatabase.Replace.NONE` 的含义是不要替代测试数据库。也就是说，该注解告诉Spring Boot测试框架不要替换应用程序的数据源配置，而是使用应用程序的实际数据源。这对于希望在测试中使用与生产环境相同的数据库配置的情况很有用。

  常见的替代行为选项有：

  - `AutoConfigureTestDatabase.Replace.NONE`: 不替代，使用应用程序的实际数据源。
  - `AutoConfigureTestDatabase.Replace.ANY`: 任何情况下都替代应用程序的数据源。
  - `AutoConfigureTestDatabase.Replace.NONE`: 任何情况下都不替代应用程序的数据源。
  - `AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED`: 由Spring Boot自动配置决定是否替代应用程序的数据源。



**参考**

https://blog.csdn.net/u012397189/article/details/109288747



