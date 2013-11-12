package jhyun.mlih.spring;

import java.io.IOException;

import javax.sql.DataSource;

import jhyun.mlih.mybatis.DoNothingInterceptor;
import jhyun.mlih.mybatis.MybatisLogInterceptor;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@MapperScan(basePackages = { "jhyun" })
@Import(value = { TransactionContext.class })
@Configuration
public class MybatisContext {
	public final String MYBATIS_MAPPER_LOCATIONS = "classpath*:jhyun/**/sqlmaps/*.xml";

	private Resource[] mybatisMapperLocations(ResourceLoader resourceLoader,
			final String pattern) throws IOException {
		PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver(
				resourceLoader);
		return pmrpr.getResources(MYBATIS_MAPPER_LOCATIONS);
	}

	@Bean
	@Autowired
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource,
			ResourceLoader resourceLoader) throws Exception {
		SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
		ssfb.setDataSource(dataSource);
		ssfb.setMapperLocations(mybatisMapperLocations(resourceLoader,
				MYBATIS_MAPPER_LOCATIONS));
		ssfb.setPlugins(new Interceptor[] {//
		// new MybatisLogInterceptor(),//
		new DoNothingInterceptor(),//
		});

		return ssfb;
	}

}
