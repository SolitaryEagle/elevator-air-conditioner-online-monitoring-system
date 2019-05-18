package edu.hhu.air.conditioner.online.monitoring.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author 覃国强
 * @date 2019/5/9 15:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResetPasswordRequest {

    @NotBlank
    @Email
    @Size(min = 2, max = 30)
    private String email;

    @NotBlank
    @Size(min = 8, max = 16)
    private String newPassword;

    @NotBlank
    @Size(min = 8, max = 16)
    private String reNewPassword;

    @NotBlank
    @Size(min = 6, max = 6)
    private String activationCode;

}
