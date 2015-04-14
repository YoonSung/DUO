package tdd.duo.web.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yoon on 15. 4. 14..
 */
@Controller
@RequestMapping("/board")
public class BoardController {

    @RequestMapping("")
    public String list() {
        return "/board/list";
    }

}
