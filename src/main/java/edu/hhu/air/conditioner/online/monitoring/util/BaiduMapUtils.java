package edu.hhu.air.conditioner.online.monitoring.util;

import edu.hhu.air.conditioner.online.monitoring.exception.AddressContainSpaceException;
import edu.hhu.air.conditioner.online.monitoring.exception.BaiduMapException;
import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.model.EarthCoordinate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

/**
 * @author 覃国强
 * @date 2019-02-20
 */
public final class BaiduMapUtils {

    private final static String AK_KEY = "2NXdR6Sj4i9Zm07prVnAOW4NtCpG8oXt";
    private final static String ADDRESS_RESOLUTION_URL = "http://api.map.baidu.com/geocoder/v2/";

    private BaiduMapUtils() {}

    // 获取根据地址获取地球坐标（经纬度）

    public static EarthCoordinate getEarthCoordinate(String address) throws IOException, BusinessException {

        address = address.trim();
        if (StringUtils.contains(address, " ")) {
            throw new AddressContainSpaceException(ErrorCodeEnum.INVALID, "address", "地址中不能包含空格！");
        }

        String requestUrl = ADDRESS_RESOLUTION_URL + "?address=" + address + "&output=json&src=" +
                "webapp.hhu.elevator-monitoring&ak=" + AK_KEY;
        String jsonStr = loadUrl(requestUrl);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonStr);
        int status = jsonNode.get("status").asInt();
        if (status != 0) {
            throw new BaiduMapException(ErrorCodeEnum.INVALID, "address", "地址无效");
        }

        JsonNode location = jsonNode.get("result").get("location");

        return EarthCoordinate.builder().longitude(location.get("lng").decimalValue())
                .latitude(location.get("lat").decimalValue()).build();
    }

    // 加载url获取json结果

    private static String loadUrl(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        URLConnection connection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder json = new StringBuilder();
        String line;
        while (Objects.nonNull(line = bufferedReader.readLine())) {
            json.append(line);
        }
        return json.toString();
    }

}
