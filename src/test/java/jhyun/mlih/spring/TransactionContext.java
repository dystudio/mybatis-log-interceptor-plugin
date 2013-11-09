package jhyun.mlih.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Import(value = { DataSourceContext.class })
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
@Configuration
public class TransactionContext implements TransactionManagementConfigurer {

	@Autowired
	private DataSource dataSource;

	@Bean(name = "txManager")
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(
				dataSource);
		return txManager;
	}

}
