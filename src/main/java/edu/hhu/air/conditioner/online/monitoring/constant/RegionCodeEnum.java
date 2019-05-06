package edu.hhu.air.conditioner.online.monitoring.constant;

import edu.hhu.air.conditioner.online.monitoring.exception.RegionCodeNonExistException;
import edu.hhu.air.conditioner.online.monitoring.exception.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019-02-22
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum RegionCodeEnum {

    BEI_JING("11", "北京市"),
    TIAN_JIN("12", "天津市"),
    HE_BEI("13", "河北省"),
    SHAN_XI("14", "山西省"),
    NEI_MENG_GU("15", "内蒙古自治区"),
    LIAO_NING("21", "辽宁省"),
    JI_LIN("22", "吉林省"),
    HEI_LONG_JIANG("23", "黑龙江省"),
    SHANG_HAI("31", "上海市"),
    JIANG_SU("32", "江苏省"),
    ZHE_JIANG("33", "浙江省"),
    AN_HUI("34", "安徽省"),
    FU_JIAN("35", "福建省"),
    JIANG_XI("36", "江西省"),
    SHAN_DONG("37", "山东省"),
    HE_NAN("41", "河南省"),
    HU_BEI("42", "湖北省"),
    HU_NAN("43", "湖南省"),
    GUANG_DONG("44", "广东省"),
    GUANG_XI("45", "广西壮族自治区"),
    HAI_NAN("46", "海南省"),
    CHONG_QING("50", "重庆市"),
    SI_CHUAN("51", "四川省"),
    GUI_ZHOU("52", "贵州省"),
    YUN_NAN("53", "云南省"),
    XI_ZANG("54", "西藏自治区"),
    SHAAN_XI("61", "陕西省"),
    GAN_SU("62", "甘肃省"),
    QING_HAI("63", "青海省"),
    NING_XIA("64", "宁夏回族自治区"),
    XIN_JIANG("65", "新疆维吾尔自治区"),
    TAI_WAN("71", "台湾省"),
    XIANG_GANG("81", "香港特别行政区"),
    AO_MEN("91", "澳门特别行政区");

    private String code;
    private String region;

    // 根据地址获取区域编码

    public static RegionCodeEnum parse(String address) throws RegionCodeNonExistException {
        RegionCodeEnum[] regionCodeEnums = values();
        Optional<RegionCodeEnum> optional = Arrays.stream(regionCodeEnums).filter(
                value -> StringUtils.startsWith(address, value.region)).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        } else {
            log.error("区域编码不存在！address ：{}", address);
            throw new RegionCodeNonExistException(ResponseCode.MISSING, "regionCode", "区域编码不存在！");
        }
    }

    public static RegionCodeEnum valueOfProvince(String province) throws RegionCodeNonExistException {
        Optional<RegionCodeEnum> optional = Arrays.stream(values()).filter(
                value -> StringUtils.equals(province, value.region)).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        } else {
            log.error("区域编码不存在！province ：{}", province);
            throw new RegionCodeNonExistException(ResponseCode.MISSING, "regionCode", "区域编码不存在！");
        }
    }

}
