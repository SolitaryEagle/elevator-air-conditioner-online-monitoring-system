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
        registry.addViewController("").setViewName("register");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

}
