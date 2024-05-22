package com.als.tog.web.vlogs.service;

import com.als.tog.config.ResultUtil;
import com.als.tog.web.vlogs.entity.UserInfo;
import com.als.tog.web.vlogs.form.LoginForm;
import com.als.tog.web.vlogs.form.VlogForm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author dkw
 */
public interface LoginService extends IService<UserInfo> {
    ResultUtil login(LoginForm form);
    void register(LoginForm form);
}
