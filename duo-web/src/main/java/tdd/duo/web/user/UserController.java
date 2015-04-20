package tdd.duo.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(User user){
        return "/user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(User user, Model model){

        logger.debug("paramUser : {}", user);

        User selectedUser = userRepository.findByEmail(user.getEmail());

        logger.debug("selectedUser : {}", selectedUser);

        //exists user
        if (selectedUser != null && selectedUser.getEmail().equals(user.getEmail())) {
            model.addAttribute("error", "user already exists");

        } else {
            userRepository.save(user);
            return "redirect:/user/home";
        }

        return "/user/register";
    }

}