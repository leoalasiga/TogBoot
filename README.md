# TogBoot
> 全称是to Government springboot

打算参考sofaboot的模式，学习增强springboot

现在开始我的模仿之旅

## 1.定义全局的pom依赖
在项目下添加全局pom.xml
```text
.
└─ pom.xml
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.14</version>
        <!-- Add this to resolve to repository -->
        <relativePath/>
    </parent>

    <groupId>com.als.tog</groupId>
    <artifactId>tog-boot-build</artifactId>
    <version>${revision}</version>
    <packaging>pom</packaging>
    <name>TogBoot Build</name>
    <description>TogBoot Build</description>

    <properties>
        <revision>1.0.0</revision>
        <sofa.boot.version>${revision}</sofa.boot.version>
        <!--maven plugin-->
        <maven.staging.plugin>1.6.7</maven.staging.plugin>
        <!-- 用于处理 GPG（GNU Privacy Guard）签名和加密操作。主要功能包括创建数字签名以及验证数字签名，通常用于确保构建产物的完整性和安全性。-->
        <maven.gpg.pluign>1.6</maven.gpg.pluign>
        <!--flatten-maven-plugin 是一个 Maven 插件，用于将复杂的 Maven 构建文件（pom.xml）转化为更加扁平化（即没有继承关系和多个模块的结构）的形式-->
        <flatten-maven-plugin.version>1.2.7</flatten-maven-plugin.version>
    </properties>

    <modules>
        <module>tog-boot-project</module>
    </modules>

    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>flatten-maven-plugin</artifactId>
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
                        <id>flatten.clean</id>
                        <phase>clean</phase>
                        <goals>
                            <goal>clean</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.codehaus.mojo</groupId>
                    <artifactId>flatten-maven-plugin</artifactId>
                    <version>${flatten-maven-plugin.version}</version>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>

    <distributionManagement>
        <repository>
            <id>admin</id>
            <name>Nexus Release Repository</name>
            <url>http://127.0.0.1:8081/repository/maven-releases/</url>
        </repository>
        <snapshotRepository>
            <id>admin</id>
            <name>Nexus Snapshot Repository</name>
            <url>http://127.0.0.1:8081/repository/maven-snapshots/</url>
        </snapshotRepository>
    </distributionManagement>
</project>
```
+ 这边用的springboot版本为2.7.14
+ 其他的只是定义一些打包的东西，和仓库地址，你们下载后，记得将两个distributionManagement的repository地址改成自己的

## 创建tog-boot-project，初始化pom文件
```text
.
├─ tog-boot-project/
│  └─ pom.xml
└─ pom.xml
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.als.tog</groupId>
        <artifactId>tog-boot-build</artifactId>
        <version>${revision}</version>
    </parent>

    <artifactId>tog-boot-project</artifactId>
    <packaging>pom</packaging>

    <modules>

    </modules>

    <properties>
        <main.user.dir>${basedir}/..</main.user.dir>
    </properties>

</project>
```

