package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.controller;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.MailConsts;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.SessionConsts;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.ResponseCode;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.UserException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.vo.UserVO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.UserService;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.util.PasswordEncryptionUtils;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.util.VerificationCodeUtils;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * @author 覃国强
 * @date 2019-02-12
 */
@Slf4j
@RestController
@RequestMapping("/v1/monitoring-system/users")
public class UserController {

    private final UserService userService;
    private final MailSender mailSender;
    private final HttpSession session;

    @Autowired
    public UserController(UserService userService, MailSender mailSender, HttpSession session) {
        this.userService = userService;
        this.mailSender = mailSender;
        this.session = session;
    }

    @GetMapping("/test")
    public void test(HttpSession session) {
        System.out.println(session.getAttribute(SessionConsts.VERIFICATION_CODE_KEY));
        System.out.println(this.session.getAttribute(SessionConsts.VERIFICATION_CODE_KEY));
    }

    // 注册

    @PostMapping
    public Object register(@Valid UserVO userVO, String repassword, BindingResult result) {

        if (result.hasErrors()) {
            return result.getAllErrors();
        }
        if (!StringUtils.equals(userVO.getPassword(), repassword)) {
            return "两次密码不一致";
        }

        User saveUser = userService.save(userVO);
        session.setAttribute(SessionConsts.LOGIN_USER_KEY, saveUser);
        log.debug("注册成功");
        return saveUser;
    }

    // 获取激活验证码

    @GetMapping("/activation")
    public String sendActivationCode(@Email String receiverEmail) {

        // 获取验证码
        String verificationCode = VerificationCodeUtils.generate();
        session.setAttribute(SessionConsts.VERIFICATION_CODE_KEY, verificationCode);

        //发送邮件，  TODO 但是由于发送过程需要与smtp服务器进行交互，所以有点慢，后期可以使用异步实现
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MailConsts.SENDER_EMAIL);
        message.setTo(receiverEmail);
        message.setText(MailConsts.EMAIL_TEXT_TEMPLATE + verificationCode);
        try {
            this.mailSender.send(message);
        } catch (Exception e) {
            log.error("邮件发送出错", e);
        }
        log.debug("邮件已发送");
        return "邮件已发送";
    }

    // 验证激活码

    @PutMapping("/activation")
    public String validateActivationCode(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @Pattern(regexp = "\\d{6}") String verificationCode) {

        User testUser = userService.findById(user.getId());
        if (testUser.getActivation()) {
            throw new UserException(ResponseCode.ALREADY_EXISTS, "activation", "账户已激活");
        }
        String testVerificationCode = (String) session.getAttribute(SessionConsts.VERIFICATION_CODE_KEY);
        if (!StringUtils.equals(verificationCode, testVerificationCode)) {
            throw new UserException(ResponseCode.INCONSISTENT, "verificationCode", "验证码错误或已失效");
        }

        int result = userService.updateActivation(user);
        if (result <= 0) {
            log.info("激活失败； userId: {}, userEmail: {}", user.getId(), user.getEmail());
            return "激活失败";
        }
        log.debug("激活成功");
        return "激活成功";
    }

    // 登录

    @PostMapping("/login")
    public String login(String usernameOrEmail, String password) {

        User testUser;
        // 判断用户输入的是邮箱还是用户名
        if (StringUtils.contains(usernameOrEmail, "@")) {
            testUser = userService.findByEmail(usernameOrEmail);
        } else {
            testUser = userService.findByUsername(usernameOrEmail);
        }
        // 验证密码是否正确
        try {
            if (!PasswordEncryptionUtils.validate(password, testUser.getPassword())) {
                return "用户名、邮箱或密码错误";
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("密码验证器错误，password：{}", password, e);
            throw new UserException(ResponseCode.PASSWORD_ENCRYPTION_ERROR, "password", "密码验证器错误");
        }

        session.setAttribute(SessionConsts.LOGIN_USER_KEY, testUser);
        log.info("登录成功");
        return "登录成功";
    }

    // 找回密码

    @PostMapping("/password")
    public String retrievePassword(@Email String email, @Size(min = 8, max = 16) String newPassword,
            String reNewPassword, @Pattern(regexp = "\\d{6}") String verificationCode) {

        String testVerificationCode = (String) session.getAttribute(SessionConsts.VERIFICATION_CODE_KEY);
        if (!StringUtils.equals(verificationCode, testVerificationCode)) {
            throw new UserException(ResponseCode.INCONSISTENT, "verificationCode", "验证码错误或已失效");
        }
        if (!StringUtils.equals(newPassword, reNewPassword)) {
            return "两次密码不一致";
        }

        UserVO userVO = UserVO.builder().email(email).password(newPassword).build();
        int result = userService.updatePassword(userVO);
        if (result <= 0) {
            return "重置密码失败";
        }
        return "重置密码成功";
    }

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
            throw new UserException(ResponseCode.PASSWORD_ENCRYPTION_ERROR, "password", "加密密码验证器错误");
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

    // 修改用户信息

    @PutMapping
    public String updateUser(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user, @Valid UserVO userVO) {

        // 验证旧密码是否正确
        try {
            if (!PasswordEncryptionUtils.validate(userVO.getPassword(), user.getPassword())) {
                return "旧密码错误";
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("加密密码验证器错误，password：{}", userVO.getPassword(), e);
            throw new UserException(ResponseCode.PASSWORD_ENCRYPTION_ERROR, "password", "加密密码验证器错误");
        }
        userVO.setId(user.getId());
        userService.update(userVO);
        return null;
    }


}
