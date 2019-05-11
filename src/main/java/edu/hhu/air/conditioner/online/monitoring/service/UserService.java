package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.model.request.UserAutoLoginRequest;

/**
 * @author 覃国强
 * @date 2019-02-13
 */
public interface UserService {

    User add(User user);

    int updateActivation(User user);

    int updatePassword(User user);

    int update(User user);

    boolean existsById(Long id);

    User getById(Long id);

    User getByUsername(String username);

    User getByEmail(String email);

    boolean validatePassword(User user, String password);

    User autoLogin(User user);

}
