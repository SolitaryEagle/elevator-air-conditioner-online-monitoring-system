package edu.hhu.air.conditioner.online.monitoring.controller;

import edu.hhu.air.conditioner.online.monitoring.constant.SessionConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.ActivationException;
import edu.hhu.air.conditioner.online.monitoring.exception.UserException;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.model.request.UserAutoLoginRequest;
import edu.hhu.air.conditioner.online.monitoring.model.request.UserRegisterRequest;
import edu.hhu.air.conditioner.online.monitoring.model.request.UserResetPasswordRequest;
import edu.hhu.air.conditioner.online.monitoring.model.response.UserResponse;
import edu.hhu.air.conditioner.online.monitoring.service.MailService;
import edu.hhu.air.conditioner.online.monitoring.service.UserService;
import edu.hhu.air.conditioner.online.monitoring.util.VerificationCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 覃国强
 * @date 2019-02-12
 */
@Slf4j
@RestController
@RequestMapping("/v1/monitoring-system/users")
public class UserController {

    @Autowired
    private HttpSession session;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public void test(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        int a = 1 / 0;
        System.out.println(session.getAttribute(SessionConsts.VERIFICATION_CODE_KEY));
    }

    /**
     * 注册请求
     *
     * @param userRegisterRequest 注册表单中提交的信息
     * @return 注册成功后获取到的 User 部分信息
     */
    @PostMapping("/add")
    public UserResponse register(@ModelAttribute @Valid UserRegisterRequest userRegisterRequest) {
        if (StringUtils.contains(userRegisterRequest.getUsername(), "@")) {
            throw new UserException(ErrorCodeEnum.ALREADY_EXISTS, "username", "用户名不能包含@字符！");
        }
        if (!StringUtils.equals(userRegisterRequest.getPassword(), userRegisterRequest.getRepassword())) {
            throw new UserException(ErrorCodeEnum.INCONSISTENT, "password", "两次密码不一致！");
        }
        User user = new User();
        BeanUtils.copyProperties(userRegisterRequest, user);
        User saveUser = userService.add(user);
        session.setAttribute(SessionConsts.LOGIN_USER_KEY, saveUser);
        log.info("注册成功! user: {}", saveUser);
        return UserResponse.valueOf(saveUser);
    }

    /**
     * 获取激活验证码请求
     *
     * @param receiverEmail 接收验证码的邮箱
     * @return 邮件发送的反馈信息
     */
    @ResponseBody
    @GetMapping("/activation")
    public String sendActivationCode(@RequestParam @NotNull @Email String receiverEmail) {
        // 校验该邮箱是否存在
        User testUser = userService.findByEmail(receiverEmail);
        if (testUser.getActivation()) {
            throw new UserException(ErrorCodeEnum.ALREADY_EXISTS, "activation", "账户已激活！");
        }
        // 获取激活码（验证码的一种）
        String activationCode = VerificationCodeUtils.generate();
        session.setAttribute(SessionConsts.VERIFICATION_CODE_KEY, activationCode);
        //发送邮件，
        mailService.sendActivationCode(receiverEmail, activationCode);
        log.debug("邮件已发送！receiverEmail: {}", receiverEmail);
        return "邮件已发送！";
    }

    /**
     * 验证激活码请求
     *
     * @param activationEmail 激活邮箱
     * @param activationCode  待验证的激活码
     * @return 激活结果的反馈信息
     */
    @PostMapping("/activation/verify")
    public String verifyActivationCode(@RequestParam @NotBlank @Email String activationEmail,
            @RequestParam String activationCode) {
        User testUser = userService.findByEmail(activationEmail);
        if (testUser.getActivation()) {
            throw new ActivationException(ErrorCodeEnum.ALREADY_EXISTS, "activation", "账户已激活!");
        }
        String testActivationCode = (String) session.getAttribute(SessionConsts.VERIFICATION_CODE_KEY);
        if (!StringUtils.equals(activationCode, testActivationCode)) {
            throw new ActivationException(ErrorCodeEnum.INVALID, "activation", "验证码错误或已失效!");
        }
        testUser.setActivation(Boolean.TRUE);
        userService.updateActivation(testUser);
        log.debug("激活成功! activationEmail: {}", activationEmail);
        return "激活成功!";
    }

