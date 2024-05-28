package com.als.tog.web.vlogs.service;

import com.als.tog.web.vlogs.entity.Xgdsb;
import com.als.tog.web.vlogs.form.AddTagForm;
import com.als.tog.web.vlogs.form.QueryVlogsForm;
import com.als.tog.web.vlogs.form.VlogForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

    IPage QueryList(QueryVlogsForm form);

    void AddVlog(VlogForm form);

    void DeleteVlog(String id);

    void UpdateVlog(VlogForm form);
}
