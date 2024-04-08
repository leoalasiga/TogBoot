package com.als.tog.web.vlogs.service;

import com.als.tog.web.vlogs.entity.Xgdsb;
import com.als.tog.web.vlogs.form.TestForm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dkw001
 * @since 2024年04月08日
 */
public interface XgdsbService extends IService<Xgdsb> {
    void test(TestForm form);

}
