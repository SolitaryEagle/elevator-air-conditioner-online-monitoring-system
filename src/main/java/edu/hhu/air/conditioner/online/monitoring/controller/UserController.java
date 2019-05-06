package edu.hhu.air.conditioner.online.monitoring.controller;

import edu.hhu.air.conditioner.online.monitoring.constant.MailConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.RequestConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.SessionConsts;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.model.vo.UserVO;
import edu.hhu.air.conditioner.online.monitoring.service.UserService;
import edu.hhu.air.conditioner.online.monitoring.util.PasswordEncryptionUtils;
import edu.hhu.air.conditioner.online.monitoring.util.VerificationCodeUtils;
import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author 覃国强
 * @date 2019-02-12
 */
@Slf4j
@Controller
@RequestMapping("/v1/monitoring-system/users")
public class UserController {

    private final UserService userService;
    private final MailSender mailSender;

    @Autowired
    public UserController(UserService userService, MailSender mailSender) {
        this.userService = userService;
        this.mailSender = mailSender;
    }

    @GetMapping("/test")
    public void test(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        int a = 1 / 0;
        System.out.println(session.getAttribute(SessionConsts.VERIFICATION_CODE_KEY));
    }

    // 注册

    @PostMapping("/add")
    public String register(@Valid UserVO userVO, HttpServletRequest request, HttpSession session) {
        request.setAttribute(RequestConsts.USER_REGISTER_FORM_KEY, userVO);
        if (StringUtils.contains(userVO.getUsername(), "@")) {
            request.setAttribute(RequestConsts.TIP_KEY, "用户名不能包含@字符！");
            return "user/register";
        }
        User saveUser = null;
        try {
            saveUser = userService.save(userVO);
        } catch (BusinessException e) {
            request.setAttribute(RequestConsts.TIP_KEY, e.getMessage());
            return "user/register";
        }
        session.setAttribute(SessionConsts.LOGIN_USER_KEY, saveUser);
        log.info("注册成功");
        request.setAttribute(RequestConsts.TIP_KEY, "注册成功，请激活邮箱！");
        return "user/activation";
    }

    // 获取激活验证码

    @ResponseBody
    @GetMapping("/activation")
    public String sendActivationCode(@Email String receiverEmail, HttpSession session) {

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
            return "邮件发送出错！";
        }
        log.debug("邮件已发送");
        return "邮件已发送！";
    }

    // 验证激活码

    @PostMapping("/activation/verify")
    public String validateActivationCode(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            String verificationCode, HttpServletRequest request, HttpSession session) {

        User testUser = null;
        try {
            testUser = userService.findById(user.getId());
        } catch (BusinessException e) {
            request.setAttribute(RequestConsts.TIP_KEY, e.getMessage());
            return "user/activation";
        }
        if (testUser.getActivation()) {
            request.setAttribute(RequestConsts.TIP_KEY, "账户已激活!");
            return "user/activation";
        }
        String testVerificationCode = (String) session.getAttribute(SessionConsts.VERIFICATION_CODE_KEY);
        if (!StringUtils.equals(verificationCode, testVerificationCode)) {
            request.setAttribute(RequestConsts.TIP_KEY, "验证码错误或已失效!");
            return "user/activation";
        }

        int result = 0;
        try {
            result = userService.updateActivation(user);
        } catch (BusinessException e) {
            request.setAttribute(RequestConsts.TIP_KEY, e.getMessage());
            return "user/activation";
        }
        if (result <= 0) {
            log.warn("激活失败； userId: {}, userEmail: {}", user.getId(), user.getEmail());
            request.setAttribute(RequestConsts.TIP_KEY, "系统正在维护，请稍后重试!");
            return "user/activation";
        }
        log.debug("激活成功");
        return "map/address-catalogue";
    }

    // 登录

    @PostMapping("/login")
    public String login(String usernameOrEmail, String password, HttpServletRequest request, HttpSession session) {
        request.setAttribute(RequestConsts.USER_LOGIN_FORM_KEY, usernameOrEmail);
        User testUser = null;
        if (StringUtils.contains(usernameOrEmail, "@")) {
            try {
                testUser = userService.findByEmail(usernameOrEmail);
            } catch (BusinessException e) {
                request.setAttribute(RequestConsts.TIP_KEY, "邮箱未注册！");
                return "user/login";
            }
        } else {
            try {
                testUser = userService.findByUsername(usernameOrEmail);
            } catch (BusinessException e) {
                request.setAttribute(RequestConsts.TIP_KEY, "用户名未注册！");
                return "user/login";
            }
        }

        // 验证密码是否正确
        try {
            if (!PasswordEncryptionUtils.validate(password, testUser.getPassword())) {
                request.setAttribute(RequestConsts.TIP_KEY, "用户名、邮箱或密码错误!");
                return "user/login";
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("密码验证器错误，password：{}", password, e);
            request.setAttribute(RequestConsts.TIP_KEY, "系统正在维护！");
        }

        session.setAttribute(SessionConsts.LOGIN_USER_KEY, testUser);

        // 验证是否激活

        if (!testUser.getActivation()) {
            request.setAttribute(RequestConsts.TIP_KEY, "请先激活账户！");
            return "user/activation";
        }

        log.info("登录成功");
        return "map/address-catalogue";
    }

    // 找回密码

    @PostMapping("/password/reset")
    public String retrievePassword(@Email String email, @Size(min = 8, max = 16) String newPassword,
            String reNewPassword, String verificationCode, HttpServletRequest request, HttpSession session) {
        request.setAttribute(RequestConsts.USER_EMAIL_KEY, email);
        String testVerificationCode = (String) session.getAttribute(SessionConsts.VERIFICATION_CODE_KEY);
        if (!StringUtils.equals(verificationCode, testVerificationCode)) {
            request.setAttribute(RequestConsts.TIP_KEY, "验证码错误或已失效!");
            return "user/forgot-password";
        }
        if (!StringUtils.equals(newPassword, reNewPassword)) {
            request.setAttribute(RequestConsts.TIP_KEY, "两次密码不一致!");
            return "user/forgot-password";
        }

        UserVO userVO = UserVO.builder().email(email).password(newPassword).build();
        int result = 0;
        try {
            result = userService.updatePassword(userVO);
        } catch (BusinessException e) {
            request.setAttribute(RequestConsts.TIP_KEY, e.getMessage());
            return "user/forgot-password";
        }
        if (result <= 0) {
            request.setAttribute(RequestConsts.TIP_KEY, "系统正在维护，请稍后重试!");
            return "user/forgot-password";
        }
        log.info("密码重置成功！email ：{}", email);
        request.setAttribute(RequestConsts.TIP_KEY, "密码重置成功，请重新登录！");
        return "user/login";
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

*/
}
