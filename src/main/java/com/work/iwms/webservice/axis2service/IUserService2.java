package com.work.iwms.webservice.axis2service;

import org.apache.axis2.AxisFault;

import javax.jws.WebParam;


public interface IUserService2 {
    String getHello(String name) throws AxisFault;
}
