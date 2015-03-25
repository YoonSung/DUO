package tdd.duo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tdd.duo.domain.User;
import tdd.duo.repository.UserRepository;

/**
 * Created by yoon on 15. 3. 25..
 */
@Controller
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public @ResponseBody String home() {

        User user = new User("testUser", 28);
        userRepository.save(user);

        return userRepository.findOne((long) 1).toString();
    }
}
