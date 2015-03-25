package tdd.duo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by yoon on 15. 3. 25..
 */
@Configuration
@ComponentScan(basePackages = {"tdd.duo.web"})
public class WebConfig {

}