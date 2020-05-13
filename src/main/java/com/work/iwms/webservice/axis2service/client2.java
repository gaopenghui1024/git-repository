package com.work.iwms.webservice.axis2service;

import javax.xml.namespace.QName;

import com.work.iwms.webservice.axis2service.client.HelloServiceStub;
import com.work.iwms.webservice.axis2service.client.HelloServiceStub.GetHello;
import com.work.iwms.webservice.axis2service.client.HelloServiceStub.GetHelloResponse;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HttpTransportProperties;

import java.rmi.RemoteException;

public class client2 {

    private final static String url = "http://localhost:8082/services/HelloService?wsdl";
    private final static String Username= "static/admin";
    private final static String Password="123456";
    private final static String tns="http://impl.axis2service.webservice.iwms.work.com";

    public static void main(String[] args) {

        //new client2().testRPCClient();
        //new client2().testDocument();
        new client2().testCodeClient();
        //new client2().testClient();
    }
    /**
     * 方法一：
     * 应用rpc的方式调用 这种方式就等于远程调用，
     * 即通过url定位告诉远程服务器，告知方法名称，参数等， 调用远程服务，得到结果。
     * 使用 org.apache.axis2.rpc.client.RPCServiceClient类调用WebService
     *
     【注】：

     如果被调用的WebService方法有返回值 应使用 invokeBlocking 方法 该方法有三个参数
     第一个参数的类型是QName对象，表示要调用的方法名；
     第二个参数表示要调用的WebService方法的参数值，参数类型为Object[]；
     当方法没有参数时，invokeBlocking方法的第二个参数值不能是null，而要使用new Object[]{}。
     第三个参数表示WebService方法的 返回值类型的Class对象，参数类型为Class[]。


     如果被调用的WebService方法没有返回值 应使用 invokeRobust 方法
     该方法只有两个参数，它们的含义与invokeBlocking方法的前两个参数的含义相同。

     在创建QName对象时，QName类的构造方法的第一个参数表示WSDL文件的命名空间名，
     也就是 <wsdl:definitions>元素的targetNamespace属性值。
     *
     */
    public void testRPCClient() {

        try {
            // axis1 服务端
            // String url = "http://localhost:8080/StockQuote/services/StockQuoteServiceSOAP11port?wsdl";
            // axis2 服务端

            String method="getHello";
            // 使用RPC方式调用WebService
            RPCServiceClient serviceClient = new RPCServiceClient();
            //Header构造验证信息
            addValidation(serviceClient,tns,Username,Password);
            // 指定调用WebService的URL
            EndpointReference targetEPR = new EndpointReference(url);
            Options options = serviceClient.getOptions();
            //确定目标服务地址
            options.setTo(targetEPR);
            //确定调用方法
            options.setAction("urn:getHello");

            /**
             * 指定要调用的getHello方法及WSDL文件的命名空间
             * 如果 webservice 服务端由axis2编写
             * 命名空间 不一致导致的问题
             * org.apache.axis2.AxisFault: java.lang.RuntimeException: Unexpected subelement arg0
             */
            QName qname = new QName(tns, method);
            // 指定getPrice方法的参数值
            Object[] parameters = new Object[] { "Gene" };

            // 指定getPrice方法返回值的数据类型的Class对象
            Class[] returnTypes = new Class[] { String.class };
            // 调用方法一 传递参数，调用服务，获取服务返回结果集
            OMElement element = serviceClient.invokeBlocking(qname, parameters);
            //值得注意的是，返回结果就是一段由OMElement对象封装的xml字符串。
            //我们可以对之灵活应用,下面我取第一个元素值，并打印之。因为调用的方法返回一个结果
            String result = element.getFirstElement().getText();
            System.out.println(result);

            // 调用方法二 getPrice方法并输出该方法的返回值
            Object[] response = serviceClient.invokeBlocking(qname, parameters, returnTypes);
            // String r = (String) response[0];
            String r = (String) response[0];
            System.out.println(r);
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
    }


    /**
     * 方法二： 应用document方式调用
     * 用ducument方式应用现对繁琐而灵活。现在用的比较多。因为真正摆脱了我们不想要的耦合
     */
    public  void testDocument() {
        try {
            // String url = "http://localhost:8080/axis2ServerDemo/services/StockQuoteService";

            Options options = new Options();
            // 指定调用WebService的URL
            EndpointReference targetEPR = new EndpointReference(url);
            options.setTo(targetEPR);
            // options.setAction("urn:getHello");

            ServiceClient sender = new ServiceClient();
            sender.setOptions(options);
            addValidation(sender,tns,Username,Password);

            OMFactory fac = OMAbstractFactory.getOMFactory();
            String tns = "http://impl.axis2service.webservice.iwms.work.com";
            // 命名空间，有时命名空间不增加没事，不过最好加上，因为有时有事，你懂的
            OMNamespace omNs = fac.createOMNamespace(tns, "");

            OMElement method = fac.createOMElement("getHello", omNs);
            OMElement name = fac.createOMElement("name", omNs);
            // name.setText("1");
            name.addChild(fac.createOMText(name, "Gene"));
            method.addChild(name);
            method.build();
            OMElement result = sender.sendReceive(method);

            System.out.println(result.getFirstElement().getText());

        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
    }

    /**
     * 方法三：利用axis2插件生成客户端方式调用
     *
     */
    public  void testCodeClient() {
        try {
            HelloServiceStub stub = new HelloServiceStub(url);
            HttpTransportProperties.ProxyProperties auth = new HttpTransportProperties.ProxyProperties();
            auth.setUserName("static/admin");
            auth.setPassWord("123456");
           // addValidation(stub._getServiceClient(),tns,Username,Password);
            GetHello request = new GetHello();
            request.setName("Gene");
            GetHelloResponse response = stub.getHello(request);
            System.out.println(response.get_return());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void testClient(){
        try{
            HelloServiceStub stub = new HelloServiceStub();
            stub._getServiceClient().getOptions().setProperty(
                    org.apache.axis2.transport.http.HTTPConstants.CHUNKED,
                    Boolean.FALSE);
            EndpointReference reference = new EndpointReference();
            reference.setAddress(url);
            stub._getServiceClient().getOptions().setTo(reference);
            addValidation(stub._getServiceClient(),tns,Username,Password);
            GetHello hello = new GetHello();
            hello.setName("gene");
            GetHelloResponse response = stub.getHello(hello);
            System.out.println(response.get_return());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 为SOAP Header构造验证信息，
     * 如果你的服务端是没有验证的，那么你不用在Header中增加验证信息
     *
     * @param serviceClient
     * @param tns 命名空间
     * @param user
     * @param password
     */
    public void addValidation(ServiceClient serviceClient, String tns , String user, String password) {
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace omNs = fac.createOMNamespace(tns, "nsl");
        OMElement header = fac.createOMElement("AuthenticationToken", omNs);
        OMElement ome_user = fac.createOMElement("Username", omNs);
        OMElement ome_pass = fac.createOMElement("Password", omNs);

        ome_user.setText(user);
        ome_pass.setText(password);

        header.addChild(ome_user);
        header.addChild(ome_pass);
        serviceClient.addHeader(header);
//        OMFactory fac = OMAbstractFactory.getOMFactory();
//        SOAPFactory sOAPFactory = OMAbstractFactory.getSOAP11Factory();
//
//        OMNamespace omNs = fac.createOMNamespace(tns, "nsl");
//        SOAPHeaderBlock soapHeader = sOAPFactory.createSOAPHeaderBlock("AuthenticationToken", omNs);
//
//        SOAPHeaderBlock usernameBlock = sOAPFactory.createSOAPHeaderBlock("Username", omNs);
//        usernameBlock.addChild(sOAPFactory.createOMText(user));
//
//        SOAPHeaderBlock passwordBlock = sOAPFactory.createSOAPHeaderBlock("Password", omNs);
//        passwordBlock.addChild(sOAPFactory.createOMText(password));
//
//        soapHeader.addChild(usernameBlock);
//        soapHeader.addChild(passwordBlock);
//        serviceClient.addHeader(soapHeader);
    }

}
