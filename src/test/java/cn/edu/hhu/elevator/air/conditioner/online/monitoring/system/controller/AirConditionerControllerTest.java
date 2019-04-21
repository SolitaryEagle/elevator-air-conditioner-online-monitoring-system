package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.controller;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.SessionConsts;
import constant.UserConsts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author 覃国强
 * @date 2019-02-21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AirConditionerControllerTest {

    private static final String BASE_URL = "/v1/monitoring-system/air-conditioners";

    private static final MultiValueMap<String, String> PARAMS = new LinkedMultiValueMap<>();

    {
        PARAMS.set("brand", "米家互联网空调");
        PARAMS.set("model", "KFR-35GW-B1ZM-M3");
        PARAMS.set("seller", "小米商城");
        PARAMS.set("address", "江苏省南京市浦口区大桥北路58号印象汇一层01-02-03号商铺");
        PARAMS.set("state", "GOOD");
        PARAMS.set("faultDescription", "无");
    }

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void entryInfo() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConsts.LOGIN_USER_KEY, UserConsts.ORDINARY_USER);

        mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL).params(PARAMS).contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void listAirConditioners() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConsts.LOGIN_USER_KEY, UserConsts.ORDINARY_USER);
        // session.setAttribute(SessionConsts.LOGIN_USER_KEY, UserConsts.CUSTOM_SERVICE_USER);

        mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_URL).param("address", "北京").contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

    }

}
