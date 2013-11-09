package jhyun.mlih.spring;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceContext {

	@Bean
	public DataSource dataSource() {
		final BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName("org.h2.Driver");
		bds.setUsername("sa");
		bds.setPassword("");
		bds.setUrl("jdbc:h2:foo;MODE=Oracle");
		bds.setMaxActive(10);
		bds.setMaxIdle(2);
		bds.setPoolPreparedStatements(true);
		return bds;
	}

}
