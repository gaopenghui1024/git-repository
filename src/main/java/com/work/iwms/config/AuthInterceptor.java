package com.work.iwms.config;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.security.AuthenticationException;
import org.apache.cxf.phase.AbstractPhaseInterceptor;


import org.apache.cxf.headers.Header;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import javax.xml.namespace.QName;


/**
 * 安全校验拦截器
 * 
 * @author Gene
 *
 */

@Component
public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	// 由于AbstractPhaseInterceptor无无参数构造器，使用继承的方式，需要显示调用父类有参数的构造器
	public AuthInterceptor() {
		super(Phase.USER_LOGICAL);// 该拦截器将会调用之前拦截SOAP消息
	}

	// 实现自己的拦截器时，需要实现handleMessage方法。
	// handleMessage方法中的形参就是被拦截到的Soap消息
	// 一旦程序获取了SOAP消息，剩下的事情就可以解析SOAP消息或修改SOAP消息
//	@Override
//	public void handleMessage(SoapMessage msg) throws Fault {
//
//		// 从这里可以看出，我们已经拦截到了SOAP消息
//		System.out.println("-------" + msg);
//		// 得到SOAP消息所有Header
//		List<Header> headers = msg.getHeaders();
//
//		// 如果没有Header
//		if (headers == null || headers.size() < 1){
//			throw new Fault(new IllegalArgumentException("请输入用户名密码验证，否则不能调用"));
//		}
//		// 假如要求第一个Header携带了用户名，密码信息
//		Header firstHeader = headers.get(0);
//		Element ele = (Element) firstHeader.getObject();
//
//		NodeList userName = ele.getElementsByTagName("username");
//		NodeList password = ele.getElementsByTagName("password");
//
//		if (userName.getLength() != 1) {
//			throw new Fault(new IllegalArgumentException("用户名的格式不正确！"));
//		}
//		if (password.getLength() != 1) {
//			throw new Fault(new IllegalArgumentException("密码的格式不正确！"));
//		}
//		// 得到第一个userId元素里的文本内容，以该内容作为用户名字
//		String user = userName.item(0).getTextContent();
//		String pwd = password.item(0).getTextContent();
//		// 实际项目中，应该去查询数据库，该用户名密码是否被授权调用web service
//		if (!user.equals("admin") || !pwd.equals("123456")) {
//			throw new Fault(new IllegalArgumentException("用户名密码不正确！"));
//		}
//	}


//	@Override
//	public void handleMessage(SoapMessage message) throws Fault {
//
//		Header header = message.getHeader(new QName("RequestSOAPHeader"));
//		if (null == header) {
//			throw new Fault(new AuthenticationException("token没有认证通过!原因为：客户端请求中不存在认证的Header"));
//		}
//		Element ele = (Element) header.getObject();
//		if (null == ele) {
//			throw new Fault(new AuthenticationException("token没有认证通过!原因为：客户端请求中认证的Header无认证信息"));
//		}
//		NodeList userName = ele.getElementsByTagName("username");
//		NodeList passWord = ele.getElementsByTagName("password");
//
//		if (null == userName || null == passWord) {
//			throw new Fault(new AuthenticationException("token没有认证通过!原因为：客户端请求中认证的Header无认证信息"));
//		}
//		String username = userName.item(0).getTextContent();
//		String password = passWord.item(1).getTextContent();
//
//		if (!username.equals("admin") || !password.equals("123456")) {
//			throw new Fault(new AuthenticationException("用户名密码不正确！"));
//		}
//
//	}






	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		Header header = message.getHeader(new QName("RequestSOAPHeader"));
		if(null == header){
			throw new Fault(new AuthenticationException("token没有认证通过!原因为：客户端请求中不存在认证的Header"));
		}
		Element ele = (Element) header.getObject();
		if(null == ele){
			throw new Fault(new AuthenticationException("token没有认证通过!原因为：客户端请求中认证的Header无认证信息"));
		}
		Node node = ele.getFirstChild();
		if(null == node){
			throw new Fault(new AuthenticationException("token没有认证通过!原因为：客户端请求中认证的Header无认证信息"));
		}
		String token = node.getTextContent();
		if(null == token || token.isEmpty()){
			throw new Fault(new AuthenticationException("token没有认证通过!原因为：客户端请求中认证的Header无token信息"));
		}

		if(!token.equals("123456")){
			throw new Fault(new AuthenticationException("token没有认证通过!原因为：客户端请求中认证的token信息无效，请查看申请流程中的正确token信息，流程申请地址:http://127.0.0.1:8080/email"));
		}

	}

}
