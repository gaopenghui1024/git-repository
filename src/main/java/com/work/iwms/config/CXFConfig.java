package com.work.iwms.config;

import com.work.iwms.webservice.cxfservice.IUserService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.xml.ws.Endpoint;

@Configuration
public class CXFConfig {
    @Autowired
    private Bus bus;
    @Autowired
    private IUserService userService;
//    @Autowired
//    private AuthInterceptor interceptor;


    /**
     * 此方法被注释后:wsdl访问地址为http://127.0.0.1:8080/services/user?wsdl
     * 去掉注释后：wsdl访问地址为：http://127.0.0.1:8080/soap/user?wsdl
     */
    @Bean
    public ServletRegistrationBean disServlet(){
        return new ServletRegistrationBean(new CXFServlet(),"/soap/*");
    }

    /**
     * 发布服务
     * 指定访问url
     * @return
     */
    @Bean
    public Endpoint userEndpoint(){
        EndpointImpl endpoint = new EndpointImpl(bus,userService);
        endpoint.publish("/user");
//        endpoint.getInInterceptors().add(interceptor); // 添加Header认证拦截器;
        return endpoint;
    }
}
