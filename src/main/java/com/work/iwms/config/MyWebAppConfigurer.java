package com.work.iwms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 跨域访问配置
 * 
 * @author Gene
 *
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

	/** 该方法用来 解决跨域问题 **/
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径。"/**"覆盖项目所有路径
		registry.addMapping("/**")
				//是否发送Cookie信息
				.allowCredentials(true)
				// 允许所有的请求方法访问该跨域资源服务器，如：POST、GET、PUT、DELETE等。
				.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
				//允许所有的请求域名访问我们的跨域资源，可以固定单条或者多条内容，
				//如："http://www.baidu.com"，只有百度可以访问我们的跨域资源。
				.allowedOrigins("*")
				//允许所有的请求header访问，可以自定义设置任意请求头信息，如："X-YAUTH-TOKEN"
				.allowedHeaders("*");
	}
}
