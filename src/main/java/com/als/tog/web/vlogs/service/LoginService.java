package com.als.tog.web.vlogs.service;

import com.als.tog.web.vlogs.entity.UserInfo;
import com.als.tog.web.vlogs.form.LoginForm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author dkw
 */
public interface LoginService extends IService<UserInfo> {

    String login(LoginForm form);

    void register(LoginForm form);

    UserInfo getUserInfoByToken(String token);
}
