package tdd.duo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tdd.duo.domain.User;
import tdd.duo.repository.UserRepository;

/**
 * Created by kws on 15. 3. 31..
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(User user){
        return "/user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(User user){
        userRepository.save(user);
        return "redirect:/user/home";
    }

    @RequestMapping("/home")
    public String homeForm(){
        return "/user/home";
    }

}