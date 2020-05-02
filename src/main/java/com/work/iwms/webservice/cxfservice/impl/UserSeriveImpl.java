package com.work.iwms.webservice.cxfservice.impl;

import com.work.iwms.entity.SysUser;
import com.work.iwms.repository.SysUserRepository;
import com.work.iwms.webservice.cxfservice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Component
@Transactional
public class UserSeriveImpl implements IUserService {
    @Autowired
    private SysUserRepository sysUserRepository;
    @Override
    public String getHello(String name) {
        return "hello:"+name;
    }

    @Override
    public Map<String, Object> getUser(SysUser user) {
//        log.info("客户端请求数据："+user.toString());
        Map<String, Object> map = new HashMap<String, Object>();
        sysUserRepository.saveAndFlush(user);
        map.put("success", true);
        return map;
    }
}
