package com.example.mvcdemo;

import com.example.mvcdemo.filter.DemoFilter;
import com.example.mvcdemo.servlet.Demo2Servlet;
import com.example.mvcdemo.servlet.DemoServlet;
import com.mongodb.ClientSessionOptions;
import com.mongodb.client.ClientSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableMongoAuditing
@SpringBootApplication
public class MvcdemoApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(MvcdemoApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean getServletRegistrationBean() {
		ServletRegistrationBean bean = new ServletRegistrationBean(new DemoServlet());
		bean.addUrlMappings("/demo-servlet/*");  //访问路径值
		return bean;
	}

	@Bean
	public ServletRegistrationBean getServletRegistrationBean2() {
		ServletRegistrationBean bean = new ServletRegistrationBean(new Demo2Servlet());
		bean.addUrlMappings("/demo2-servlet/*");  //访问路径值
		return bean;
	}

	@Bean
	public FilterRegistrationBean getFilterRegistrationBean(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new DemoFilter());
		filterRegistrationBean.addServletRegistrationBeans(getServletRegistrationBean());
		return filterRegistrationBean;
	}

}
