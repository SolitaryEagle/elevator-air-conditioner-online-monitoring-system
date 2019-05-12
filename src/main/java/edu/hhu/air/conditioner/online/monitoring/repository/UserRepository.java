package edu.hhu.air.conditioner.online.monitoring.repository;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.RoleEnum;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 覃国强
 * @date 2019-01-31
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update User set activation = :#{#user.activation}, gmtModified = :#{#user.gmtModified} "
            + "where id = :#{#user.id}")
    int updateActivationById(User user);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update User set password = :#{#user.password}, gmtModified = :#{#user.gmtModified} "
            + "where email = :#{#user.email}")
    int updatePasswordByEmail(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByRole(RoleEnum role);

}
