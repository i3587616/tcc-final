package cn.linc.tccfinal.core.transaction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 数据库层面的配置：jdbcTemplate、事务管理器、dataSource
 * @author chang.lin
 *
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:tcccore.properties")
public class DbConfig{

    @Value("${tccfinal.url}")
    private String url;

    @Value("${tccfinal.username}")
    private String username;

    @Value("${tccfinal.password}")
    private String password;

    @Value("${tccfinal.driverClassName}")
    private String driverClassName;

    @Value("${tccfinal.initialSize}")
    private int initialSize;

    @Value("${tccfinal.minIdle}")
    private int minIdle;

    @Value("${tccfinal.maxActive}")
    private int maxActive;

    @Value("${tccfinal.maxWait}")
    private int maxWait;

    @Value("${tccfinal.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${tccfinal.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${tccfinal.validationQuery}")
    private String validationQuery;

    @Value("${tccfinal.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${tccfinal.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${tccfinal.testOnReturn}")
    private boolean testOnReturn;

    @Value("${tccfinal.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${tccfinal.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("{tccfinal.connectionProperties}")
    private String connectionProperties;

    @Bean(name = "dataSourceTcc")//声明其为Bean实例
    public DruidDataSource dataSourceTcc() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

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
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }

    //事务管理
    @Bean(name = "txManagerTccFinal")
    public PlatformTransactionManager annotationDrivenTransactionManager(DruidDataSource dataSourceTcc) {
        return new DataSourceTransactionManager(dataSourceTcc);
    }
    
    //jdbcTemplate取得
    @Bean(name="jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DruidDataSource dataSourceTcc){
        return new JdbcTemplate(dataSourceTcc);
    }

}
