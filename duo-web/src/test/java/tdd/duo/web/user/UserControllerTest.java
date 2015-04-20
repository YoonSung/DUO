package tdd.duo.web.user;

import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tdd.duo.config.AppConfig;
import tdd.duo.config.WebConfig;
import tdd.duo.domain.User;
import tdd.duo.domain.auth.Authentication;
import tdd.duo.exception.PasswordMismatchException;
import tdd.duo.service.UserService;
import tdd.duo.web.MvcTestUtil;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class UserControllerTest {

    MockMvc mockMvc;

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Before
    public void setUp() {
        mockMvc = MvcTestUtil.getMockMvc(userController);
    }

    @Test
    public void userRegisterForm() throws Exception {
        mockMvc.perform(get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/register"))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + "/user/register" + WebConfig.RESOLVER_SUFFIX));
    }

    @Test
    public void userRegister() throws Exception {
        mockMvc.perform(post("/user/register")
                .param("email", "test@gmail.com")
                .param("name", "김우승")
                .param("age", "31"))

                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/home"));
    }

    @Test
    public void userRegisterWithAlreadyExistion() throws Exception {

        String testEmail = "test@gmail.com";
        String testPassword = "asdf";
        String testName = "김우승";
        int testAge = 31;

        User testUser = new User(testEmail, testPassword, testName, testAge);

        when(userService.findByEmail(testEmail)).thenReturn(testUser);


        mockMvc.perform(post("/user/register")
                .param("email", testEmail)
                .param("name", testName)
                .param("age", "" + testAge))

                .andExpect(status().isOk())
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + "/user/register" + WebConfig.RESOLVER_SUFFIX))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    public void userRegisterWithInvalidParameter() throws Exception {
        String testEmail = "";
        String testPassword = "asdf";
        String testName = "김우승";
        int testAge = 31;

        User testUser = new User(testEmail, testPassword, testName, testAge);

        when(userService.findByEmail(testEmail)).thenReturn(testUser);


        mockMvc.perform(post("/user/register")
                .param("email", testEmail)
                .param("name", testName)
                .param("age", "" + testAge))

                .andExpect(status().isOk())
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + "/user/register" + WebConfig.RESOLVER_SUFFIX))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("errorMessage"));
    }


    // ------ Login Test

    @Test
    public void userLoginForm() throws Exception {

        String expectedUrl = "/user/login";

        mockMvc.perform(get("/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedUrl))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + expectedUrl + WebConfig.RESOLVER_SUFFIX));
    }

    @Test
    public void userLogin() throws Exception {

        String email = "testEmail@test.com";
        String password = "testPassword";

        mockMvc.perform(post("/user/login")
                        .param("email", email)
                        .param("password", password)
        )
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));

        //assertEquals(email, result.getRequest().getSession().getAttribute("id"));
    }

    @Test
    public void userLoginRequestWithInvalidParameter() throws Exception {
        Mockito.doThrow(IllegalArgumentException.class).when(userService).login(any(Authentication.class));

        mockMvc.perform(post("/user/login")
                        .param("email", "testEmail@test.com")
                        .param("password", "testPassword")
        )
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX +"/user/login" + WebConfig.RESOLVER_SUFFIX))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "잘못된 접근입니다"));
    }

    @Test
    public void userLoginRequestWithNotExistId() throws Exception {
        Mockito.doThrow(NotFoundException.class).when(userService).login(any(Authentication.class));

        mockMvc.perform(post("/user/login")
                        .param("email", "testEmail@test.com")
                        .param("password", "testPassword")
        )
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX +"/user/login" + WebConfig.RESOLVER_SUFFIX))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "아이디를 다시 확인해주세요"));
    }

    @Test
    public void userLoginRequestWithWrongPassword() throws Exception {
        Mockito.doThrow(PasswordMismatchException.class).when(userService).login(any(Authentication.class));

        mockMvc.perform(post("/user/login")
                        .param("email", "testEmail@test.com")
                        .param("password", "testPassword")
        )
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX +"/user/login" + WebConfig.RESOLVER_SUFFIX))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "비밀번호를 다시 확인해주세요"));
    }
}
