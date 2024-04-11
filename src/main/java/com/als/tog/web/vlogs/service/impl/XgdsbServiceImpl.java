package com.als.tog.web.vlogs.service.impl;

import com.als.tog.web.vlogs.entity.Xgdsb;
import com.als.tog.web.vlogs.form.QueryVlogsForm;
import com.als.tog.web.vlogs.form.VlogForm;
import com.als.tog.web.vlogs.mapper.XgdsbMapper;
import com.als.tog.web.vlogs.service.XgdsbService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dkw
 * @since 2024年04月08日
 */
@Service
@Slf4j
public class XgdsbServiceImpl extends ServiceImpl<XgdsbMapper, Xgdsb> implements XgdsbService {

    @Override
    public List<Xgdsb> QueryList(QueryVlogsForm form) {
        LambdaQueryWrapper<Xgdsb> wrapper = new LambdaQueryWrapper();
        if(!form.getTitle().isEmpty()){
            wrapper.like(Xgdsb::getTitle, form.getTitle());
        }
        if(!form.getSummary().isEmpty()){
            wrapper.like(Xgdsb::getSummary, form.getSummary());
        }
        wrapper.orderByDesc(Xgdsb::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void AddVlog(VlogForm form){
        Xgdsb xgdsb = new Xgdsb();
        LocalDateTime now = LocalDateTime.now();
        xgdsb.setTitle(form.getTitle());
        xgdsb.setSummary(form.getSummary());
        xgdsb.setContent(form.getContent());
        xgdsb.setCreateTime(now);
        saveOrUpdate(xgdsb);
    }

    @Override
    public void DeleteVlog(VlogForm form){
        removeById(form.getId());
    }


    @Override
    public void UpdateVlog(VlogForm form){
        Xgdsb xgdsb = new Xgdsb();
        xgdsb.setId(form.getId());
        xgdsb.setTitle(form.getTitle());
        xgdsb.setSummary(form.getSummary());
        xgdsb.setContent(form.getContent());
        saveOrUpdate(xgdsb);
    }
}
