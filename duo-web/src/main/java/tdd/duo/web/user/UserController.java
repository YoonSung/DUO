package tdd.duo.web.user;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tdd.duo.domain.User;
import tdd.duo.domain.auth.Authentication;
import tdd.duo.exception.PasswordMismatchException;
import tdd.duo.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * Created by kws on 15. 3. 31..
 */
@RequestMapping("/user")
@Controller
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(User user){
        return "/user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(User user, Model model){

        logger.debug("paramUser : {}", user);

        User selectedUser = userService.findByEmail(user.getEmail());

        logger.debug("selectedUser : {}", selectedUser);

        //exists user
        if (selectedUser != null && selectedUser.getEmail().equals(user.getEmail())) {
            model.addAttribute("error", "user already exists");

        } else {
            userService.save(user);
            return "redirect:/user/home";
        }

        return "/user/register";
    }

    @RequestMapping(value = "/login")
    public String loginForm() {
        return "/user/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Authentication authentication, HttpSession session, Model model) {

        try {
            userService.login(authentication);
            session.setAttribute("id", authentication.getId());
            System.out.println("test : "+session.getAttribute("id"));
            return "redirect:/";

        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", "아이디를 다시 확인해주세요");
            logger.error("Login Request : {}", e.getMessage());

        } catch (PasswordMismatchException e) {
            model.addAttribute("errorMessage", "비밀번호를 다시 확인해주세요");
            logger.error("Login Request : PasswordMismatchException");

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "잘못된 접근입니다");
            logger.error("Login Request : {}", authentication.toString());
        }

        return "/user/login";
    }

}