package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.repository;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.AirConditionerStateEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.RegionCodeEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.AirConditioner;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 覃国强
 * @date 2019-02-20
 */
public interface AirConditionerRepository extends JpaRepository<AirConditioner, Long> {

    List<AirConditioner> findByUserId(Long userId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update AirConditioner set brand = :brand, model = :model, seller = :seller, address = :address, "
            + "regionCode = :regionCode, longitude = :longitude,latitude = :latitude, gmtModified = :gmtModified "
            + "where id = :id")
    int update(long id, Timestamp gmtModified, String brand, String model, String seller, String address,
            RegionCodeEnum regionCode, BigDecimal longitude, BigDecimal latitude);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update AirConditioner set state = :state, faultDescription = :faultDescription where id = :id")
    int updateFaultInfo(Long id, AirConditionerStateEnum state, String faultDescription);

    long countByRegionCode(RegionCodeEnum regionCode);

}
