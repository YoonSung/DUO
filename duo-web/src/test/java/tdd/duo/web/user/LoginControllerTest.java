package tdd.duo.web.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import tdd.duo.config.AppConfig;
import tdd.duo.domain.User;
import tdd.duo.repository.UserRepository;
import tdd.duo.web.MvcTestUtil;
import tdd.duo.web.user.LoginController;

import javax.servlet.http.Cookie;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Administrator on 2015-04-15.
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class LoginControllerTest {

    MockMvc mockMvc;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    LoginController loginController;

    private String testEmail;
    private String testPassword;
    private String testName;
    private int testAge;
    private User testUser;

    @Before
    public void setUp(){
        mockMvc = MvcTestUtil.getMockMvc(loginController);
        testEmail = "test@gmail.com";
        testPassword = "asdf";
        testName = "김우승";
        testAge = 31;

        testUser = new User(testEmail, testPassword, testName, testAge);
    }

    @Test
    public void 로그인_폼_요청() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("/login/loginForm"));
    }

    @Test
    public void 로그인_실패_요청() throws Exception{
        testPassword = "asdfasdf";
        when(userRepository.findByEmail(testEmail)).thenReturn(testUser);

        mockMvc.perform(makePostForLogin())
                .andExpect(status().isOk())
                .andExpect(view().name("/login/loginForm"));
    }

    @Test
    public void 로그인_성공_요청() throws Exception{
        when(userRepository.findByEmail(testEmail)).thenReturn(testUser);

        mockMvc.perform(makePostForLogin())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void 쿠키_생성_확인() throws Exception{
        when(userRepository.findByEmail(testEmail)).thenReturn(testUser);

        MockHttpServletResponse response = mockMvc.perform(makePostForLogin())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andReturn().getResponse();

        Cookie cookie = response.getCookie("_USER_SESSION_");

        assertNotNull(cookie);
        assertTrue(cookie.getValue().equals(testUser.getEmail()));
    }

    private MockHttpServletRequestBuilder makePostForLogin() {
        return post("/login")
                .param("email", testEmail)
                .param("password", testPassword);
    }
}
