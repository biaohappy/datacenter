package com.jsure.datacenter.serviceImpl;

import com.google.common.collect.Maps;
import com.jsure.datacenter.constant.CustomConstant;
import com.jsure.datacenter.exception.CustomException;
import com.jsure.datacenter.mapper.TRoleMapper;
import com.jsure.datacenter.mapper.TUserMapper;
import com.jsure.datacenter.model.entity.TRole;
import com.jsure.datacenter.model.entity.TUser;
import com.jsure.datacenter.model.enums.CustomErrorEnum;
import com.jsure.datacenter.service.TokenService;
import com.jsure.datacenter.utils.JWTUtil;
import com.jsure.datacenter.utils.MD5Util;
import com.jsure.datacenter.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/5/21
 * @Time: 15:09
 * I am a Code Man -_-!
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TUserMapper tUserMapper;

    @Autowired
    private TRoleMapper tRoleMapper;

    /**
     * 登录
     * 把token返回给客户端-->客户端保存至cookie-->客户端每次请求附带cookie参数
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public Map<String, Object> login(String userName, String password) {
        Map<String, Object> resultMap = Maps.newHashMap();
        TUser user = tUserMapper.findByUserName(userName);
        //返回用户名错误
        if (ObjectUtils.isNullOrEmpty(user)) {
            throw new CustomException(CustomErrorEnum.ERROR_CODE_341005.getErrorCode(),
                    CustomErrorEnum.ERROR_CODE_341005.getErrorDesc());
        }
        //对密码加密
        password = MD5Util.MD5Encrypt(password);
        //返回密码错误
        if (!password.equals(user.getPassword())) {
            throw new CustomException(CustomErrorEnum.ERROR_CODE_341006.getErrorCode(),
                    CustomErrorEnum.ERROR_CODE_341006.getErrorDesc());
        } else {
            //生成token密钥
            String token = JWTUtil.createJWT(user.getId().toString(), user.getRoleId(), user.getUserName(), CustomConstant.JWT_TTL);
            resultMap.put("Authorization", token);
        }
        return resultMap;
    }

    @Override
    public TUser findUserByUserName(String userName) {
        return tUserMapper.findByUserName(userName);
    }

    @Override
    public TRole findPermissionByRoleId(Integer roleId) {
        return tRoleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Map<String, Object> updateUser(TUser user) {
        Map<String, Object> resultMap = Maps.newHashMap();
        Integer r = tUserMapper.updateByPrimaryKeySelective(user);
        if (ObjectUtils.isNullOrEmpty(r)) {
            throw new CustomException(CustomErrorEnum.ERROR_CODE_341009.getErrorCode(),
                    CustomErrorEnum.ERROR_CODE_341009.getErrorDesc());
        }
        TUser result = tUserMapper.selectByPrimaryKey(user.getId());
        if (ObjectUtils.isNullOrEmpty(result)) {
            throw new CustomException(CustomErrorEnum.ERROR_CODE_341009.getErrorCode(),
                    CustomErrorEnum.ERROR_CODE_341009.getErrorDesc());
        }
        resultMap.put("user", result);
        return resultMap;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Map<String, Object> addUser(TUser user) {
        Map<String, Object> resultMap = Maps.newHashMap();
        user.setPassword(MD5Util.MD5Encrypt(user.getPassword()));
        Integer r = tUserMapper.insertSelective(user);
        if (ObjectUtils.isNullOrEmpty(r)) {
            throw new CustomException(CustomErrorEnum.ERROR_CODE_341010.getErrorCode(),
                    CustomErrorEnum.ERROR_CODE_341010.getErrorDesc());
        }
        TUser result = tUserMapper.selectByPrimaryKey(user.getId());
        if (ObjectUtils.isNullOrEmpty(result)) {
            throw new CustomException(CustomErrorEnum.ERROR_CODE_341010.getErrorCode(),
                    CustomErrorEnum.ERROR_CODE_341010.getErrorDesc());
        }
        resultMap.put("user", result);
        return resultMap;
    }
}
