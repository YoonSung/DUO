package tdd.duo.interceptor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import tdd.duo.config.AppConfig;
import tdd.duo.web.HomeController;
import tdd.duo.web.MvcTestUtil;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Administrator on 2015-04-15.
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class LoginInterceptorTest {

    MockMvc mockMvc;

    @InjectMocks
    HomeController homeController;

    @Before
    public void setUp() {
        mockMvc = MvcTestUtil.getInterceptorMockMvc(homeController);
    }

    @Test
    public void 미_로그인_인터셉터_실패_검증() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void 로그인_인터셉터_검증() throws Exception {

        MockHttpServletResponse response =  mockMvc.perform(get("/")
                .cookie(new Cookie("_USER_SESSION_", "asdf@naver.com")))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andReturn().getResponse();

    }
}
