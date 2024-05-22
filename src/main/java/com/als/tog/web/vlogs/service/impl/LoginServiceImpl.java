package com.als.tog.web.vlogs.service.impl;

import com.als.tog.Exception.BusinessException;
import com.als.tog.config.ResultUtil;
import com.als.tog.utils.ExceptionUtils;
import com.als.tog.web.vlogs.entity.UserInfo;
import com.als.tog.web.vlogs.form.LoginForm;
import com.als.tog.web.vlogs.mapper.UserInfoMapper;
import com.als.tog.web.vlogs.service.LoginService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * @author dkw
 */
@Service
public class LoginServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements LoginService {

    @Override
    public ResultUtil login(LoginForm form) {
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getAccount, form.getAccount());
        UserInfo userInfo = baseMapper.selectOne(lambdaQueryWrapper);
        if(userInfo == null){
            ExceptionUtils.throwBusinessException("用户不存在");
        }
        if(userInfo!=null && userInfo.getPassWord().equals(form.getPassWord())) {
            return ResultUtil.success();
        }
        if (userInfo!=null && !userInfo.getPassWord().equals(form.getPassWord())){
            ExceptionUtils.throwBusinessException("账号或密码不正确");
        }
        return ResultUtil.success();
    }

    @Override
    public void register(LoginForm form) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAccount(form.getAccount());
        userInfo.setPassWord(form.getPassWord());
        saveOrUpdate(userInfo);
    }
}
