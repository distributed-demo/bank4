package com.java4all;

import org.bytesoft.bytetcc.supports.springcloud.config.SpringCloudConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

//引入byteTCC配置
@ImportResource({"classpath:bytetcc-supports-springcloud.xml"})
@Import(SpringCloudConfiguration.class)
//配置扫描的应用包
@SpringBootApplication(scanBasePackages = "com.java4all")
@EnableEurekaClient
@EnableDiscoveryClient
public class Bank4Application {

	public static void main(String[] args) {
		SpringApplication.run(Bank4Application.class, args);
	}

}
