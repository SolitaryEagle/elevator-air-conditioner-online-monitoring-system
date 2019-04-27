package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.impl;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.RoleEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.BusinessException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.ResponseCode;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.UserException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.vo.UserVO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.repository.UserRepository;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.UserService;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.util.PasswordEncryptionUtils;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 覃国强
 * @date 2019-02-13
 */
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User save(UserVO userVO) throws BusinessException {

        Optional<User> optional = userRepository.findByUsername(userVO.getUsername());
        if (optional.isPresent()) {
            log.warn("用户名已存在！username ：{}", userVO.getUsername());
            throw new UserException(ResponseCode.ALREADY_EXISTS, "username", "用户名已存在");
        }
        optional = userRepository.findByEmail(userVO.getEmail());
        if (optional.isPresent()) {
            log.warn("邮箱已注册！email ：{}", userVO.getEmail());
            throw new UserException(ResponseCode.ALREADY_EXISTS, "email", "邮箱已注册");
        }

        // 设置默认值
        userVO.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        userVO.setGmtModified(new Timestamp(System.currentTimeMillis()));
        userVO.setActivation(Boolean.FALSE);
        userVO.setRole(RoleEnum.ORDINARY);

        // 密码加密
        String password = userVO.getPassword();
        try {
            userVO.setPassword(PasswordEncryptionUtils.generate(password));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("密码加密器错误，password：{}", password, e);
            throw new UserException(ResponseCode.PASSWORD_ENCRYPTION_ERROR, "password", "密码加密错误");
        }

        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        return userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateActivation(User user) throws BusinessException {

        Optional<User> optional = userRepository.findById(user.getId());
        if (!optional.isPresent()) {
            log.warn("用户不存在！id ：{}", user.getId());
            throw new UserException(ResponseCode.MISSING, "id", "用户不存在");
        }
        user.setActivation(Boolean.TRUE);
        user.setGmtModified(new Timestamp(System.currentTimeMillis()));
        return userRepository.updateActivationById(user.getId(), user.getActivation(), user.getGmtModified());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updatePassword(UserVO userVO) throws BusinessException {

        // 更新先查询
        Optional<User> optional = userRepository.findByEmail(userVO.getEmail());
        if (!optional.isPresent()) {
            log.warn("邮箱未注册！email : {}", userVO.getEmail());
            throw new UserException(ResponseCode.MISSING, "email", "邮箱未注册");
        }

        // 密码加密
        String password = userVO.getPassword();
        try {
            userVO.setPassword(PasswordEncryptionUtils.generate(password));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("密码加密器错误，password：{}", password, e);
            throw new UserException(ResponseCode.PASSWORD_ENCRYPTION_ERROR, "password", "密码加密错误");
        }
        userVO.setGmtModified(new Timestamp(System.currentTimeMillis()));
        return userRepository.updatePasswordByEmail(userVO.getEmail(), userVO.getPassword(), userVO.getGmtModified());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(UserVO userVO) throws BusinessException {

        Optional<User> optional = userRepository.findByUsername(userVO.getUsername());
        if (optional.isPresent()) {
            log.warn("用户名已存在！username : {}", userVO.getUsername());
            throw new UserException(ResponseCode.ALREADY_EXISTS, "username", "用户名已存在");
        }
        userVO.setGmtModified(new Timestamp(System.currentTimeMillis()));
        return userRepository
                .updateById(userVO.getId(), userVO.getUsername(), userVO.getPhoneNumber(), userVO.getGmtModified());
    }

    @Override
    public User findById(UserVO userVO) throws BusinessException {
        return findById(userVO.getId());
    }

    @Override
    public User findById(Long userId) throws BusinessException {

        Optional<User> optional = userRepository.findById(userId);
        if (!optional.isPresent()) {
            log.warn("用户不存在! userId : {}", userId);
            throw new UserException(ResponseCode.MISSING, "userId", "用户不存在");
        }
        return optional.get();
    }

    @Override
    public User findByUsername(UserVO userVO) throws BusinessException {
        return findByUsername(userVO.getUsername());
    }

    @Override
    public User findByUsername(String username) throws BusinessException {

        Optional<User> optional = userRepository.findByUsername(username);
        if (!optional.isPresent()) {
            log.warn("用户名不存在! username : {}", username);
            throw new UserException(ResponseCode.MISSING, "username", "用户不存在");
        }
        return optional.get();
    }

    @Override
    public User findByEmail(UserVO userVO) throws BusinessException {
        return findByEmail(userVO.getEmail());
    }

    @Override
    public User findByEmail(String email) throws BusinessException {

        Optional<User> optional = userRepository.findByEmail(email);
        if (!optional.isPresent()) {
            log.warn("邮箱为注册！email : {}", email);
            throw new UserException(ResponseCode.MISSING, "email", "邮箱未注册");
        }
        return optional.get();
    }

}
