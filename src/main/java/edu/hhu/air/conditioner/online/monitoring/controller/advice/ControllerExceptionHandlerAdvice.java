package edu.hhu.air.conditioner.online.monitoring.controller.advice;

import edu.hhu.air.conditioner.online.monitoring.constant.SystemConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import edu.hhu.air.conditioner.online.monitoring.model.CustomizeExceptionResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author 覃国强
 * @date 2019/5/6 15:11
 */
@Slf4j
@RestControllerAdvice(annotations = { RestController.class, ResponseBody.class })
public class ControllerExceptionHandlerAdvice {

    @Autowired
    private ResponseEntityExceptionHandler responseEntityExceptionHandler;

    /**
     * 使用 Spring MVC 提供的 SpringMVC标准异常处理工具 统一处理 SpringMVC标准异常
     *
     * @param ex         the target exception
     * @param webRequest the current request
     * @return 统一的 ResponseBody 格式
     */
    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class,
            BindException.class,
            NoHandlerFoundException.class,
            AsyncRequestTimeoutException.class
    })
    @ResponseBody
    public CustomizeExceptionResponseBody handleStandardSpringMVCException(Exception ex, WebRequest webRequest,
            HttpServletRequest httpServletRequest) {
        log.debug("", ex);

        CustomizeExceptionResponseBody result = new CustomizeExceptionResponseBody();
        String stackTrace = getStackTrace(ex);
        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = responseEntityExceptionHandler.handleException(ex, webRequest);

        } catch (Exception e) {
            // 这里一般不会进入，因为 @ExceptionHandler 声明的异常都处理了
            log.error("enter the edu.hhu.air.conditioner.online.monitoring.controller.advice"
                    + ".ControllerExceptionHandlerAdvice#handleStandardSpringMVCException "
                    + "method catch block, but it isn't supposed to be in here");
            throw new RuntimeException(SystemConsts.SYSTEM_ERROR_PROMPT);
        }
        HttpStatus httpStatus;
        // 查看 handleException 的源码可知，只有当发生 AsyncRequestTimeoutException 这个异常时，responseEntity 才有可能为 null
        if (responseEntity == null) {
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
        } else {
            // 查看 handleException 的源码可知，如果这个方法不发生异常，那么HttpStatus 就一定不为 null
            httpStatus = responseEntity.getStatusCode();
        }
        result.setCode(httpStatus.value());
        result.setMessage(ex.getMessage());
        result.setData(stackTrace);

        log.warn("Standard Spring MVC Exception. exception name: {}, exception message: {}, http code: {}, "
                        + "http reason phrase: {}, uri: {}",
                ex.getClass().getName(), ex.getMessage(), httpStatus.value(), httpStatus.getReasonPhrase(),
                httpServletRequest.getRequestURI());

        return result;
    }

    /**
     * 统一处理 BusinessException 异常
     *
     * @param ex the target exception
     * @return 统一的 ResponseBody 格式
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public CustomizeExceptionResponseBody handleBusinessException(BusinessException ex, HttpServletRequest request) {
        CustomizeExceptionResponseBody result = new CustomizeExceptionResponseBody();
        String stackTrace = getStackTrace(ex);
        ErrorCodeEnum errorCode = ex.getErrorCode();
        result.setCode(errorCode.getCode());
        result.setMessage(ex.getMessage());
        result.setData(stackTrace);

        log.warn("Business Exception. exception message: {}, exception filed: {}, error code: {}, "
                        + "error message: {}, uri: {}",
                ex.getMessage(), ex.getField(), errorCode.getCode(), errorCode.getMessage(), request.getRequestURI());

        return result;
    }

    /**
     * 统一处理 Exception 异常，这个方法用来捕捉漏网之鱼，即没有被上面的方法捕捉到的异常
     *
     * @param ex the target exception
     * @return 统一的 ResponseBody 格式
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CustomizeExceptionResponseBody handleException(Exception ex, HttpServletRequest request) {
        log.debug("", ex);

        CustomizeExceptionResponseBody result = new CustomizeExceptionResponseBody();
        String stackTrace = getStackTrace(ex);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        result.setCode(httpStatus.value());
        result.setMessage(ex.getMessage());
        result.setData(stackTrace);

        log.warn("Unknown Exception. exception message: {}, http code: {}, http reason phrase: {}, uri: {}",
                ex.getMessage(), httpStatus.value(), httpStatus.getReasonPhrase(), request.getRequestURI());

        return result;
    }

    /**
     * 获取 异常 中的 栈追踪 信息
     *
     * @param ex the target exception
     * @return printStackTrace 打印的字符串
     */
    private String getStackTrace(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        return stringWriter.toString();
    }

}
