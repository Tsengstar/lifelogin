package cn.zwsheng.lifelogin.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = LifeDataSourceConfig.JPA_PACKAGE, transactionManagerRef = "lifeTransactionManager",
        entityManagerFactoryRef = "lifeEntityManagerFactory")
public class LifeDataSourceConfig {

    static final String JPA_PACKAGE = "cn.zwsheng.lifelogin.repository";
    private static final String ENTITY_LOCATION = "cn.zwsheng.lifelogin.entity";

    @Autowired
    private JpaProperties jpaProperties;

    @Bean(name = {"lifeDataSource"})
    @ConfigurationProperties(prefix = "life.datasource.druid")
    public DataSource lifeDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "jdbclife")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("lifeDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "namedJdbclife")
    @Qualifier(value = "namedJdbclife")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(
            @Qualifier("lifeDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean(name = {"lifeEntityManagerFactory"})
    public EntityManagerFactory lifeEntityManagerFactory(
            @Qualifier("lifeDataSource") DataSource lifeDataSource) {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(lifeDataSource);
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setJpaPropertyMap(jpaProperties.getProperties());
        adapter.setShowSql(jpaProperties.isShowSql());
        factory.setJpaVendorAdapter(adapter);
        factory.setPackagesToScan(LifeDataSourceConfig.ENTITY_LOCATION);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = {"lifeTransactionManager"})
    public PlatformTransactionManager oracleTransactionManager(
            @Qualifier("lifeEntityManagerFactory") EntityManagerFactory lifeEntityManagerFactory) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(lifeEntityManagerFactory);
        return manager;
    }
}
