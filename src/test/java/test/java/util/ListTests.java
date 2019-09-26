package test.java.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 覃国强
 * @date 2019-09-12 20:17
 */
public class ListTests {

    @Test
    public void testList2String() {
        List<String> list = new ArrayList<>();
        list.add("zhangsan");
        list.add("lisi");
        list.add("wangwu");
        list.add("zhangsan");
        String result = "" + list;
        System.out.println(result);
    }

    @Test
    public void testMap2String() {
        Map<String, String> map = new HashMap<>(3);
        map.put("111", "aaa");
        map.put("222", "bbb");
        map.put("333", "ccc");
        String result = "" + map;
        System.out.println(result);
    }

}
