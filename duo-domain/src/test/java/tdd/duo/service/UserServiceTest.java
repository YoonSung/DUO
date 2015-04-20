package tdd.duo.service;

import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import tdd.duo.config.DBConfig;
import tdd.duo.domain.User;
import tdd.duo.domain.auth.Authentication;
import tdd.duo.exception.PasswordMismatchException;
import tdd.duo.repository.UserRepository;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by yoon on 15. 4. 20..
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {DBConfig.class})
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void loginWithValidParameter() {

        String id = "testId";
        String password = "testPassword";

        User user = new User();
        user.setEmail(id);
        user.setPassword(password);

        when(userRepository.findByEmail(id)).thenReturn(user);

        Authentication authentication = new Authentication(id, password);

        try {
            userService.login(authentication);
        } catch (Exception e) {
            fail("unexpected failure");
        }

    }


    //RuntimeException은 expected로 처리되지 않는다.
    @Test
    public void loginWithEmptyAuthentication() {
        Authentication authentication = new Authentication("", "password");

        try {
            userService.login(authentication);
            fail("unexpected failure");
        } catch (IllegalArgumentException e) {
            System.out.println("must here");
        } catch (Exception e) {
            fail("unexpected failure");
        }

        authentication = new Authentication(null, "password");

        try {
            userService.login(authentication);
            fail("unexpected failure");
        } catch (IllegalArgumentException e) {
            System.out.println("must here");
        } catch (Exception e) {
            fail("unexpected failure");
        }
    }

    @Test(expected = PasswordMismatchException.class)
    public void loginWithMismatchPassword() throws NotFoundException, PasswordMismatchException {

        String id = "testEmail";

        Authentication authentication = new Authentication(id, "testPassword");
        User user = new User();
        user.setEmail(id);
        user.setPassword("testUserPassoword");

        when(userRepository.findByEmail(authentication.getId())).thenReturn(user);

        userService.login(authentication);
    }

    @Test(expected = NotFoundException.class)
    public void loginWithNotExistId() throws NotFoundException, PasswordMismatchException {
        Authentication authentication = new Authentication("testEmail", "testPassword");
        when(userRepository.findByEmail(authentication.getId())).thenReturn(null);

        userService.login(authentication);
    }
}
