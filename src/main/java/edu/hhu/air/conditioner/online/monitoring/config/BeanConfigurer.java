package edu.hhu.air.conditioner.online.monitoring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author 覃国强
 * @date 2019/5/6 15:02
 */
@Configuration
public class BeanConfigurer {

    /**
     * 异常处理工具类
     * @return ResponseEntityExceptionHandler 的子类
     */
    @Bean
    public ResponseEntityExceptionHandler responseEntityExceptionHandler() {
        return new ResponseEntityExceptionHandler() {};
    }

    /**
     * Json 工具类
     * @return {@link com.fasterxml.jackson.databind.ObjectMapper ObjectMapper}
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