    /**
     * 找回密码请求
     *
     * @param userResetPasswordRequest 重置密码提交的表单信息
     * @return 重置密码结果的反馈信息
     */
    @PostMapping("/password/reset")
    public String retrievePassword(@ModelAttribute @Valid UserResetPasswordRequest userResetPasswordRequest) {
        String testActivationCode = (String) session.getAttribute(SessionConsts.VERIFICATION_CODE_KEY);
        if (!StringUtils.equals(userResetPasswordRequest.getActivationCode(), testActivationCode)) {
            throw new ActivationException(ErrorCodeEnum.INVALID, "activation", "验证码错误或已失效!");
        }
        if (!StringUtils.equals(userResetPasswordRequest.getNewPassword(),
                userResetPasswordRequest.getReNewPassword())) {
            throw new UserException(ErrorCodeEnum.INCONSISTENT, "password", "两次密码不一致！");
        }
        User user = User.builder().email(userResetPasswordRequest.getEmail())
                .password(userResetPasswordRequest.getNewPassword()).build();
        user.setPassword(userResetPasswordRequest.getNewPassword());
        userService.updatePassword(user);
        log.info("密码重置成功！email：{}", userResetPasswordRequest.getEmail());
        return "密码重置成功，请重新登录！";
    }

    /**
     * 登录请求
     *
     * @param usernameOrEmail 用户名或者邮箱
     * @param password        密码
     * @return 登录的 User 部分信息
     */
    @PostMapping("/login")
    public UserResponse login(@RequestParam String usernameOrEmail, @RequestParam String password) {
        User testUser;
        if (StringUtils.contains(usernameOrEmail, "@")) {
            testUser = userService.findByEmail(usernameOrEmail);
        } else {
            testUser = userService.findByUsername(usernameOrEmail);
        }
        // 验证密码是否正确
        if (!userService.validatePassword(testUser, password)) {
            throw new UserException(ErrorCodeEnum.INCONSISTENT, "password", "用户名、邮箱或密码错误!");
        }
        // 验证是否激活
        if (!testUser.getActivation()) {
            throw new UserException(ErrorCodeEnum.ACCOUNT_INACTIVATED, "activation", "请先激活账户！");
        }
        session.setAttribute(SessionConsts.LOGIN_USER_KEY, testUser);
        log.debug("登录成功! username: {}, email: {}", testUser.getUsername(), testUser.getEmail());
        return UserResponse.valueOf(testUser);
    }

    /**
     * 自动登入请求
     *
     * @param userAutoLoginRequest 自动登录的用户信息
     * @return 登录的 User 部分信息
     */
    @PostMapping("/auto-login")
    public UserResponse autoLogin(@ModelAttribute UserAutoLoginRequest userAutoLoginRequest) {
        User testUser = userService.autoLogin(userAutoLoginRequest.toUser());
        session.setAttribute(SessionConsts.LOGIN_USER_KEY, testUser);
        log.debug("自动登录成功! userId: {}, username: {}, email: {}", testUser.getId(), testUser.getUsername(),
                testUser.getEmail());
        return UserResponse.valueOf(testUser);
    }


/*

    // 修改密码

    @PutMapping("/password")
    public String updatePassword(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @Size(min = 8, max = 16) String oldPassword, @Size(min = 8, max = 16) String newPassword,
            String reNewPassword) {

        // 验证旧密码是否正确
        try {
            if (!PasswordEncryptionUtils.validate(oldPassword, user.getPassword())) {
                return "旧密码错误";
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("加密密码验证器错误，password：{}", oldPassword, e);
            throw new UserException(ErrorCodeEnum.PASSWORD_ENCRYPTION_ERROR, "password", "加密密码验证器错误");
        }
        if (!StringUtils.equals(newPassword, reNewPassword)) {
            return "两次密码不一致";
        }

        UserVO userVO = UserVO.builder().email(user.getEmail()).password(newPassword).build();
        int result = userService.updatePassword(userVO);
        if (result <= 0) {
            return "修改密码失败";
        }
        User testUser = userService.findByEmail(userVO);
        session.setAttribute(SessionConsts.LOGIN_USER_KEY, testUser);
        return "修改密码成功";
    }

*/
}
