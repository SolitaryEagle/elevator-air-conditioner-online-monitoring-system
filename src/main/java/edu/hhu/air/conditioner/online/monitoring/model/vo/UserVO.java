package edu.hhu.air.conditioner.online.monitoring.model.vo;

import edu.hhu.air.conditioner.online.monitoring.constant.RoleEnum;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author 覃国强
 * @date 2019-03-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO implements Serializable {

    private static final long serialVersionUID = 3313722011714460742L;
    private Long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;

    @Email
    @Size(min = 2, max = 30)
    private String email;

    @NotBlank
    @Pattern(regexp = "^1\\d{10}")
    private String phoneNumber;
    private Boolean activation;
    private RoleEnum role;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
