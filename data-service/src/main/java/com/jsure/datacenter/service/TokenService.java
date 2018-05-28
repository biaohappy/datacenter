package com.jsure.datacenter.service;

import com.jsure.datacenter.model.entity.TRole;
import com.jsure.datacenter.model.entity.TUser;

import java.util.Map;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/5/21
 * @Time: 15:07
 * I am a Code Man -_-!
 */
public interface TokenService {

    Map<String, Object> login(String userName, String password);

    TUser findUserByUserName(String userName);

    TRole findPermissionByRoleId(Integer roleId);

    Map<String, Object> updateUser(TUser user);

    Map<String, Object> addUser(TUser user);
}
