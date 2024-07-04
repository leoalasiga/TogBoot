package com.als.tog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.application.name:dkw}")
    private String applicationName;

    @Value("${swagger.application.enable:true}")
    private boolean enable;

    @Bean
    public Docket createRestApi() {
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .query(simpleParameterSpecificationBuilder -> simpleParameterSpecificationBuilder
                        .model(modelSpecificationBuilder -> modelSpecificationBuilder.scalarModel(ScalarType.STRING))
                        .defaultValue(""))
                .in(ParameterType.HEADER)
                .name("token")
                .description("令牌")
                .required(false)
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                //这里根据你们实际文件目录修改
                .apis(RequestHandlerSelectors.basePackage("com.als.tog.web"))
                .paths(PathSelectors.any())
                .build().globalRequestParameters(parameters);
    }



    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(applicationName + "接口文档")
                .version("1.0")
                .build();
    }

    /**
     * 处理actuator走了pathPatternsCondition，使用ant_path_matcher
     * @param webEndpointsSupplier
     * @param servletEndpointsSupplier
     * @param controllerEndpointsSupplier
     * @param endpointMediaTypes
     * @param corsProperties
     * @param webEndpointProperties
     * @param environment
     * @return
     */
    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier,
                                                                         ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier,
                                                                         EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties,
                                                                         WebEndpointProperties webEndpointProperties, Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping =
                webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath)
                        || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes,
                corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath),
                shouldRegisterLinksMapping, null);
    }
}
