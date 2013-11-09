package jhyun.mlih.services;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JdbcTransactionExampleService {

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

	public long countAll() {
		return jdbcTemplate.queryForLong("SELECT COUNT(ID) FROM FOO");
	}

	public long sequenceNextVal() {
		return jdbcTemplate.queryForLong("SELECT FOO_ID_SEQ.NEXTVAL FROM DUAL");
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public long insertNew() {
		final long n = sequenceNextVal();
		jdbcTemplate.update("INSERT INTO FOO (ID) VALUES (?)",
				new Object[] { n, });
		return n;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public void insertsNow() throws Exception {
		for (int n = 0; n < 2; n++) {
			insertNew();
			if (n > 0)
				throw new Exception();
		}
	}

}
