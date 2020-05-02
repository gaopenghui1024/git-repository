package com.work.iwms.webservice.cxfservice;

import com.work.iwms.entity.SysUser;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Map;

@WebService(name="IUserService",targetNamespace = "http://cxfservice.webservice.iwms.work.com")
public interface IUserService {
    @WebMethod(operationName = "getHello")//在wsdl文档中显示的方法名，可不指定默认与方法相同；@WebMethod可不指定
    String getHello(@WebParam(name = "name") String name);
    @WebMethod(operationName = "getUser")//在wsdl文档中显示的方法名，可不指定默认与方法相同；@WebMethod可不指定
    Map<String, Object> getUser(@WebParam(name = "users") SysUser user);
}
