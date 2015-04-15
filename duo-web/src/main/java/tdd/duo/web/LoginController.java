package tdd.duo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tdd.duo.domain.User;
import tdd.duo.repository.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2015-04-15.
 */
@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(){
        return "/login/loginForm";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, HttpServletResponse response){
        User findUser = userRepository.findByEmail(user.getEmail());
        if ( user.getPassword().equals(findUser.getPassword())) {
            Cookie c = new Cookie("_USER_SESSION_",user.getEmail());
            c.setPath("/");
            response.addCookie(c);
            return "redirect:/";
        }

        return "/login/loginForm";
    }
}
