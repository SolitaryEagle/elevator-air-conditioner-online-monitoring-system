package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.AirConditionerStateEnum;
import edu.hhu.air.conditioner.online.monitoring.model.entity.AirConditioner;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.io.IOException;
import java.util.List;

/**
 * @author 覃国强
 * @date 2019-02-20
 */
public interface AirConditionerService {

    /**
     * 添加 AirConditioner 记录。参数 airConditioner 中需要的字段：
     * brand：品牌
     * model：型号
     * seller：销售方
     * addressString：地址的普通形式
     * addressId：地址主键
     * userId: 所属者主键
     *
     * @param airConditioner 聚合 airConditioner 字段的参数
     * @return 保存成功后的 airConditioner 对象
     * @throws IOException 获取地址坐标时可能会抛出此异常
     */
    AirConditioner add(AirConditioner airConditioner) throws IOException;

    void deleteByEquipmentId(String equipmentId);

    AirConditioner updateByEquipmentId(AirConditioner airConditioner) throws IOException;

    int updateStateAndFaultDescriptionById(AirConditioner airConditioner);

    long count();

    AirConditioner getById(Long id);

    List<AirConditioner> listAll();

    Page<AirConditioner> listAll(int page, int size);

    List<AirConditioner> listAll(Specification<AirConditioner> specification);

    Page<AirConditioner> listAll(Specification<AirConditioner> specification, int page, int size);

    List<AirConditioner> listAllByUserId(Long userId);

    Page<AirConditioner> listAllByUserId(Long userId, int page, int size);

    AirConditioner getByEquipmentId(String equipmentId);

    List<AirConditioner> listByUserIdAndState(Long userId, AirConditionerStateEnum state);






/*
    int update(AirConditionerVO airConditionerVO) throws IOException;

    List<AirConditionerVO> listAirConditioners(AirConditioner example);

    List<AirConditionerVO> listAll();
*/
}
