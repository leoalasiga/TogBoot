package com.als.tog.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @description: TogBoot代码生成器，用mybatis-plus
 * @author: liujiajie
 * @date: 2024/3/1 15:47
 */
public class TogBootGenerator {
    public static void main(String[] args) {

    }

    public static void generatorByZL() {
        /**
         * 先配置数据源
         */
        MySqlQuery mySqlQuery = new MySqlQuery() {
            @Override
            public String[] fieldCustom() {
                return new String[]{"Default"};
            }
        };


        DataSourceConfig dsc = new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/tog?&useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai", "tog", "Tog@666")
                .dbQuery(mySqlQuery).build();
        //通过datasourceConfig创建AutoGenerator
        AutoGenerator generator = new AutoGenerator(dsc);

        /**
         * 全局配置
         */
        String projectPath = System.getProperty("user.dir"); //获取项目路径
        String filePath = projectPath + "/src/main/java";  //java下的文件路径
        GlobalConfig global = new GlobalConfig.Builder()
                .outputDir(filePath)//生成的输出路径
                .author("liujiajie")//生成的作者名字
                //.enableSwagger()开启swagger，需要添加swagger依赖并配置
                .dateType(DateType.TIME_PACK)//时间策略
                .commentDate("yyyy年MM月dd日")//格式化时间格式
                .disableOpenDir()//禁止打开输出目录，默认false
//                        .fileOverride()//覆盖生成文件 3.5.5已经没有这个方法了
                .build();

        /**
         * 包配置
         */
        PackageConfig packages = new PackageConfig.Builder()
                .entity("entity")//实体类包名
                .parent("com.als.tog")//父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
                .controller("controller")//控制层包名
                .mapper("dao")//mapper层包名
                .xml("mapper.xml")//数据访问层xml包名
                .service("service")//service层包名
                .serviceImpl("service.impl")//service实现类包名
                .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/mapper")) //路径配置信息,就是配置各个文件模板的路径信息,这里以mapper.xml为例
                .build();
        /**
         * 模板配置
         */

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
//         如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";


        TemplateConfig template = new TemplateConfig.Builder()
//            .disable()//禁用所有模板
                //.disable(TemplateType.ENTITY)禁用指定模板
//                .service(filePath + "/service.java")//service模板路径
//                .serviceImpl(filePath + "/service/impl/serviceImpl.java")//实现类模板路径
//                .mapper(filePath + "/mapper.java")//mapper模板路径
//                .mapperXml("/templates/mapper.xml")//xml文件模板路路径
//                .controller(filePath + "/controller")//controller层模板路径
                .build();

        /**
         * 注入配置,自定义配置一个Map对象
         */
//    Map<String,Object> map = new HashMap<>();
//        map.put("name","young");
//        map.put("age","22");
//        map.put("sex","男");
//        map.put("description","深情不及黎治跃");
//
//    InjectionConfig injectionConfig = new InjectionConfig.Builder()
//            .customMap(map)
//            .build();

        /**
         * 策略配置开始
         */
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .enableCapitalMode()//开启全局大写命名
                //.likeTable()模糊表匹配
                .addInclude()//添加表匹配，指定要生成的数据表名，不写默认选定数据库所有表
                //.disableSqlFilter()禁用sql过滤:默认(不使用该方法）true
                //.enableSchema()启用schema:默认false

                .entityBuilder() //实体策略配置
                //.disableSerialVersionUID()禁用生成SerialVersionUID：默认true
                .enableChainModel()//开启链式模型
                .enableLombok()//开启lombok
                .enableRemoveIsPrefix()//开启 Boolean 类型字段移除 is 前缀
                .enableTableFieldAnnotation()//开启生成实体时生成字段注解
                //.addTableFills()添加表字段填充
                .naming(NamingStrategy.underline_to_camel)//数据表映射实体命名策略：默认下划线转驼峰underline_to_camel
                .columnNaming(NamingStrategy.underline_to_camel)//表字段映射实体属性命名规则：默认null，不指定按照naming执行
                .idType(IdType.AUTO)//添加全局主键类型
                .formatFileName("%s")//格式化实体名称，%s取消首字母I
                .build()

                .mapperBuilder()//mapper文件策略
                .enableMapperAnnotation()//开启mapper注解
                .enableBaseResultMap()//启用xml文件中的BaseResultMap 生成
                .enableBaseColumnList()//启用xml文件中的BaseColumnList
                //.cache(缓存类.class)设置缓存实现类
                .formatMapperFileName("%sMapper")//格式化Dao类名称
                .formatXmlFileName("%sMapper")//格式化xml文件名称
                .build()

                .serviceBuilder()//service文件策略
                .formatServiceFileName("%sService")//格式化 service 接口文件名称
                .formatServiceImplFileName("%sServiceImpl")//格式化 service 接口文件名称
                .build()

                .controllerBuilder()//控制层策略
                //.enableHyphenStyle()开启驼峰转连字符，默认：false
                .enableRestStyle()//开启生成@RestController
                .formatFileName("%sController")//格式化文件名称
                .build();
        /*至此，策略配置才算基本完成！*/

        /**
         * 将所有配置项整合到AutoGenerator中进行执行
         */


        generator.global(global)
                .template(template)
//                .injection(injectionConfig)
                .packageInfo(packages)
                .strategy(strategyConfig)
                .execute();
    }


    public static void genertor() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/tog?&useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai", "tog", "Tog@666")
                .globalConfig(builder -> {
                    builder.author("liujiajie") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("D://mybatis-plus-generator/"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.als.tog.web") // 设置父包名
                            .moduleName("system") // 设置父包模块名

                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://mybatis-plus-generator/")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user") // 设置需要生成的表名
                            .controllerBuilder()
                            .enableRestStyle()
                            .enableHyphenStyle()
                            .entityBuilder()
                            .enableLombok()
//                            .addTablePrefix("t_", "c_")
                    ; // 设置过滤表前缀
                })
                .execute();
    }

    public static void generatorWithJh() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/tog?&useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai", "tog", "Tog@666")
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？")))
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名？")))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok().addTableFills(
                                new Column("create_time", FieldFill.INSERT)
                        ).build())
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
                .execute();
    }


    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }


    protected static StrategyConfig.Builder strategyConfig() {
        return new StrategyConfig.Builder();
    }

    /**
     * 全局配置
     */
    protected static GlobalConfig.Builder globalConfig() {
        return new GlobalConfig.Builder();
    }

    /**
     * 包配置
     */
    protected static PackageConfig.Builder packageConfig() {
        return new PackageConfig.Builder();
    }

    /**
     * 模板配置
     */
    protected static TemplateConfig.Builder templateConfig() {
        return new TemplateConfig.Builder();
    }

    /**
     * 注入配置
     */
    protected static InjectionConfig.Builder injectionConfig() {
        // 测试自定义输出文件之前注入操作，该操作再执行生成代码前 debug 查看
        return new InjectionConfig.Builder().beforeOutputFile((tableInfo, objectMap) -> {
            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
        });
    }
}
