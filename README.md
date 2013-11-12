mybatis-log-interceptor-plugin
=============================

* display parameter bound SQLs for your MyBatis Mappers.


**WARN: DO NOT USE THIS!!!!**
===============================
* IT'S TROUBLESOME WITH `<foreach/>`s.

# usage

1. add repositories to `pom.xml`:
  * we host this artifact for you. 
  * [**it's good for you.**](http://youtu.be/Klx7qxgurMM)
  
```xml
<repositories>
    <repository>
        <id>inger-release</id>
        <name>inger.co.kr releases</name>
        <url>http://j.inger.co.kr/nexus/content/repositories/releases</url>
        <layout>default</layout>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
    <repository>
        <id>inger-snapshot</id>
        <name>inger.co.kr snapshots</name>
        <url>http://j.inger.co.kr/nexus/content/repositories/snapshots</url>
        <layout>default</layout>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>		
</repositories>
```


2. add dependencies to `pom.xml`:
  * you need `commons-lang3`, `commons-jexl`, `slf4j-api`, `mybatis`, and `mybatis-log-interceptor-plugin`. ;-)

```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.1</version>
    <scope>provided</scope>
</dependency>

<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-jexl</artifactId>
    <version>2.1.1</version>
</dependency>

<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.5</version>
</dependency>

<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.2.3</version>
</dependency>

<dependency>
    <groupId>jhyun</groupId>
    <artifactId>mybatis-log-interceptor-plugin</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```


2. just configure as a MyBatis plugin.

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