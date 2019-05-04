package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;

/**
 * @author 覃国强
 * @date 2019-02-20
 */
public class BaiduMapUtilsTest {

    /*
    http://api.map.baidu.com/marker/v2/?location=118.72885419830244,32
    .146282724732714&title=我的位置&content=江苏省南京市浦口区大桥北路58号印象汇一层01-02-03号商铺&output=html&src=webapp.hhu.elevatorMonitoringSystem&ak=Xnydmr1oWTenkGrNxvNk8WkXm6jOLcUF

v2: {"lng":118.77807440802562,"lat":32.05723550180587}
v1: {"lng":118.778074,        "lat":32.057236}
http://api.map.baidu.com/geocoder?location=32.05723550180587,118.77807440802562&output=html&src=webapp.baidu.openAPIdemo


    @Test
    public void getLongitudeAndLatitude() throws IOException {

        String address = "河海大学常州校区";
        String address2 = "北京市";
        String address3 = "中山公园";
        BigDecimal[] longitudeAndLatitude = BaiduMapUtils.getLongitudeAndLatitude(address);
        System.out.println(Arrays.toString(longitudeAndLatitude));
    }

     */
}
