package com.work.iwms.config;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;
@Component
public class ClientInterceptor extends AbstractSoapInterceptor {
    private static final String USERNAME = "static/admin";

    private static final String PASSWORD = "123456";

    public ClientInterceptor() {
        super(Phase.WRITE);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {

        QName qname = new QName("RequestSOAPHeader");
        Document doc = DOMUtils.createDocument();
        Element nameEle = doc.createElement("username");
        nameEle.setTextContent(USERNAME);
        Element pwdEle = doc.createElement("password");
        pwdEle.setTextContent(PASSWORD);
        Element root = doc.createElementNS("http://cxfservice.webservice.iwms.work.com", "tns:RequestSOAPHeader");
        root.appendChild(nameEle);
        root.appendChild(pwdEle);
        SoapHeader head = new SoapHeader(qname, root);
        List<Header> headers = message.getHeaders();
        headers.add(head);
//        QName qname = new QName("RequestSOAPHeader");
//        List<Header> headers = message.getHeaders();
//
//        Document doc = DOMUtils.createDocument();
//
//        Element auth = doc.createElement("RequestSOAPHeader");
//
//        Element name = doc.createElement("username");
//        name.setTextContent(USERNAME);
//
//        Element password = doc.createElement("password");
//        password.setTextContent(PASSWORD);
//
//        auth.appendChild(name);
//        auth.appendChild(password);
//
//        headers.add(new Header(qname, auth));


    }
}
