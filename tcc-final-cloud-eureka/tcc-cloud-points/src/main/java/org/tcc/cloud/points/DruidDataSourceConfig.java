package org.tcc.cloud.points;

import java.io.IOException;
import java.sql.SQLException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@MapperScan(value = "org.tcc.cloud.points.dao")
public class DruidDataSourceConfig{



    @Value("${spring.datasource.db.url}")
    private String url;

    @Value("${spring.datasource.db.username}")
    private String username;

    @Value("${spring.datasource.db.password}")
    private String password;

    @Value("${spring.datasource.db.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.db.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.db.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.db.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.db.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.db.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.db.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.db.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.db.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.db.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.db.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.db.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.db.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.db.filters}")
    private String filters;

    @Value("{spring.datasource.db.connectionProperties}")
    private String connectionProperties;

    //  配置类型别名
    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    //  配置mapper的扫描，找到所有的mapper.xml映射文件
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    @Bean//声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DruidDataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
           
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }


    // 提供SqlSeesion
    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() {
        try {
            SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
            sessionFactoryBean.setDataSource(dataSource());

            // 读取配置
            sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);

            //设置mapper.xml文件所在位置
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
            sessionFactoryBean.setMapperLocations(resources);
            return sessionFactoryBean.getObject();
        } catch (IOException e) {
        	e.printStackTrace();//测试例子，就不做日志了
            return null;
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }

    //事务管理
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
