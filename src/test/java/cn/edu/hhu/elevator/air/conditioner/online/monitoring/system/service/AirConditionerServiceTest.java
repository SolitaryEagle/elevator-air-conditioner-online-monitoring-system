package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.RegionCodeEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.vo.AirConditionerVO;

import java.io.IOException;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 覃国强
 * @date 2019-02-27
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirConditionerServiceTest {

    private static final Random random = new Random();

    @Autowired
    private AirConditionerService airConditionerService;

    // 向 air_conditioner 表中插入 100 条数据
    @Test
    public void insert() throws IOException {

        // 准备 3 个 user
        User[] users = {User.builder().id(1L).build(), User.builder().id(2L).build(), User.builder().id(3L).build()};

        // 准备一些空调品牌
        String[] brands = {"米家空调", "米家互联网空调", "米家互联网空调（一级能效）", "DAIKIN 大金", "三菱电机",
                "PANASONIC 松下", "三菱重工", "HAIER 海尔", "格力", "美的", "TCL", "格兰仕"};
        String[] models = {"KFR-26GW/F3W1", "KFR-35GW-B1ZM-M3", "KFR-35GW-B1ZM-M1", "KFR-26GW/(26532)FNhAb-A1",
                "KFR-26GW/(26532)FNhCb-A1", "KFR-32GW/(32532)FNhAb-A1", "KFR-32GW/(32532)FNhCb-A1",
                "KFR-35GW/(35532)FNhAb-A1", "KFR-35GW/(35532)FNhCb-A1"};
        String[] sellers = {"小米", "海尔", "格力", "美的"};
        RegionCodeEnum[] regions = RegionCodeEnum.values();
        int length = 10;
        for (int i = 0; i < length; i++) {

            int userIndex = random.nextInt(users.length);
            int brandIndex = random.nextInt(brands.length);
            int modelIndex = random.nextInt(models.length);
            int sellerIndex = random.nextInt(sellers.length);
            int regionIndex = random.nextInt(regions.length);

            AirConditionerVO airConditionerVO =
                    AirConditionerVO.builder().brand(brands[brandIndex]).model(models[modelIndex])
                            .seller(sellers[sellerIndex]).address(regions[regionIndex].getRegion())
                            .user(users[userIndex]).build();
            airConditionerService.save(airConditionerVO);
        }
    }

}
