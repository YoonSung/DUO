package tdd.duo.domain.auth;

import org.junit.Before;
import org.junit.Test;
import tdd.duo.domain.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by yoon on 15. 4. 20..
 */
public class AuthenticationTest {

    Authentication authentication;
    User user;

    @Before
    public void setUp() {
        authentication = new Authentication("testId", "testPassword");
        user = new User();
    }


    @Test
    public void checkIsValidMethod() {

        assertTrue(authentication.isValid());

        authentication = new Authentication("", "testPassword");
        assertFalse(authentication.isValid());

        authentication = new Authentication("testId", "");
        assertFalse(authentication.isValid());

        authentication = new Authentication(null, "testPassword");
        assertFalse(authentication.isValid());

        authentication = new Authentication("testId", null);
        assertFalse(authentication.isValid());
    }

    @Test
    public void checkIsMatchIdMethod() {

        assertFalse(authentication.isMathchId(user));

        user.setEmail("testId");
        assertTrue(authentication.isMathchId(user));

        authentication = new Authentication("", "testPassword");
        assertFalse(authentication.isMathchId(user));

        authentication = new Authentication(null, "testPassword");
        assertFalse(authentication.isMathchId(user));
    }
}
