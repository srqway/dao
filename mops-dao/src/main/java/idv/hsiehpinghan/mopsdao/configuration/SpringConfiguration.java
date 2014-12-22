package idv.hsiehpinghan.mopsdao.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration("mopsDaoSpringConfiguration")
@ComponentScan(basePackages = { "idv.hsiehpinghan.mopsdao.repository" })
public class SpringConfiguration {
}
