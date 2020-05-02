package com.work.iwms.webservice.cxfservice;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;

public class ClientToken extends AbstractPhaseInterceptor<SoapMessage> {

    /**
     * 客户端申请的token信息
     */
    private String token;
    public ClientToken(String token) {
        super(Phase.PREPARE_SEND);
        this.token=token;
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        // 在soap消息中添加认证头信息
        List<Header> headers = message.getHeaders();
        Document doc = DOMUtils.createDocument();
        Element auth = doc.createElement("RequestSOAPHeader");
        Element token = doc.createElement("token");
        token.setTextContent(this.token);
        auth.appendChild(token);
        Header header = new SoapHeader(new QName("RequestSOAPHeader"), auth);
        headers.add(header);
    }
}
