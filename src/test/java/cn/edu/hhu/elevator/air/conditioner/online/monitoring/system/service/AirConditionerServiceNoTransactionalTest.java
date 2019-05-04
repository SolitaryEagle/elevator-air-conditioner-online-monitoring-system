package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.BusinessException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.dto.AddressDTO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.dto.AirConditionerDTO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Random;

/**
 * @author 覃国强
 * @date 2019-02-27
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirConditionerServiceNoTransactionalTest {

    private static final Random random = new Random();

    @Autowired
    private AirConditionerService airConditionerService;

    // 向 air_conditioner 表中插入 100 条数据
    @Test
    public void insert() throws IOException, BusinessException {

        // 准备 3 个 user
        User[] users = { User.builder().id(1L).build(), User.builder().id(2L).build(), User.builder().id(3L).build() };

        // 准备一些空调品牌
        String[] brands = { "米家空调", "米家互联网空调", "米家互联网空调（一级能效）", "DAIKIN 大金", "三菱电机",
                "PANASONIC 松下", "三菱重工", "HAIER 海尔", "格力", "美的", "TCL", "格兰仕" };
        String[] models = { "KFR-26GW/F3W1", "KFR-35GW-B1ZM-M3", "KFR-35GW-B1ZM-M1", "KFR-26GW/(26532)FNhAb-A1",
                "KFR-26GW/(26532)FNhCb-A1", "KFR-32GW/(32532)FNhAb-A1", "KFR-32GW/(32532)FNhCb-A1",
                "KFR-35GW/(35532)FNhAb-A1", "KFR-35GW/(35532)FNhCb-A1" };
        String[] sellers = { "小米", "海尔", "格力", "美的" };

        // 准备一些地址
        AddressDTO[] addressDTOs = { new AddressDTO("江苏省", "常州市", "新北区", ""),
                new AddressDTO("四川省", "乐山市", "夹江县", ""),
                new AddressDTO("江西省", "吉安市", "吉州区", ""), new AddressDTO("北京市", "北京城区", "朝阳区", ""),
                new AddressDTO("辽宁省", "沈阳市", "和平区", "") };

        int length = 100;
        for (int i = 0; i < length; i++) {

            int userIndex = random.nextInt(users.length);
            int brandIndex = random.nextInt(brands.length);
            int modelIndex = random.nextInt(models.length);
            int sellerIndex = random.nextInt(sellers.length);
            int addressDTOIndex = random.nextInt(addressDTOs.length);

            AirConditionerDTO value =
                    AirConditionerDTO.builder().brand(brands[brandIndex]).model(models[modelIndex])
                            .seller(sellers[sellerIndex]).addressDTO(addressDTOs[addressDTOIndex])
                            .user(users[userIndex]).build();
            airConditionerService.add(value);
        }
    }

}
