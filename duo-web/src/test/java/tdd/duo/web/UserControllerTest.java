package tdd.duo.web;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tdd.duo.config.WebConfig;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by kws on 15. 3. 31..
 */

public class UserControllerTest {
    MockMvc mockMvc;

    @Before
    public void setUp() {
        MvcTestUtil.getMockMvc(new UserController());
    }

    @Test
    public void userRegisterForm() throws Exception {
        mockMvc.perform(get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/register"))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + "register" + WebConfig.RESOLVER_SUFFIX));
    }

    @Test
    public void userRegister() throws Exception {
        mockMvc.perform(post("/user/register").param("name","김우승").param("age","31"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/home"));
    }


}
