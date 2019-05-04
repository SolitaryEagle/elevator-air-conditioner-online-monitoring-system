package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.config;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.framework.interceptor.LoginInterceptor;
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
public class WebConfig implements WebMvcConfigurer {

    public static final String PROJECT_BASE_MAPPING_V1 = "/v1/monitoring-system";
    public static final String USER_BASE_MAPPING_V1 = PROJECT_BASE_MAPPING_V1 + "/users";
    public static final String AIR_CONDITIONER_BASE_MAPPING_V1 = PROJECT_BASE_MAPPING_V1 + "/air-conditioners";

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
        registry.addViewController(PROJECT_BASE_MAPPING_V1 + "/page/index").setViewName("map/address-catalogue");
        registry.addViewController(USER_BASE_MAPPING_V1 + "/page/login").setViewName("user/login");
        registry.addViewController(USER_BASE_MAPPING_V1 + "/page/register").setViewName("user/register");
        registry.addViewController(USER_BASE_MAPPING_V1 + "/page/forgot-password").setViewName("user/forgot-password");
        registry.addViewController(AIR_CONDITIONER_BASE_MAPPING_V1 + "/page/add-info")
                .setViewName("air-conditioner/add-info");
        registry.addViewController(AIR_CONDITIONER_BASE_MAPPING_V1 + "/page/online-state")
                .setViewName("air-conditioner/online-state");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

}
