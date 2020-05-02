package com.work.iwms.webservice.cxfservice;

import com.work.iwms.entity.SysUser;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Map;

public class client3 {
    static {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("static/admin", "123456".toCharArray());
            }
        });
    }
    public final static String URL ="http://127.0.0.1:8082/soap/user?wsdl";
    public static void main(String[] args) {
        try {


            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(URL);
//            jaxWsProxyFactoryBean.getInInterceptors().add(new ClientToken("123456"));
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(IUserService.class);
            // 创建一个代理接口实现
            IUserService us = (IUserService) jaxWsProxyFactoryBean.create();

            //数据准备
//            String name = "小高";
//            //
//            String result = us.getHello(name);
//            System.out.println("返回结果:" + result);

//            Client client = ClientProxy.getClient(us);
//            client.getInInterceptors().add(new ClientInterceptor());

            SysUser user = new SysUser();
            user.setUserId("uuid006");
            user.setUserName("xiaogao");
            user.setPassWord("123");
            user.setUseable("Y");
            user.setCreateTime("2020-04-28");
            Map<String, Object> r = us.getUser(user);
            System.out.println(r.toString());


//            JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
//            Client client = clientFactory.createClient(URL);
//            try {
//                client.getOutInterceptors().add(new ClientInterceptor());
//                Object[] result1 = client.invoke("getHello", "GENE");
//                System.out.println(result1[0]);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
