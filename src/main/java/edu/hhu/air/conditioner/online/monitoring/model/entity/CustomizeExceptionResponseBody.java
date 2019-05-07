package edu.hhu.air.conditioner.online.monitoring.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 覃国强
 * @date 2019/5/8 13:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomizeExceptionResponseBody {

    private int code;
    private String message;
    private Object data;

}
