package edu.hhu.air.conditioner.online.monitoring.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *      因为本项目的所有 @RestController 的返回值都做了统一的格式处理，所以在返回之前，会对返回值进行一次包装。详情请看
 * {@link edu.hhu.air.conditioner.online.monitoring.controller.advice.ResponseBodyHandlerAdvice
 * ResponseBodyHandlerAdvice} 的 beforeBodyWrite 方法。
 *
 *      但是，SpringBoot 框架在对这种包装转换的时，需要判断和设置 源返回值类型 和 目标返回值类型；其中有一个判断条件如下：
 * <hr><blockquote><pre>
 *		if (value instanceof CharSequence) {
 * 			body = value.toString();
 * 			valueType = String.class;   // 源返回值类型
 * 			targetType = String.class;  // 目标返回值类型
 *       }
 * </pre></blockquote><hr>
 *     由此可知，如果 @RestController 的返回值为 String 类型，那么目标返回值类型默认就是 String 类型！
 *     那么，介于此，我们可以有两种解决方案：
 *          1，将 {@link edu.hhu.air.conditioner.online.monitoring.controller.advice.ResponseBodyHandlerAdvice
 * ResponseBodyHandlerAdvice} 的 beforeBodyWrite 方法的真实返回值类型设置为 String 类型，即在该方法中手动进行 Json 格式化。
 *          2，就是新建本类，@RestController 的返回值为 String 类型，就使用本类进行包装一下。
 *     结论：显然，方法一 明显比 方法二 要好！所以这个类存在的意义就是为了记住这些信息！QAQ
 *
 *     详情请看 {@link org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor
 *  AbstractMessageConverterMethodProcessor} 的 writeWithMessageConverters 方法。
 *
 *      所以，本类的目的在于，包装 String 类型的返回值，防止进行返回值包装时报错。
 *
 * @author 覃国强
 * @date 2019/5/9 10:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {

    private String message;
}
