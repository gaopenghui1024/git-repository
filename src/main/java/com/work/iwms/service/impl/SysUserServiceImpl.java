package com.work.iwms.service.impl;

import com.work.iwms.entity.SysUser;
import com.work.iwms.repository.SysUserRepository;
import com.work.iwms.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements ISysUserService {
    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public void saveUser(SysUser sysUser) {
        sysUserRepository.save(sysUser);
    }
}
