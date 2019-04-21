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
 * @date 2019-02-15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
// @Transactional
public class UserControllerTest {

    private static final String BASE_URL = "/v1/monitoring-system/users";
    private static final String USER_JSON =
            "{'username':'zhangsan', 'name':'12345678', 'email':'825580813@qq.com', " + "'phoneNumber':'15161161253'}";

    private static final MultiValueMap<String, String> PARAMS = new LinkedMultiValueMap<>();
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    {
        PARAMS.set("username", "wangwu");
        PARAMS.set("password", "12345678");
        PARAMS.set("email", "1021367240@qq.com");
        PARAMS.set("phoneNumber", "17314878230");
        PARAMS.set("repassword", "12345678");
    }

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void register() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL).params(PARAMS).contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(USER_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void sendActivationCode() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/activation").accept(MediaType.APPLICATION_JSON_UTF8)
                .param("receiverEmail", "825580813@qq.com").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void validateActivationCode() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConsts.VERIFICATION_CODE_KEY, "550191");
        session.setAttribute(SessionConsts.LOGIN_USER_KEY, UserConsts.ORDINARY_USER);

        mockMvc.perform(
                MockMvcRequestBuilders.put(BASE_URL + "/activation").contentType(MediaType.APPLICATION_JSON_UTF8)
                        .param("verificationCode", "550191").session(session).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void login() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/login").param("usernameOrEmail", "lisi")
                .param("password", "12345678").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        System.out.println("=======================================================");

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/login").param("usernameOrEmail", "3154979786@qq.com")
                .param("password", "12345678").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void retrievePassword() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConsts.VERIFICATION_CODE_KEY, "550191");

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/password").accept(MediaType.APPLICATION_JSON_UTF8)
                .param("email", "3154979786@qq.com").param("newPassword", "12345678910")
                .param("reNewPassword", "12345678910").param("verificationCode", "550191")
                .contentType(MediaType.APPLICATION_JSON_UTF8).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void changePassword() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConsts.LOGIN_USER_KEY, UserConsts.ORDINARY_USER);
        session.setAttribute(SessionConsts.VERIFICATION_CODE_KEY, "550191");

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/password").accept(MediaType.APPLICATION_JSON_UTF8)
                .param("oldPassword", "12345678").param("newPassword", "12345678").param("reNewPassword", "12345678")
                .contentType(MediaType.APPLICATION_JSON_UTF8).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUser() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConsts.LOGIN_USER_KEY, UserConsts.ORDINARY_USER);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL).accept(MediaType.APPLICATION_JSON_UTF8).params(PARAMS)
                .contentType(MediaType.APPLICATION_JSON_UTF8).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

}
