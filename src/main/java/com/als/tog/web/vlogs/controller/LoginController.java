package com.als.tog.web.vlogs.controller;

import com.als.tog.config.ResultUtil;
import com.als.tog.web.vlogs.entity.UserInfo;
import com.als.tog.web.vlogs.entity.Xgdsb;
import com.als.tog.web.vlogs.form.LoginForm;
import com.als.tog.web.vlogs.service.LoginService;
import com.als.tog.web.vlogs.service.XgdsbService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author dkw
 * @since 2024年04月08日
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Resource
    private LoginService loginService;

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public ResultUtil login(@RequestBody LoginForm form) {
        String token = loginService.login(form);
        return ResultUtil.success(token);
    }

    @ApiOperation("注册接口")
    @PostMapping("/register")
    public ResultUtil register(@RequestBody LoginForm form) {
        loginService.register(form);
        return ResultUtil.success();
    }


    @ApiOperation("获取用户信息")
    @PostMapping("/getUserInfoByToken")
    public ResultUtil getUserInfoByToken(String token) {
        UserInfo userInfo = loginService.getUserInfoByToken(token);
        return ResultUtil.success(userInfo);
    }
}
