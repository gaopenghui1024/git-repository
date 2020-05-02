package com.work.iwms.controller;

import com.work.iwms.webservice.HttpClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private HttpClient httpClient;
    @RequestMapping(value="/getData/{userId}")
    public String getData(@PathVariable("userId") String requestStr){
        String url ="http://127.0.0.1:8082/user/getCurrentData";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String,String>();
        map.add("requestStr",requestStr);
        String data = httpClient.client(url,map);
        return data;
    }
    @RequestMapping(value="/getCurrentData")
    public String getCurrentData(HttpServletRequest request){
        String userId = request.getParameter("requestStr");
        System.out.println(userId);
        String data ="{\"user\":[{\"userId\":\"root\",\"password\":\"root\"},{\"userId\":\"dds\",\"password\":\"123\"}]}";
        return data;
    }

}
