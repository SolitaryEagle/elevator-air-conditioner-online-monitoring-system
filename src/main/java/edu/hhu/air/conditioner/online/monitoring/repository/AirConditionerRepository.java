package edu.hhu.air.conditioner.online.monitoring.repository;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.AirConditionerStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.RegionCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.model.entity.AirConditioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019-02-20
 */
public interface AirConditionerRepository extends JpaRepository<AirConditioner, Long>,
        JpaSpecificationExecutor<AirConditioner> {

    void deleteByEquipmentId(String equipmentId);

    List<AirConditioner> findByUserId(Long userId);

    Optional<AirConditioner> findByEquipmentId(String equipmentId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update AirConditioner set brand = :brand, model = :model, seller = :seller, address = :address, "
            + "regionCode = :regionCode, longitude = :longitude,latitude = :latitude, gmtModified = :gmtModified "
            + "where id = :id")
    int update(long id, Timestamp gmtModified, String brand, String model, String seller, String address,
            RegionCodeEnum regionCode, BigDecimal longitude, BigDecimal latitude);

    @Query("update AirConditioner set brand = :#{#airConditioner.brand}, model = :#{#airConditioner.model}, " +
            "seller = :#{#airConditioner.seller}, " +
            "addressString = :#{#airConditioner.addressString}, addressId = :#{#airConditioner.addressId}, " +
            "regionCode = :#{#airConditioner.regionCode}, longitude = :#{#airConditioner.longitude}, " +
            "latitude = :#{#airConditioner.latitude}, gmtModified = :#{#airConditioner.gmtModified} " +
            "where equipmentId = :#{#airConditioner.equipmentId}")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    int updateByEquipmentId(AirConditioner airConditioner);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update AirConditioner set state = :#{#airConditioner.state}, " +
            "faultDescription = :#{#airConditioner.faultDescription}, gmtModified = :#{#airConditioner.gmtModified} " +
            "where id = :#{#airConditioner.id}")
    int updateStateAndFaultDescriptionById(AirConditioner airConditioner);

    long countByRegionCode(RegionCodeEnum regionCode);

    List<AirConditioner> findByUserIdAndState(Long userId, AirConditionerStateEnum state);

}
