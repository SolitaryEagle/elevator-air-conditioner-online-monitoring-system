package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.BusinessException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.UserException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.vo.UserVO;

/**
 * @author 覃国强
 * @date 2019-02-13
 */
public interface UserService {

    User save(UserVO userVO) throws BusinessException;

    int updateActivation(User user) throws BusinessException;

    int updatePassword(UserVO userVO) throws BusinessException;

    int update(UserVO userVO) throws BusinessException;

    User findById(UserVO userVO) throws BusinessException;

    User findById(Long userId) throws BusinessException;

    User findByUsername(UserVO userVO) throws BusinessException;

    User findByUsername(String username) throws BusinessException;

    User findByEmail(UserVO userVO) throws BusinessException;

    User findByEmail(String email) throws BusinessException;

}
