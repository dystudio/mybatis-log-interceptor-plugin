package jhyun.mlih.mybatis;

import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.JexlException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 원 출처: {@linkplain http://zgundam.tistory.com/34}
 * 
 * @author jonghyouk yun
 * 
 */
@Intercepts({
		@Signature(type = StatementHandler.class, method = "update", args = { Statement.class }),
		@Signature(type = StatementHandler.class, method = "query", args = {
				Statement.class, ResultHandler.class }) })
public class MybatisLogInterceptor implements Interceptor {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private JexlEngine engine = new JexlEngine();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler handler = (StatementHandler) invocation.getTarget();
		BoundSql boundSql = handler.getBoundSql();
		// 쿼리문을 가져온다(이 상태에서의 쿼리는 값이 들어갈 부분에 ?가 있다)
		String sql = boundSql.getSql();
		// 쿼리실행시 맵핑되는 파라미터를 구한다
		Object param = handler.getParameterHandler().getParameterObject();
		if (param == null) {
			// 파라미터가 아무것도 없을 경우
			sql = sql.replaceFirst("\\?", "''");
		} else {
			// 해당 파라미터의 클래스가 Integer, Long, Float, Double 클래스일 경우
			if (param instanceof Integer || param instanceof Long
					|| param instanceof Float || param instanceof Double) {
				sql = sql.replaceFirst("\\?", param.toString());
			} else if (param instanceof String) {
				// 해당 파라미터의 클래스가 String 일 경우(이 경우는 앞뒤에 '(홑따옴표)를 붙여야해서 별도 처리.
				sql = sql.replaceFirst("\\?", "'" + param + "'");
			} else if (param instanceof Map) {
				// 해당 파라미터가 Map 일 경우
				/*
				 * 쿼리의 ?와 매핑되는 실제 값들의 정보가 들어있는 ParameterMapping 객체가 들어간 List 객체로
				 * return이 된다. 이때 List 객체의 0번째 순서에 있는 ParameterMapping 객체가 쿼리의
				 * 첫번째 ?와 매핑이 된다 이런 식으로 쿼리의 ?과 ParameterMapping 객체들을 Mapping 한다
				 */
				final List<ParameterMapping> paramMapping = boundSql
						.getParameterMappings();
				for (ParameterMapping mapping : paramMapping) {
					// 파라미터로 넘긴 Map의 key 값이 들어오게 된다
					final String propValue = mapping.getProperty();
					// 넘겨받은 key 값을 이용해 실제 값을 꺼낸다.
					Object value = ((Map) param).get(propValue);
					if (value instanceof String) {
						// SQL의 ? 대신에 실제 값을 넣는다. 이때 String 일 경우는 '를 붙여야 하기땜에 별도
						// 처리
						sql = sql.replaceFirst("\\?", "'" + value + "'");
					} else {
						sql = sql.replaceFirst("\\?", value.toString());
					}
				}
			} else { // 해당 파라미터가 사용자 정의 클래스일 경우
				/*
				 * 쿼리의 ?와 매핑되는 실제 값들이 List 객체로 return이 된다. 이때 List 객체의 0번째 순서에
				 * 있는 ParameterMapping 객체가 쿼리의 첫번째 ?와 매핑이 된다 이런 식으로 쿼리의 ?과
				 * ParameterMapping 객체들을 Mapping 한다
				 */
				List<ParameterMapping> paramMapping = boundSql
						.getParameterMappings();
				Class<? extends Object> paramClass = param.getClass();
				for (ParameterMapping mapping : paramMapping) {
					String propValue = mapping.getProperty();
					// 해당 파라미터로 넘겨받은 사용자 정의 클래스 객체의 멤버변수명
					Object v = null;
					try {
						v = engine.getProperty(param, propValue);
					} catch (JexlException e) {
						logger.warn(
								String.format(
										"SKIP PROPERTY - [%s]. (no such public getter or field on parameter object.).",
										propValue), e);
						continue;
					}
					// 해당 파라미터로 넘겨받은 사용자 정의 클래스 객체의 멤버변수의 타입.
					Class<?> javaType = mapping.getJavaType();
					if (String.class == javaType) {
						// SQL의 ? 대신에 실제 값을 넣는다. 이때 String 일 경우는 '를 붙여야 하기땜에 별도
						// 처리.
						sql = sql.replaceFirst("\\?", "'" + v + "'");
					} else {
						sql = sql.replaceFirst("\\?", ObjectUtils.toString(v));
					}
				}
			}
		}
		//
		logger.debug("---------------------------------------------------------------------");
		logger.debug("sql : \n{}", sql);
		Object o = invocation.proceed(); // 쿼리 실행
		logger.debug("=====================================================================");
		//
		return o;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
