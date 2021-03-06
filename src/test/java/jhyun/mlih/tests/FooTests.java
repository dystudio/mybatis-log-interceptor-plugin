package jhyun.mlih.tests;

import java.util.Arrays;

import jhyun.mlih.entities.Foo;
import jhyun.mlih.junit.MyTestCase;
import jhyun.mlih.services.FooJdbcService;
import jhyun.mlih.services.FooService;

import org.junit.Assert;
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
		final long before = fooService.count();
		fooService.insert(new Foo(fooService.newFooId()));
		final long after = fooService.count();
		Assert.assertTrue(before < after);
	}

	@Test
	public void selectMany() {
		fooService.selectMany(Arrays.asList(1, 2, 3));
	}

}
