package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.model.vo.UserVO;

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
