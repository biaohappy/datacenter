package com.jsure.datacenter.controller.user;

import com.google.common.collect.Maps;
import com.jsure.datacenter.constant.CustomConstant;
import com.jsure.datacenter.controller.base.BaseController;
import com.jsure.datacenter.exception.CustomException;
import com.jsure.datacenter.model.entity.TUser;
import com.jsure.datacenter.model.enums.CustomErrorEnum;
import com.jsure.datacenter.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/5/21
 * @Time: 14:49
 * I am a Code Man -_-!
 */
@Slf4j
@RestController
@Api(description = "用户登录注册")
@RequestMapping(value = "api/user/")
public class LoginTokenController extends BaseController {

    @Autowired
    private TokenService tokenService;

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @ApiOperation(value="登录", notes="根据用户名和密码登录，返回token")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Map<String, Object> login(String username, String password) {
        log.info("call login, {}, URI:{}", requestParamToString(request), request.getRequestURI());
        Map<String, Object> result = Maps.newHashMap();
        try {
            result = tokenService.login(username, password);
            log.info("success to login, RESULT:{}", result);
            return successData(CustomConstant.SYS_LOGIN_SUCCESS, result);
        } catch (CustomException e) {
            log.error("failed to login, RESULT:{},cause:{}", result, e);
            return failedData(e.getCode(), e.getMessage(), result);
        } catch (Exception e) {
            log.error("failed to login, RESULT:{},cause:{}", result, e);
            return failedData(CustomErrorEnum.ERROR_CODE_341FFF.getErrorCode(), CustomErrorEnum.ERROR_CODE_341FFF.getErrorDesc(), result);
        }
    }

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @ApiOperation(value="根据用户名查询用户信息", notes="根据用户名查询用户信息")
    @RequestMapping(value = "userInfo" ,method = RequestMethod.GET)
    public Map<String, Object> findUserByUserName(@RequestParam String username) {
        log.info("call findUserByUserName, {}, URI:{}", requestParamToString(request), request.getRequestURI());
        Map<String, Object> result = Maps.newHashMap();
        try {
            checkShiroPermission("find");
            TUser user = tokenService.findUserByUserName(username);
            result.put("user", user);
            log.info("success to findUserByUserName, RESULT:{}", result);
            return successData(CustomConstant.USER_INFO_SUCCESS, result);
        } catch (CustomException e) {
            log.error("failed to findUserByUserName, RESULT:{},cause:{}", result, e);
            return failedData(e.getCode(), e.getMessage(), result);
        } catch (Exception e) {
            log.error("failed to findUserByUserName, RESULT:{},cause:{}", result, e);
            return failedData(CustomErrorEnum.ERROR_CODE_341FFF.getErrorCode(), CustomErrorEnum.ERROR_CODE_341FFF.getErrorDesc(), result);
        }
    }

    /**
     * 修改用户资料
     * @param user
     * @return
     */
    @ApiOperation(value="修改用户资料", notes="根据用户资料修改信息，返回修改后的用户信息")
    @RequestMapping(value = "user" ,method = RequestMethod.PUT)
    public Map<String, Object> updateUser(@RequestBody TUser user) {
        log.info("call updateUserById, {}, URI:{}", requestParamToString(request), request.getRequestURI());
        Map<String, Object> result = Maps.newHashMap();
        try {
            checkShiroPermission("update");
            Map<String, Object> userInfo = tokenService.updateUser(user);
            result.put("user", userInfo);
            log.info("success to updateUserById, RESULT:{}", result);
            return successData(CustomConstant.USER_INFO_SUCCESS, result);
        } catch (CustomException e) {
            log.error("failed to updateUserById, RESULT:{},cause:{}", result, e);
            return failedData(e.getCode(), e.getMessage(), result);
        } catch (Exception e) {
            log.error("failed to updateUserById, RESULT:{},cause:{}", result, e);
            return failedData(CustomErrorEnum.ERROR_CODE_341FFF.getErrorCode(), CustomErrorEnum.ERROR_CODE_341FFF.getErrorDesc(), result);
        }
    }

}
