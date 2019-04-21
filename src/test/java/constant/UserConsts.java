package constant;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.RoleEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;

/**
 * @author 覃国强
 * @date 2019-02-27
 */
public interface UserConsts {

    User ORDINARY_USER = User.builder().id(1L).username("lisi").password(
            "1000:13b89cf0374a0342b433c775d5310b7abcba7fd0f5aba6e1:6fca583abe5a954d96ae2867bdf77b9b831c98e53b536e78")
            .email("825580813@qq.com").phoneNumber("15161161253").activation(Boolean.TRUE).role(RoleEnum.ORDINARY)
            .build();

    User CUSTOM_SERVICE_USER = User.builder().id(2L).username("zhangsan").password(
            "1000:a32ce94768d6d96fdcfa07fee9f625f462207a36c805290a:6918aa06b0eddd0a6dd9bb31bb5b3b8c9035d309425aa847")
            .email("3154979786@qq.com").phoneNumber("15161161253").activation(Boolean.TRUE)
            .role(RoleEnum.CUSTOM_SERVICE).build();


    User REPAIRMAN_USER = User.builder().id(3L).username("lisi").password(
            "1000:1fd829d6c5ee026ff53ef0dfd177ec3bdcb3ff8bb1e6cc35:5150ef9a9b8caa350fe8bb1e0f80575263c6c985f581efc3")
            .email("1021367240@qq.com").phoneNumber("15161161253").activation(Boolean.TRUE)
            .role(RoleEnum.REPAIRMAN).build();

}
