package jhyun.mlih.tests;

import jhyun.mlih.spring.RootContext;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 부모 단위테스트.
 * 
 * 단위테스트 작성할 때 이 클래스를 상속해 작성해주세요.
 * 
 * @author jhyun
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootContext.class, })
public class MyTestCase {

	protected Logger logger = LoggerFactory.getLogger(getClass());

}
