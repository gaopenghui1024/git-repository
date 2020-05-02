package com.work.iwms.config;

import com.work.iwms.util.FileCopyUtils;
import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.axis2.transport.http.AxisServlet;

import java.io.IOException;
import java.util.Iterator;

@Configuration
public class Axis2Config {

    @Bean
    public ServletRegistrationBean<AxisServlet> axisServlet() {
        ServletRegistrationBean<AxisServlet> helloWorldServlet = new ServletRegistrationBean<AxisServlet>();
        helloWorldServlet.setServlet(new AxisServlet());// 这里的AxisServlet就是web.xml中的org.apache.axis2.transport.http.AxisServlet
        helloWorldServlet.addUrlMappings("/services/*");
        // 通过默认路径无法找到services.xml，这里需要指定一下路径，且必须是绝对路径
        String path = this.getClass().getResource("/ServicesPath").getPath().toString();
        if (path.toLowerCase().startsWith("file:")) {
            path = path.substring(5);
        }
        /**
         * @如果获得到的地址里有感叹号，说明文件在压缩包（jar包）中，Axis2无法正常使用，需要拷贝到jar包外
         *
         * */
        if (path.indexOf("!") != -1) {
            try {
                FileCopyUtils.copy("ServicesPath/services/axis2/META-INF/services.xml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            path = path.substring(0, path.lastIndexOf("/", path.indexOf("!"))) + "/ServicesPath";
        }
        helloWorldServlet.addInitParameter("axis2.repository.path", path);
        helloWorldServlet.setLoadOnStartup(1);
        return helloWorldServlet;
    }

    public static void checkUserPwd() throws AxisFault {
        MessageContext msgContext = MessageContext.getCurrentMessageContext();
        Iterator list = (Iterator)msgContext.getEnvelope().getHeader().getFirstElement().getChildren();
        String Username = "";
        String Password = "";

        while (list.hasNext()) {
            OMElement element = (OMElement) list.next();

            if (element.getLocalName().equals("Username")) {
                Username = element.getText();
            }
            if (element.getLocalName().equals("Password")) {
                Password = element.getText();
            }
        }
        if (!Username.equals("static/admin") || !Password.equals("123456")){
            throw new AxisFault(" Authentication Fail! Check username/password ");
        }
    }
}
