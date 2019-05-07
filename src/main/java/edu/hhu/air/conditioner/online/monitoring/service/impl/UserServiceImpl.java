package edu.hhu.air.conditioner.online.monitoring.service.impl;

import edu.hhu.air.conditioner.online.monitoring.constant.SystemConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.RoleEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.ActivationException;
import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import edu.hhu.air.conditioner.online.monitoring.exception.UserException;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.repository.UserRepository;
import edu.hhu.air.conditioner.online.monitoring.service.UserService;
import edu.hhu.air.conditioner.online.monitoring.util.PasswordEncryptionUtils;
import edu.hhu.air.conditioner.online.monitoring.util.TimeStampUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019-02-13
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User add(User user) {
        Optional<User> optional = userRepository.findByUsername(user.getUsername());
        if (optional.isPresent()) {
            log.debug("用户名已存在！username：{}", user.getUsername());
            throw new UserException(ErrorCodeEnum.ALREADY_EXISTS, "username", "用户名已存在");
        }
        optional = userRepository.findByEmail(user.getEmail());
        if (optional.isPresent()) {
            log.debug("邮箱已注册！email：{}", user.getEmail());
            throw new UserException(ErrorCodeEnum.ALREADY_EXISTS, "email", "邮箱已注册");
        }

        // 设置默认值
        user.setGmtCreate(TimeStampUtils.now());
        user.setGmtModified(TimeStampUtils.now());
        user.setActivation(Boolean.FALSE);
        user.setRole(RoleEnum.ORDINARY);

        // 密码加密
        String password = user.getPassword();
        try {
            user.setPassword(PasswordEncryptionUtils.generate(password));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("密码加密器错误，password：{}", password, e);
            throw new BusinessException(ErrorCodeEnum.PASSWORD_ENCRYPTION_ERROR, "password",
                    SystemConsts.SYSTEM_ERROR_PROMPT);
        }
        return userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateActivation(User user) {
        Optional<User> optional = userRepository.findById(user.getId());
        if (!optional.isPresent()) {
            log.debug("用户不存在！userId：{}", user.getId());
            throw new UserException(ErrorCodeEnum.MISSING, "userId", "用户不存在");
        }
        user.setGmtModified(TimeStampUtils.now());
        int result = userRepository.updateActivationById(user);
        if (result <= 0) {
            log.warn("更新activation字段失败； userId: {}, userEmail: {}, activation: {}",
                    user.getId(), user.getEmail(), user.getActivation());
            throw new ActivationException(ErrorCodeEnum.INTERNAL_SERVER_ERROR, "activation",
                    SystemConsts.SYSTEM_ERROR_PROMPT);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updatePassword(User user) {
        // 更新先查询
        Optional<User> optional = userRepository.findByEmail(user.getEmail());
        if (!optional.isPresent()) {
            log.warn("用户更新密码时，邮箱未注册！email: {}", user.getEmail());
            throw new UserException(ErrorCodeEnum.MISSING, "email", "邮箱未注册");
        }

        // 密码加密
        String password = user.getPassword();
        try {
            user.setPassword(PasswordEncryptionUtils.generate(password));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("密码加密器错误，password：{}", password, e);
            throw new BusinessException(ErrorCodeEnum.PASSWORD_ENCRYPTION_ERROR, "password",
                    SystemConsts.SYSTEM_ERROR_PROMPT);
        }
        user.setGmtModified(TimeStampUtils.now());
        int result = userRepository.updatePasswordByEmail(user);
        if (result <= 0) {
            log.warn("更新password字段失败； userId: {}, userEmail: {}, password: {}", user.getId(), user.getEmail(),
                    password);
            throw new ActivationException(ErrorCodeEnum.INTERNAL_SERVER_ERROR, "password",
                    SystemConsts.SYSTEM_ERROR_PROMPT);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(User user) {
        Optional<User> optional = userRepository.findByUsername(user.getUsername());
        if (optional.isPresent()) {
            log.debug("用户名已存在！username: {}", user.getUsername());
            throw new UserException(ErrorCodeEnum.ALREADY_EXISTS, "username", "用户名已存在");
        }
        user.setGmtModified(TimeStampUtils.now());
        return userRepository.updateById(user);
    }

    @Override
    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public User findById(Long userId) {
        Optional<User> optional = userRepository.findById(userId);
        if (!optional.isPresent()) {
            log.warn("用户不存在! userId: {}", userId);
            throw new UserException(ErrorCodeEnum.MISSING, "userId", "用户不存在");
        }
        return optional.get();
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> optional = userRepository.findByUsername(username);
        if (!optional.isPresent()) {
            log.debug("用户名不存在! username: {}", username);
            throw new UserException(ErrorCodeEnum.MISSING, "username", "用户名不存在");
        }
        return optional.get();
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        if (!optional.isPresent()) {
            log.debug("邮箱未注册！email: {}", email);
            throw new UserException(ErrorCodeEnum.MISSING, "email", "邮箱未注册");
        }
        return optional.get();
    }

    @Override
    public boolean validatePassword(User user, String password) {
        try {
            return PasswordEncryptionUtils.validate(password, user.getPassword());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("密码验证器错误，password：{}", password, e);
            throw new BusinessException(ErrorCodeEnum.PASSWORD_DECRYPTION_ERROR, "password",
                    SystemConsts.SYSTEM_ERROR_PROMPT);
        }
    }

    @Override
    public User autoLogin(User user) {
        User testUser = findById(user.getId());
        // 验证数据是否被更改过 以及 是否激活
        if (isNotModifiedUser(user, testUser) && testUser.getActivation()) {
            return testUser;
        }
        if (!testUser.getActivation()) {
            throw new UserException(ErrorCodeEnum.AUTO_LOGIN_ERROR, "autoLogin", "账户未激活，无法自动登入，请手动登录！");
        }
        throw new UserException(ErrorCodeEnum.AUTO_LOGIN_ERROR, "autoLogin", "账户记录已更改，无法自动登入，请手动登录！");
    }

    // 检查一个User的数据库记录是否被更改过     TODO 这个方法名还没想好，不知道合不合适

    private boolean isNotModifiedUser(User source, User target) {
        return DateUtils.isSameInstant(source.getGmtModified(), target.getGmtModified());
    }

}
