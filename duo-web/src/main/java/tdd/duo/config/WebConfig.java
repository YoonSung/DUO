package tdd.duo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by yoon on 15. 3. 25..
 */
@Configuration
@ComponentScan(basePackages = {"tdd.duo.web"})
public class WebConfig {

    public static final String RESOLVER_PREFIX = "/WEB-INF/view";
    public static final String RESOLVER_SUFFIX = ".jsp";

    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix(RESOLVER_PREFIX);
        resolver.setSuffix(RESOLVER_SUFFIX);

        return resolver;
    }
}