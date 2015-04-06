package tdd.duo.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class UserTest {

    String email = "";
    String name = "";
    int age = 0;

    @org.junit.Before
    public void setData(){
        email = "asdf@naver.com";
        name = "김우승";
        age = 30;
    }

    @Test
    public void checkUserEmail(){
        email = "";
        User testUser = new User(email, name, age);
        assertFalse(testUser.checkUserData());
    }

    @Test
    public void checkUserName(){
        String name = "";
        User testUser = new User(email, name, age);
        assertFalse(testUser.checkUserData());
    }

    @Test
    public void checkUserAge(){
        int age = 0;
        User testUser = new User(email, name, age);
        assertFalse(testUser.checkUserData());
    }

    @Test
    public void 이메일_정합성_검사(){
        String email = "test.co.kr";
        User testUser = new User(email, name, age);
        assertFalse(testUser.checkUserEmail());
    }


}
