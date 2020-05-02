package com.work.iwms.webservice;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


/**
 * rest
 * 
 * @author Gene
 *
 */
@Service
public class HttpClient {
	public String client(String url, MultiValueMap<String, String> map) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		//List<String> cookies =new ArrayList<String>();
		/* 登录获取Cookie 这里是直接给Cookie，可使用下方的login方法拿到Cookie给入*/
		//cookies.add("XXL_JOB_LOGIN_IDENTITY=6333303830376536353837616465323835626137616465396638383162336437; Path=/; HttpOnly");       //在 header 中存入cookies
		//headers.put(HttpHeaders.COOKIE,cookies);        //将cookie存入头部

		//headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		//解决中文乱码
		//headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		//注:GET请求 创建HttpEntity时,请求体传入null即可
		HttpEntity<MultiValueMap<String, String>> httpEntity = 
				new HttpEntity<MultiValueMap<String, String>>(map,headers);
		
		//postForEntity():POST数据到一个URL，返回包含一个对象的ResponseEntity，这个对象是从响应体中映射得到的
		//ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
		
		/**
		 * 往 url发送 post请求 请求携带信息为entity时返回的结果信息 
		 * 因为返回的是HttpEntity<MultiValueMap<String, String>>所以如果要得到字符串不能强转为String，可以exchange().getBody()获取字符串
		 *	String.class是可以修改的，其实本质上就是在指定反序列化对象类型，这取决于你要怎么解析请求返回的参数
		 */
		String strbody = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class).getBody();
		/**
		 * 由于获取到的是字符串，需要的是自定义对象
		 * 可以使用Gson转换
		 * toJson(entity)//实体转字符串
		 * fromJson(entity,entity.class)//字符串转实体
		 */
		/*Gson gson = new Gson();
		BoxDataItem boxData = gson.fromJson(strbody, BoxDataItem.class);*/
		return strbody;
	}
}
