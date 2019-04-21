package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 覃国强
 * @date 2019-02-22
 */
public enum RegionCodeEnum {

    BEI_JING("11", "北京市"),
    TIAN_JIN("12", "天津市"),
    HE_BEI("13", "河北省"),
    SHAN_XI("14", "山西省"),
    NEI_MENG_GU("15", "内蒙古自治区"),
    LIAO_NING("21", "辽宁"),
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

    RegionCodeEnum(String code, String region) {
        this.code = code;
        this.region = region;
    }

    public String getCode() {
        return code;
    }

    public String getRegion() {
        return region;
    }

    // 根据地址获取区域编码

    public static RegionCodeEnum parse(String address) {

        if (StringUtils.startsWith(address, RegionCodeEnum.BEI_JING.region)) {
            return RegionCodeEnum.BEI_JING;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.TIAN_JIN.region)) {
            return RegionCodeEnum.TIAN_JIN;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.HE_BEI.region)) {
            return RegionCodeEnum.HE_BEI;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.SHAN_XI.region)) {
            return RegionCodeEnum.SHAN_XI;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.NEI_MENG_GU.region)) {
            return RegionCodeEnum.NEI_MENG_GU;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.LIAO_NING.region)) {
            return RegionCodeEnum.LIAO_NING;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.JI_LIN.region)) {
            return RegionCodeEnum.JI_LIN;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.HEI_LONG_JIANG.region)) {
            return RegionCodeEnum.HEI_LONG_JIANG;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.SHANG_HAI.region)) {
            return RegionCodeEnum.SHANG_HAI;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.JIANG_SU.region)) {
            return RegionCodeEnum.JIANG_SU;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.ZHE_JIANG.region)) {
            return RegionCodeEnum.ZHE_JIANG;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.AN_HUI.region)) {
            return RegionCodeEnum.AN_HUI;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.FU_JIAN.region)) {
            return RegionCodeEnum.FU_JIAN;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.JIANG_XI.region)) {
            return RegionCodeEnum.JIANG_XI;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.SHAN_DONG.region)) {
            return RegionCodeEnum.SHAN_DONG;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.HE_NAN.region)) {
            return RegionCodeEnum.HE_NAN;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.HU_BEI.region)) {
            return RegionCodeEnum.HU_BEI;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.HU_NAN.region)) {
            return RegionCodeEnum.HU_NAN;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.GUANG_DONG.region)) {
            return RegionCodeEnum.GUANG_DONG;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.GUANG_XI.region)) {
            return RegionCodeEnum.GUANG_XI;
        }

        if (StringUtils.startsWith(address, RegionCodeEnum.HAI_NAN.region)) {
            return RegionCodeEnum.HAI_NAN;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.CHONG_QING.region)) {
            return RegionCodeEnum.CHONG_QING;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.SI_CHUAN.region)) {
            return RegionCodeEnum.SI_CHUAN;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.GUI_ZHOU.region)) {
            return RegionCodeEnum.GUI_ZHOU;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.YUN_NAN.region)) {
            return RegionCodeEnum.YUN_NAN;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.XI_ZANG.region)) {
            return RegionCodeEnum.XI_ZANG;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.SHAAN_XI.region)) {
            return RegionCodeEnum.SHAAN_XI;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.GAN_SU.region)) {
            return RegionCodeEnum.GAN_SU;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.QING_HAI.region)) {
            return RegionCodeEnum.QING_HAI;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.NING_XIA.region)) {
            return RegionCodeEnum.NING_XIA;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.XIN_JIANG.region)) {
            return RegionCodeEnum.XIN_JIANG;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.TAI_WAN.region)) {
            return RegionCodeEnum.TAI_WAN;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.XIANG_GANG.region)) {
            return RegionCodeEnum.XIANG_GANG;
        }
        if (StringUtils.startsWith(address, RegionCodeEnum.AO_MEN.region)) {
            return RegionCodeEnum.AO_MEN;
        }
        return null;
    }

}
