package edu.hhu.air.conditioner.online.monitoring.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 覃国强
 * @date 2019-03-01
 */
@Getter
@AllArgsConstructor
public enum FaultTypeEnum {

    /**
     * 制冷系统堵：常常发生在毛细管及干燥过滤器处，因为这两个地方是系统中最狭窄的地方，常见的堵塞原因有三种：脏堵、冰堵及焊堵。
     */
    REFRIGERATION_SYSTEM_BLOCKAGE(1, "制冷系统堵"),

    /**
     * 制冷系统漏：空调制冷制热的载体是制冷剂，如系统出现漏点，制冷剂泄漏则空调制冷差或完全不制冷，而空调器出现泄漏的地方主要集中在两器的各焊接头、
     * 毛细管焊接处、压缩机吸排气管、喇叭口、铜钠子裂、连接管等处，要检查时可先进行目测，重点检查连接管各接头处，泄漏处一般都有油迹。
     */
    REFRIGERATION_SYSTEM_LEAKAGE(2, "制冷系统漏"),

    /**
     * 四通阀故障：通常发生在制热时，四通阀吸合不好、串气或卡死，引起制热性能差，在判断时可对四通阀通断电听吸合是否良好，
     * 在维修时可通过反复给四通阀通电或轻轻敲打四通阀使其复位。
     */
    FOUR_WAY_VALUE_FAULT(3, "四通阀故障"),

    /**
     * 单向阀故障：单向阀在制冷时直接导通，但在制热时制冷剂要通过辅助毛细管，当单向阀密封不严或是辅助毛细管堵塞时，
     * 制热在受影响，因此如果空调制冷正常但制热差时，在排除四通阀问题后要重点检查单向阀。
     */
    ONE_WAY_VALUE_FAULT(4, "单向阀故障");

    private int value;
    private String message;

}
