package idv.hsiehpinghan.stockdao.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration("stockDaoSpringConfiguration")
@ComponentScan(basePackages = { "idv.hsiehpinghan.stockdao.repository" })
public class SpringConfiguration {
}
