package com.als.tog.web.vlogs.service.impl;

import com.als.tog.utils.ExceptionUtils;
import com.als.tog.utils.JwtUtils;
import com.als.tog.web.vlogs.entity.UserInfo;
import com.als.tog.web.vlogs.form.LoginForm;
import com.als.tog.web.vlogs.mapper.UserInfoMapper;
import com.als.tog.web.vlogs.service.LoginService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * @author dkw
 */
@Service
public class LoginServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements LoginService {


    // 将用户信息存储
    @Override
    public String login(LoginForm form) {
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getAccount, form.getAccount());
        UserInfo userInfo = baseMapper.selectOne(lambdaQueryWrapper);
        String jwtToken = "";
        if(userInfo == null){
            ExceptionUtils.throwBusinessException("用户不存在");
        } else {
            Map<String, Object> map = new HashMap<>(5);
            map.put("id", userInfo.getId()+"");
            map.put("account", userInfo.getAccount());
            if(userInfo!=null && userInfo.getPassWord().equals(form.getPassWord())) {
                jwtToken = JwtUtils.generateToken(map);
            }
            if (userInfo!=null && !userInfo.getPassWord().equals(form.getPassWord())){
                ExceptionUtils.throwBusinessException("账号或密码不正确");
            }
        }
        return jwtToken;
    }

    @Override
    public void register(LoginForm form) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAccount(form.getAccount());
        userInfo.setPassWord(form.getPassWord());
        saveOrUpdate(userInfo);
    }

    @Override
    public UserInfo getUserInfoByToken(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = null;
        try {
            claims = JwtUtils.parseToken(token);
        }catch (Exception e){
            ExceptionUtils.throwBusinessException("登录失效,请重新登录");
        }
        UserInfo userInfo = new UserInfo();
        Long id = Long.valueOf(claims.get("id")+"");
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(UserInfo::getId , id);
        userInfo = baseMapper.selectOne(lambdaQueryWrapper);
        return userInfo;
    }

    @Override
    public UserInfo getUserInfoByUserName(String userName) {
        UserInfo userInfo = new UserInfo();
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(UserInfo::getAccount , userName);
        userInfo = baseMapper.selectOne(lambdaQueryWrapper);
        return userInfo;
    }
}
