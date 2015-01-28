package idv.hsiehpinghan.twsedao.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration("twseDaoSpringConfiguration")
@ComponentScan(basePackages = { "idv.hsiehpinghan.twsedao.repository" })
public class SpringConfiguration {
}
