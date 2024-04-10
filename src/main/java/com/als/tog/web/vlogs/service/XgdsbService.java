package com.als.tog.web.vlogs.service;

import com.als.tog.web.vlogs.entity.Xgdsb;
import com.als.tog.web.vlogs.form.VlogForm;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dkw001
 * @since 2024年04月08日
 */
public interface XgdsbService extends IService<Xgdsb> {

    List QueryList(VlogForm form);

    void AddVlog(VlogForm form);

    void DeleteVlog(VlogForm form);
    void UpdateVlog(VlogForm form);
}
