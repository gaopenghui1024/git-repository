package com.work.iwms.webservice.axis2service.impl;

import com.work.iwms.config.Axis2Config;
import org.apache.axis2.AxisFault;
import com.work.iwms.webservice.axis2service.IUserService2;

public class UserServiceImpl2 implements IUserService2 {
    @Override

    public String getHello(String name)throws AxisFault {
        Axis2Config.checkUserPwd();
        return "hello:"+name;
    }
}
