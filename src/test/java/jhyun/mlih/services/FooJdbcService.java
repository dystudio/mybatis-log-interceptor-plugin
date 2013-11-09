package jhyun.mlih.services;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FooJdbcService {

	@Autowired
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void init() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void createTable() {
		jdbcTemplate
				.execute("CREATE TABLE IF NOT EXISTS FOO (ID INT PRIMARY KEY)");
	}

	public void truncateTable() {
		jdbcTemplate.execute("TRUNCATE TABLE FOO");
	}

	public void createSequence() {
		jdbcTemplate.execute("CREATE SEQUENCE IF NOT EXISTS FOO_ID_SEQ");
	}

}
