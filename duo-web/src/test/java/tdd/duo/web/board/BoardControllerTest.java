package tdd.duo.web.board;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import tdd.duo.config.WebConfig;
import tdd.duo.web.MvcTestUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * Created by yoon on 15. 4. 14..
 */
public class BoardControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MvcTestUtil.getMockMvc(new BoardController());
    }


    @Test
    public void listViewRequest() throws Exception {

        String expectedUrl = "/board/list";

        mockMvc.perform(get("/board"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedUrl))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + expectedUrl + WebConfig.RESOLVER_SUFFIX));
    }
}
