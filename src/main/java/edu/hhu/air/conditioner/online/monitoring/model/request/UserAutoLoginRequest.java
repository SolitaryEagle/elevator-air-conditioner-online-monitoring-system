package edu.hhu.air.conditioner.online.monitoring.model.request;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 覃国强
 * @date 2019/5/9 22:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAutoLoginRequest {

    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
    private String username;
    private String email;
    private String phoneNumber;
    private Boolean activation;
    private RoleEnum role;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
