package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.AirConditionerStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultTypeEnum;
import edu.hhu.air.conditioner.online.monitoring.model.entity.AirConditioner;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Fault;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

/**
 * @author 覃国强
 * @date 2019/5/14 21:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FaultServiceNoTransactionalTest {

    @Autowired
    private FaultService faultService;

    @Autowired
    private UserService userService;
    @Autowired
    private AirConditionerService airConditionerService;

    private static final Random RANDOM = new Random();

    @Test
    public void insert() {

        String[] descs = { "空调信号（控制）变压器损坏后会引起整机不工作。", "夏季空调电路上压敏电阻击穿损坏，多为雷击所致。",
                "冷凝器、蒸发器外部脏堵，将影响制冷效果。", "压缩机启动电容的损坏判断与排除。",
                "空调电源线（与220V或380V接线）容量不足，引起主机不工作。", "空调三相供电电源相序错误引起系统不工作。" };

        User[] users = { userService.getById(1L), userService.getById(2L) };

        int length = 16;

        for (int i = 0; i < length; i++) {

            FaultTypeEnum type = FaultTypeEnum.random();
            String desc = descs[RANDOM.nextInt(descs.length)];
            User reportUser = users[RANDOM.nextInt(users.length)];
            String realName = reportUser.getUsername();
            String phoneNumber = reportUser.getPhoneNumber();
            String address = "";
            long airConditionerId = 0L;
            List<AirConditioner> airConditioners =
                    airConditionerService.listByUserIdAndState(reportUser.getId(), AirConditionerStateEnum.GOOD);
            if (airConditioners.size() > 0) {
                address = airConditioners.get(0).getAddressString();
                airConditionerId = airConditioners.get(0).getId();
            }

            Fault fault = Fault.builder().type(type).description(desc).realName(realName).contactAddress(address)
                    .phoneNumber(phoneNumber).airConditionerId(airConditionerId).reportUserId(reportUser.getId())
                    .build();

            faultService.add(fault);
        }

    }

}
