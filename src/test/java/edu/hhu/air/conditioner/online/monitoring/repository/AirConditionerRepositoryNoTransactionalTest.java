package edu.hhu.air.conditioner.online.monitoring.repository;

import edu.hhu.air.conditioner.online.monitoring.model.entity.AirConditioner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 覃国强
 * @date 2019/5/4 11:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AirConditionerRepositoryNoTransactionalTest {

    @Autowired
    private AirConditionerRepository airConditionerRepository;

    @Test
    public void findAllByExample() {
        List<AirConditioner> list = airConditionerRepository.findAll(Example.of(new AirConditioner()));
        System.out.println(list.size());
    }


}
