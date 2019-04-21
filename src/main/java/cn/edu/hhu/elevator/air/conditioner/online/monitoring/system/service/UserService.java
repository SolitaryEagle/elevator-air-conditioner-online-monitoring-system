package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.vo.UserVO;

/**
 * @author 覃国强
 * @date 2019-02-13
 */
public interface UserService {

    User save(UserVO userVO);

    int updateActivation(User user);

    int updatePassword(UserVO userVO);

    int update(UserVO userVO);

    User findById(UserVO userVO);

    User findById(Long userId);

    User findByUsername(UserVO userVO);

    User findByUsername(String username);

    User findByEmail(UserVO userVO);

    User findByEmail(String email);

}
