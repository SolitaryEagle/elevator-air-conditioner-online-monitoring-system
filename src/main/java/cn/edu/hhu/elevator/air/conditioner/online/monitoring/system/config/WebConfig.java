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

    public static final String userBaseMapping = "/v1/monitoring-system/users";

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
        registry.addViewController(userBaseMapping + "/page/login").setViewName("user/login");
        registry.addViewController(userBaseMapping + "/page/register").setViewName("user/register");
        registry.addViewController(userBaseMapping + "/page/forgot-password").setViewName("user/forgot-password");
        registry.addViewController("").setViewName("user/register");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

}
