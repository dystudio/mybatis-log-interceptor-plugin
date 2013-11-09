package jhyun.mlih.tests;

import jhyun.mlih.services.FooJdbcService;
import jhyun.mlih.services.FooService;
import jhyun.mlih.spring.MyTestCase;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FooTests extends MyTestCase {

	@Autowired
	private FooJdbcService fooJdbcService;

	@Autowired
	private FooService fooService;

	@Before
	public void prepare() {
		fooJdbcService.createSequence();
		fooJdbcService.createTable();
	}

	@Test
	public void createAndInsertAndCount() {

	}

	// TODO: bind-params?

}
