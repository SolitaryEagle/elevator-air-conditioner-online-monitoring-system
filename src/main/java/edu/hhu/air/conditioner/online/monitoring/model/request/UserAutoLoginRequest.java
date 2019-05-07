package edu.hhu.air.conditioner.online.monitoring.model.request;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.RoleEnum;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.util.TimeStampUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
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

    public static UserAutoLoginRequest valueOf(User user) {
        UserAutoLoginRequest result = new UserAutoLoginRequest();
        BeanUtils.copyProperties(user, result);
        return result;
    }

    public User toUser() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        user.setGmtCreate(TimeStampUtils.fromDate(this.getGmtCreate()));
        user.setGmtModified(TimeStampUtils.fromDate(this.getGmtModified()));
        return user;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
