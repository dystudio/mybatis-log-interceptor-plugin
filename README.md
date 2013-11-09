mybatis-log-interceptor-plugin
=============================

* display parameter bound SQLs for your MyBatis Mappers.

* just configure as a MyBatis plugin.

```java
    // for Spring JavaConfig.
    @Bean
    @Autowired
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource,
			ResourceLoader resourceLoader) throws Exception {
		SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
		ssfb.setDataSource(dataSource);
		ssfb.setMapperLocations(mybatisMapperLocations(resourceLoader,
				MYBATIS_MAPPER_LOCATIONS));
		ssfb.setPlugins(new Interceptor[] { new MybatisLogInterceptor(), });
		return ssfb;
	}
```

  ..or `mybatis-cofig.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "HTTP://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

	<settings>    
	</settings>
	
	<typeAliases>
	</typeAliases>
	
	<plugins>
		<plugin interceptor="jhyun.mlih.mybatis.MybatisLogInterceptor"/>
	</plugins>
	
</configuration>
```

* thanks to [Original Author. ](http://zgundam.tistory.com/34)