## 定义tog-dependencies的pom文件
```text
.
├─ tog-boot-project/
│  └─ tog-boot-dependencies/
│    └─ pom.xml
│  └─ pom.xml
└─ pom.xml
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.als.tog</groupId>
        <artifactId>tog-boot-project</artifactId>
        <version>${revision}</version>
    </parent>

    <artifactId>tog-boot-dependencies</artifactId>
    <packaging>pom</packaging>


    <properties>
        <!--project-->
        <java.version>1.8</java.version>
        <project.encoding>UTF-8</project.encoding>
        <project.build.encoding>UTF-8</project.build.encoding>
        
        <!--core-->
        <!--        <sofa.registry.version>5.4.2</sofa.registry.version>-->
        <tracer.core.version>3.1.3</tracer.core.version>
        <rpc.core.version>5.8.3</rpc.core.version>
        
        <!--3rd lib dependency-->
        <!--FIXME 1.7.36 will cause compatibility problem, see #https://www.slf4j.org/manual.html-->
        <slf4j.version>1.7.32</slf4j.version>
        <fastjson.version>1.2.69</fastjson.version>
        <javassist.version>3.28.0-GA</javassist.version>
        <guice.version>3.0</guice.version>
        <guava.version>28.2-jre</guava.version>
        <zipkin.version>2.11.3</zipkin.version>
        <zipkin.reporter.version>2.7.7</zipkin.reporter.version>
        <jackson.version>2.9.10</jackson.version>
        <jboss.jaxrs.api.version>1.0.2.Final</jboss.jaxrs.api.version>
        <protobuf.version>3.22.2</protobuf.version>
        <resteasy.version>3.6.3.Final</resteasy.version>
        <curator.version>4.0.1</curator.version>
        <dubbo.version>2.6.9</dubbo.version>
        <zkclient.version>0.11</zkclient.version>
        <nacos.version>1.0.0</nacos.version>
        <swagger.version>2.0.0</swagger.version>
        <protoc.version>3.11.0</protoc.version>
        <grpc.version>1.28.0</grpc.version>
        <grpc.common.protos.version>1.17.0</grpc.common.protos.version>
    </properties>

    <developers>
        <developer>
            <name>leoalasiga</name>
            <email>leoalasiga@icloud.com</email>
            <url>leoalasiga.github.io</url>
        </developer>
    </developers>

    <dependencyManagement>
        <dependencies>
            <!-- 3rd lib dependency begin -->
            <dependency>
                <groupId>com.google.protobuf</groupId>
                <artifactId>protobuf-bom</artifactId>
                <version>${protobuf.version}</version>
                <scope>import</scope>
                <type>pom</type>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>fastjson</artifactId>
                <version>${fastjson.version}</version>
            </dependency>
            <dependency>
                <groupId>com.google.inject</groupId>
                <artifactId>guice</artifactId>
                <version>${guice.version}</version>
            </dependency>
            <dependency>
                <groupId>com.google.inject.extensions</groupId>
                <artifactId>guice-multibindings</artifactId>
                <version>${guice.version}</version>
            </dependency>
            <dependency>
                <groupId>org.javassist</groupId>
                <artifactId>javassist</artifactId>
                <version>${javassist.version}</version>
            </dependency>
            <dependency>
                <groupId>io.grpc</groupId>
                <artifactId>grpc-all</artifactId>
                <version>${grpc.version}</version>
            </dependency>
            <dependency>
                <groupId>io.grpc</groupId>
                <artifactId>grpc-netty-shaded</artifactId>
                <version>${grpc.version}</version>
            </dependency>
            <dependency>
                <groupId>io.grpc</groupId>
                <artifactId>grpc-core</artifactId>
                <version>${grpc.version}</version>
            </dependency>
            <dependency>
                <groupId>io.grpc</groupId>
                <artifactId>grpc-api</artifactId>
                <version>${grpc.version}</version>
            </dependency>
            <dependency>
                <groupId>io.grpc</groupId>
                <artifactId>grpc-protobuf</artifactId>
                <version>${grpc.version}</version>
            </dependency>
            <dependency>
                <groupId>io.grpc</groupId>
                <artifactId>grpc-context</artifactId>
                <version>${grpc.version}</version>
            </dependency>
            <dependency>
                <groupId>io.grpc</groupId>
                <artifactId>grpc-protobuf-lite</artifactId>
                <version>${grpc.version}</version>
                <exclusions>
                    <exclusion>
                        <artifactId>protobuf-javalite</artifactId>
                        <groupId>com.google.protobuf</groupId>
                    </exclusion>
                </exclusions>
            </dependency>
            <dependency>
                <groupId>com.google.api.grpc</groupId>
                <artifactId>proto-google-common-protos</artifactId>
                <version>${grpc.common.protos.version}</version>
                <exclusions>
                    <exclusion>
                        <artifactId>protobuf-java</artifactId>
                        <groupId>com.google.protobuf</groupId>
                    </exclusion>
                    <exclusion>
                        <artifactId>api-common</artifactId>
                        <groupId>com.google.api</groupId>
                    </exclusion>
                </exclusions>
            </dependency>
            <dependency>
                <groupId>io.grpc</groupId>
                <artifactId>grpc-stub</artifactId>
                <version>${grpc.version}</version>
            </dependency>
            <dependency>
                <groupId>com.google.guava</groupId>
                <artifactId>guava</artifactId>
                <version>${guava.version}</version>
            </dependency>
            <dependency>
                <groupId>io.zipkin.zipkin2</groupId>
                <artifactId>zipkin</artifactId>
                <version>${zipkin.version}</version>
            </dependency>
            <dependency>
                <groupId>io.zipkin.reporter2</groupId>
                <artifactId>zipkin-reporter</artifactId>
                <version>${zipkin.reporter.version}</version>
            </dependency>
            <dependency>
                <groupId>io.swagger.core.v3</groupId>
                <artifactId>swagger-jaxrs2</artifactId>
                <version>${swagger.version}</version>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
            </dependency>

            <!--extension dependency -->
            <dependency>
                <groupId>org.apache.curator</groupId>
                <artifactId>curator-client</artifactId>
                <version>${curator.version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.curator</groupId>
                <artifactId>curator-framework</artifactId>
                <version>${curator.version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.curator</groupId>
                <artifactId>curator-recipes</artifactId>
                <version>${curator.version}</version>
            </dependency>

            <!-- resteasy -->
            <dependency>
                <groupId>org.jboss.resteasy</groupId>
                <artifactId>resteasy-netty4</artifactId>
                <version>${resteasy.version}</version>
            </dependency>
            <dependency>
                <groupId>org.jboss.resteasy</groupId>
                <artifactId>resteasy-client</artifactId>
                <version>${resteasy.version}</version>
            </dependency>
            <dependency>
                <groupId>org.jboss.resteasy</groupId>
                <artifactId>resteasy-jackson2-provider</artifactId>
                <version>${resteasy.version}</version>
            </dependency>
            <dependency>
                <groupId>org.jboss.resteasy</groupId>
                <artifactId>resteasy-jaxb-provider</artifactId>
                <version>${resteasy.version}</version>
            </dependency>
            <dependency>
                <groupId>org.jboss.resteasy</groupId>
                <artifactId>resteasy-jaxrs</artifactId>
                <version>${resteasy.version}</version>
            </dependency>
            <dependency>
                <groupId>org.jboss.resteasy</groupId>
                <artifactId>resteasy-multipart-provider</artifactId>
                <version>${resteasy.version}</version>
            </dependency>
            <dependency>
                <groupId>org.jboss.resteasy</groupId>
                <artifactId>resteasy-validator-provider-11</artifactId>
                <version>${resteasy.version}</version>
            </dependency>

            <!-- jboss jaxrs api -->
            <dependency>
                <groupId>org.jboss.spec.javax.ws.rs</groupId>
                <artifactId>jboss-jaxrs-api_2.1_spec</artifactId>
                <version>${jboss.jaxrs.api.version}</version>
            </dependency>

            <!--dubbo-->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>dubbo</artifactId>
                <version>${dubbo.version}</version>
                <exclusions>
                    <exclusion>
                        <artifactId>javassist</artifactId>
                        <groupId>org.javassist</groupId>
                    </exclusion>
                    <exclusion>
                        <artifactId>spring</artifactId>
                        <groupId>org.springframework</groupId>
                    </exclusion>
                </exclusions>
            </dependency>
            <dependency>
                <groupId>com.101tec</groupId>
                <artifactId>zkclient</artifactId>
                <exclusions>
                    <exclusion>
                        <groupId>org.slf4j</groupId>
                        <artifactId>slf4j-api</artifactId>
                    </exclusion>
                    <exclusion>
                        <groupId>org.slf4j</groupId>
                        <artifactId>slf4j-log4j12</artifactId>
                    </exclusion>
                    <exclusion>
                        <groupId>org.apache.zookeeper</groupId>
                        <artifactId>zookeeper</artifactId>
                    </exclusion>
                </exclusions>
                <version>${zkclient.version}</version>
            </dependency>

            <!--nacos-->
            <dependency>
                <groupId>com.alibaba.nacos</groupId>
                <artifactId>nacos-api</artifactId>
                <version>${nacos.version}</version>
            </dependency>
            <dependency>
                <groupId>com.alibaba.nacos</groupId>
                <artifactId>nacos-client</artifactId>
                <version>${nacos.version}</version>
            </dependency>
            <!-- 3rd lib dependency end -->
        </dependencies>
    </dependencyManagement>


    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>flatten-maven-plugin</artifactId>
                <inherited>false</inherited>
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
                        <id>flatten.clean</id>
                        <phase>clean</phase>
                        <goals>
                            <goal>clean</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

## 添加tog-boot-parent
```text
.
├─ tog-boot-project/
│  └─ tog-boot-dependencies/
│    └─ pom.xml
│  └─ tog-boot-parent/
│    └─ pom.xml
│  └─ pom.xml
└─ pom.xml
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>tog-boot-dependencies</artifactId>
        <groupId>com.als.tog</groupId>
        <version>${revision}</version>
        <relativePath>../tog-boot-dependencies</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>tog-boot-project</artifactId>
    <packaging>pom</packaging>


    <properties>
        <main.user.dir>${basedir}/../..</main.user.dir>
        <project.encoding>UTF-8</project.encoding>
        <java.version>1.8</java.version>

        <license.maven.plugin>3.0</license.maven.plugin>
        <maven.java.formatter.plugin>0.4</maven.java.formatter.plugin>
        <maven.pmd.plugin.version>3.13.0</maven.pmd.plugin.version>
        <maven.checkstyle.plugin.version>3.1.2</maven.checkstyle.plugin.version>
        <maven.compiler.plugin>3.1</maven.compiler.plugin>
        <maven.source.plugin>3.0.0</maven.source.plugin>
        <maven.javadoc.plugin>2.9.1</maven.javadoc.plugin>
        <jacoco.maven.plugin>0.8.7</jacoco.maven.plugin>
        <maven.surefire.plugin>3.0.0</maven.surefire.plugin>

        <!-- for dependencyManagement -->
        <spring.cloud.stream.version>2.1.0.RELEASE</spring.cloud.stream.version>
        <rocketmq.spring.boot.starter.version>2.0.2</rocketmq.spring.boot.starter.version>
        <spring.cloud.starter.openfeign.version>3.1.3</spring.cloud.starter.openfeign.version>
        <javax.servlet.version>3.1.0</javax.servlet.version>
        <tomcat.version>9.0.54</tomcat.version>
        <jetty.version>9.4.26.v20200117</jetty.version>
        <undertow-core>2.1.3.Final</undertow-core>
        <commons.io.version>2.4</commons.io.version>
        <HikariCP.java6.version>2.3.8</HikariCP.java6.version>
        <druid.version>1.0.12</druid.version>
        <c3p0.version>0.9.1.1</c3p0.version>
        <common.dbcp.version>1.4</common.dbcp.version>

        <jmockit.version>1.40</jmockit.version>
        <junit.jupiter.version>5.3.2</junit.jupiter.version>
        <junit.vintage.version>5.3.2</junit.vintage.version>
        <junit.platform.version>1.3.2</junit.platform.version>
        <junit.version>4.13.1</junit.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <!--provided-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-openfeign</artifactId>
                <version>${spring.cloud.starter.openfeign.version}</version>
                <scope>provided</scope>
            </dependency>

            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-stream</artifactId>
                <version>${spring.cloud.stream.version}</version>
                <scope>provided</scope>
            </dependency>

            <dependency>
                <groupId>org.apache.rocketmq</groupId>
                <artifactId>rocketmq-spring-boot-starter</artifactId>
                <version>${rocketmq.spring.boot.starter.version}</version>
                <scope>provided</scope>
            </dependency>

            <dependency>
                <groupId>javax.servlet</groupId>
                <artifactId>javax.servlet-api</artifactId>
                <version>${javax.servlet.version}</version>
                <scope>provided</scope>
            </dependency>

            <dependency>
                <groupId>org.apache.tomcat.embed</groupId>
                <artifactId>tomcat-embed-core</artifactId>
                <version>${tomcat.version}</version>
                <scope>provided</scope>
            </dependency>
            <dependency>
                <groupId>org.eclipse.jetty</groupId>
                <artifactId>jetty-server</artifactId>
                <version>${jetty.version}</version>
                <scope>provided</scope>
            </dependency>
            <dependency>
                <groupId>org.eclipse.jetty</groupId>
                <artifactId>jetty-webapp</artifactId>
                <version>${jetty.version}</version>
                <scope>provided</scope>
            </dependency>
            <dependency>
                <groupId>io.undertow</groupId>
                <artifactId>undertow-core</artifactId>
                <version>${undertow.version}</version>
                <scope>provided</scope>
            </dependency>

            <!--Test-->
            <dependency>
                <groupId>commons-io</groupId>
                <artifactId>commons-io</artifactId>
                <version>${commons.io.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>com.zaxxer</groupId>
                <artifactId>HikariCP-java6</artifactId>
                <version>${HikariCP.java6.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>${druid.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>c3p0</groupId>
                <artifactId>c3p0</artifactId>
                <version>${c3p0.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>commons-dbcp</groupId>
                <artifactId>commons-dbcp</artifactId>
                <version>${common.dbcp.version}</version>
                <scope>test</scope>
            </dependency>

            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-api</artifactId>
                <version>${junit.jupiter.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>${junit.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-engine</artifactId>
                <version>${junit.jupiter.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.junit.vintage</groupId>
                <artifactId>junit-vintage-engine</artifactId>
                <version>${junit.vintage.version}</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>flatten-maven-plugin</artifactId>
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
                        <id>flatten.clean</id>
                        <phase>clean</phase>
                        <goals>
                            <goal>clean</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <plugin>
                <groupId>com.mycila</groupId>
                <artifactId>license-maven-plugin</artifactId>
                <version>${license.maven.plugin}</version>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>remove</goal>
                            <goal>format</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <quiet>true</quiet>
                    <header>${main.user.dir}/tools/HEADER</header>
                    <includes>
                        <include>**/src/main/java/**</include>
                        <include>**/src/test/java/**</include>
                    </includes>
                    <strictCheck>true</strictCheck>
                    <mapping>
                        <java>SLASHSTAR_STYLE</java>
                    </mapping>
                </configuration>
            </plugin>

            <plugin>
                <groupId>com.googlecode.maven-java-formatter-plugin</groupId>
                <artifactId>maven-java-formatter-plugin</artifactId>
                <version>${maven.java.formatter.plugin}</version>
                <executions>
                    <execution>
                        <phase>validate</phase>
                        <goals>
                            <goal>format</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <configFile>${main.user.dir}/tools/Formatter.xml</configFile>
                    <encoding>${project.encoding}</encoding>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-pmd-plugin</artifactId>
                <version>${maven.pmd.plugin.version}</version>
                <configuration>
                    <printFailingErrors>true</printFailingErrors>
                    <linkXRef>false</linkXRef>
                    <includeTests>true</includeTests>
                    <rulesets>
                        <ruleset>${main.user.dir}/tools/pmd-ruleset.xml</ruleset>
                    </rulesets>
                </configuration>
                <executions>
                    <execution>
                        <id>check</id>
                        <phase>validate</phase>
                        <goals>
                            <goal>check</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-checkstyle-plugin</artifactId>
                <version>${maven.checkstyle.plugin.version}</version>
                <dependencies>
                    <dependency>
                        <groupId>com.puppycrawl.tools</groupId>
                        <artifactId>checkstyle</artifactId>
                        <version>8.42</version>
                    </dependency>
                </dependencies>
                <executions>
                    <execution>
                        <id>checkstyle</id>
                        <phase>validate</phase>
                        <goals>
                            <goal>check</goal>
                        </goals>
                        <configuration>
                            <failOnViolation>true</failOnViolation>
                        </configuration>
                    </execution>
                </executions>
                <configuration>
                    <configLocation>${main.user.dir}/tools/check-style.xml</configLocation>
                </configuration>
            </plugin>


            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${maven.compiler.plugin}</version>
                <configuration>
                    <encoding>${project.encoding}</encoding>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>${maven.source.plugin}</version>
                <executions>
                    <execution>
                        <id>attach-sources</id>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-javadoc-plugin</artifactId>
                <executions>
                    <execution>
                        <id>attach-javadocs</id>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>${jacoco.maven.plugin}</version>
                <configuration>
                    <skip>false</skip>
                </configuration>
                <executions>
                    <execution>
                        <id>default-prepare-agent</id>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

    <profiles>
        <profile>
            <id>jdk8</id>
            <activation>
                <jdk>1.8</jdk>
            </activation>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-surefire-plugin</artifactId>
                        <version>${maven.surefire.plugin}</version>
                        <dependencies>
                            <dependency>
                                <groupId>org.ow2.asm</groupId>
                                <artifactId>asm</artifactId>
                                <version>6.2</version>
                            </dependency>
                            <dependency>
                                <groupId>org.apache.maven.surefire</groupId>
                                <artifactId>surefire-junit47</artifactId>
                                <version>${maven.surefire.plugin}</version>
                            </dependency>
                        </dependencies>
                        <configuration>
                            <runOrder>alphabetical</runOrder>
                            <reuseForks>false</reuseForks>
                        </configuration>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>
</project>
```






