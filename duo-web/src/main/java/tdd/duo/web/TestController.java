package tdd.duo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yoon on 15. 3. 25..
 */
@Controller
public class TestController {

    @RequestMapping("/")
    public @ResponseBody String home() {
        return "Server Started";
    }
}