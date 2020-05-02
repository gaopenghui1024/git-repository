package com.work.iwms.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

    /**
     * druid配置
     *
     * @author
     *
     */
    @Configuration
    public class DruidConfig {

        @Bean
        public ServletRegistrationBean druidServlet() {// 主要实现web监控的配置处理
            ServletRegistrationBean servletRegistrationBean =
                    new ServletRegistrationBean(new StatViewServlet(),"/druid/*");//绑定后台监控界面的路径  为/druid
            servletRegistrationBean.addInitParameter("allow", "");//白名单为空允许任何ip访问
            servletRegistrationBean.addInitParameter("loginUsername", "root");// 用户名
            servletRegistrationBean.addInitParameter("loginPassword", "root");// 密码
            servletRegistrationBean.addInitParameter("resetEnable", "false");// 是否可以重置数据源
            return servletRegistrationBean;

        }

        @Bean // 监控
        public FilterRegistrationBean filterRegistrationBean() {
            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
            filterRegistrationBean.setFilter(new WebStatFilter());
            filterRegistrationBean.addUrlPatterns("/*");// 所有请求进行监控处理
            filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.css,*.ico,/druid/*");// 排除
            return filterRegistrationBean;
        }

        //连接池的yml文件配置读取，加载
        @Bean
        @ConfigurationProperties(prefix = "spring.datasource")//在application.yml中读取配置信息注入到Druid
        public DataSource druidDataSource() {
            return new DruidDataSource();
        }
}
