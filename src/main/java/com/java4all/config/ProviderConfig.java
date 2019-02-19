package com.java4all.config;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.bytesoft.bytejta.supports.jdbc.LocalXADataSource;
import org.bytesoft.bytetcc.supports.springcloud.config.SpringCloudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 配置数据源
 *
 * 补偿型service中应该使用org.bytesoft.bytejta.supports.jdbc.LocalXADataSource封装过的数据源。
 */

@Configuration
public class ProviderConfig {

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		LocalXADataSource dataSource = new LocalXADataSource();
		dataSource.setDataSource(this.invokeGetDataSource());
		return dataSource;
	}


	/**
	 * 建议从配置文件获取
	 * @return
	 */
	public DataSource invokeGetDataSource() {
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName("com.mysql.jdbc.Driver");
		bds.setUrl("jdbc:mysql://116.62.62.26:3306/bank");
		bds.setUsername("root");
		bds.setPassword("14789632");
		bds.setMaxTotal(50);
		bds.setInitialSize(20);
		bds.setMaxWaitMillis(60000);
		bds.setMinIdle(6);
		bds.setLogAbandoned(true);
		bds.setRemoveAbandonedOnBorrow(true);
		bds.setRemoveAbandonedOnMaintenance(true);
		bds.setRemoveAbandonedTimeout(1800);
		bds.setTestWhileIdle(true);
		bds.setTestOnBorrow(false);
		bds.setTestOnReturn(false);
		bds.setValidationQuery("select 'x' ");
		bds.setValidationQueryTimeout(1);
		bds.setTimeBetweenEvictionRunsMillis(30000);
		bds.setNumTestsPerEvictionRun(20);
		return bds;
	}

	@Bean
	public JdbcTemplate getJdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(this.getDataSource());
		return jdbcTemplate;
	}

}
