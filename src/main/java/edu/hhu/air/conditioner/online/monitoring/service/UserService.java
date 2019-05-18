package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.RoleEnum;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.model.request.UserAutoLoginRequest;

import java.util.List;

/**
 * @author 覃国强
 * @date 2019-02-13
 */
public interface UserService {

    User add(User user);

    int updateActivation(User user);

    int updatePasswordByEmail(User user);

    boolean existsById(Long id);

    User getById(Long id);

    User getByUsername(String username);

    User getByEmail(String email);

    boolean validatePassword(String passwordRecord, String passwordInput);

    User autoLogin(User user);

    List<User> listByRole(RoleEnum role);

}
