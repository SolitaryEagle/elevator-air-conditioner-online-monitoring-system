package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.repository;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;
import java.sql.Timestamp;
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
    @Query("update User set activation = :activation, gmtModified = :gmtModified where id = :id")
    int updateActivationById(Long id, boolean activation, Timestamp gmtModified);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update User set password = :password, gmtModified = :gmtModified where email = :email")
    int updatePasswordByEmail(String email, String password, Timestamp gmtModified);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update User set username = :username, phoneNumber = :phoneNumber, gmtModified = :gmtModified where id = :id")
    int updateById(Long id, String username, String phoneNumber, Timestamp gmtModified);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}
