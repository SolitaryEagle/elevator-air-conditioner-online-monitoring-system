package edu.hhu.air.conditioner.online.monitoring.controller.config;

import edu.hhu.air.conditioner.online.monitoring.constant.UrlMappingConsts;
import edu.hhu.air.conditioner.online.monitoring.framework.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 覃国强
 * @date 2019-03-04
 */
@Configuration
public class CustomizeWebMvcConfigurer implements WebMvcConfigurer {

    /*

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration loginInterceptor = registry.addInterceptor(loginInterceptor());
        loginInterceptor.addPathPatterns("/**");
        loginInterceptor
                .excludePathPatterns("/v1/monitoring-system/users/register", "/v1/monitoring-system/users/login",
                        "/v1/monitoring-system/users/test");
    }
    */

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("").setViewName("user/login");
        registry.addViewController(UrlMappingConsts.PROJECT_BASE_MAPPING_V1 + "/page/index")
                .setViewName("map/address-catalogue");

        registry.addViewController(UrlMappingConsts.USER_BASE_MAPPING_V1 + "/page/activation")
                .setViewName("user/activation");
        registry.addViewController(UrlMappingConsts.USER_BASE_MAPPING_V1 + "/page/forgot-password").
                setViewName("user/forgot-password");
        registry.addViewController(UrlMappingConsts.USER_BASE_MAPPING_V1 + "/page/login").setViewName("user/login");
        registry.addViewController(UrlMappingConsts.USER_BASE_MAPPING_V1 + "/page/register")
                .setViewName("user/register");

        registry.addViewController(UrlMappingConsts.AIR_CONDITIONER_BASE_MAPPING_V1 + "/page/add-info")
                .setViewName("air-conditioner/add-info");
        registry.addViewController(UrlMappingConsts.AIR_CONDITIONER_BASE_MAPPING_V1 + "/page/online-state")
                .setViewName("air-conditioner/online-state");
        registry.addViewController(UrlMappingConsts.AIR_CONDITIONER_BASE_MAPPING_V1 + "/page/update-info")
                .setViewName("air-conditioner/update-info");

        registry.addViewController(UrlMappingConsts.FAULT_BASE_MAPPING_V1 + "/page/online-state")
                .setViewName("fault/online-state");
        registry.addViewController(UrlMappingConsts.FAULT_BASE_MAPPING_V1 + "/page/add").setViewName("fault/add");
        registry.addViewController(UrlMappingConsts.FAULT_BASE_MAPPING_V1 + "/page/update").setViewName("fault/update");
        registry.addViewController(UrlMappingConsts.FAULT_BASE_MAPPING_V1 + "/page/allocation")
                .setViewName("fault/allocation");
        registry.addViewController(UrlMappingConsts.FAULT_BASE_MAPPING_V1 + "/page/accept").setViewName("fault/accept");
        registry.addViewController(UrlMappingConsts.FAULT_BASE_MAPPING_V1 + "/page/handle").setViewName("fault/handle");
        registry.addViewController(UrlMappingConsts.FAULT_BASE_MAPPING_V1 + "/page/revisit")
                .setViewName("fault/revisit");
        registry.addViewController(UrlMappingConsts.FAULT_BASE_MAPPING_V1 + "/page/evaluation")
                .setViewName("fault/evaluation");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

}
