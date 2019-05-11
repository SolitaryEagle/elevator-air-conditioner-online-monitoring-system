package edu.hhu.air.conditioner.online.monitoring.controller.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hhu.air.conditioner.online.monitoring.constant.SystemConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.JsonParseException;
import edu.hhu.air.conditioner.online.monitoring.model.CustomizeExceptionResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 覃国强
 * @date 2019/5/5 15:16
 */
@Slf4j
@RestControllerAdvice(annotations = { RestController.class, ResponseBody.class })
public class ResponseBodyHandlerAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private ObjectMapper objectMapper;
    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        objectMapper.setDateFormat(new SimpleDateFormat(dateFormat));
        Object result;
        if (body instanceof CustomizeExceptionResponseBody) {
            result = body;
        } else {
            Map<String, Object> map = new HashMap<>(3);
            map.put("code", ((ServletServerHttpResponse) response).getServletResponse().getStatus());
            map.put("message", "success");
            map.put("data", body);
            result = map;
        }
        try {
            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            log.warn("Json解析异常！body：{}", body, e);
            throw new JsonParseException(ErrorCodeEnum.JSON_PARSE_ERROR, "data", SystemConsts.SYSTEM_ERROR_PROMPT);

        }
    }

}
