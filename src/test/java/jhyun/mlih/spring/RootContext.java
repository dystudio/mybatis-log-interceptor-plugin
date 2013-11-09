package jhyun.mlih.spring;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Import(value = { MybatisContext.class, })
@EnableAsync(mode = AdviceMode.PROXY, proxyTargetClass = true)
@EnableScheduling
@ComponentScan(basePackages = { "jhyun.mlih" })
@Configuration
public class RootContext {

}
