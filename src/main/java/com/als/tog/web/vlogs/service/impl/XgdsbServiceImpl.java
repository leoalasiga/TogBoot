package com.als.tog.web.vlogs.service.impl;

import cn.hutool.core.util.StrUtil;
import com.als.tog.web.vlogs.entity.TagsInfo;
import com.als.tog.web.vlogs.entity.UserInfo;
import com.als.tog.web.vlogs.entity.Xgdsb;
import com.als.tog.web.vlogs.form.AddTagForm;
import com.als.tog.web.vlogs.form.QueryVlogsForm;
import com.als.tog.web.vlogs.form.VlogForm;
import com.als.tog.web.vlogs.mapper.TagsMapper;
import com.als.tog.web.vlogs.mapper.UserInfoMapper;
import com.als.tog.web.vlogs.mapper.XgdsbMapper;
import com.als.tog.web.vlogs.service.XgdsbService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

/**
 * @author dkw
 * @since 2024年04月08日
 */
@Service
@Slf4j
public class XgdsbServiceImpl extends ServiceImpl<XgdsbMapper, Xgdsb> implements XgdsbService {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private XgdsbMapper xgdsbMapper;

    public XgdsbServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public IPage<Xgdsb> QueryList(QueryVlogsForm form) {
        LambdaQueryWrapper<Xgdsb> wrapper = new LambdaQueryWrapper();
        Page<Xgdsb> page=new Page<>(form.getPageIndex(), form.getPageSize());
        if(!form.getTitle().isEmpty()){
            wrapper.like(Xgdsb::getTitle, form.getTitle());
        }
        if(!form.getSummary().isEmpty()){
            wrapper.like(Xgdsb::getSummary, form.getSummary());
        }
        if(StrUtil.isNotBlank(form.getUserId())){
            wrapper.eq(Xgdsb::getUserId,form.getUserId());
        }
        if(StrUtil.isNotBlank(form.getTagType())){
            return xgdsbMapper.selectByType(page,form);
        } else {
            wrapper.orderByDesc(Xgdsb::getCreateTime);
            return baseMapper.selectPage(page, wrapper);
        }

    }

    @Override
    public void AddVlog(VlogForm form){
//        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(UserInfo::getId,form.getUserId());
//        UserInfo userInfo = userInfoMapper.selectOne(lambdaQueryWrapper);
        Xgdsb xgdsb = new Xgdsb();
        LocalDateTime now = LocalDateTime.now();
        xgdsb.setTitle(form.getTitle());
        xgdsb.setSummary(form.getSummary());
        xgdsb.setContent(form.getContent());
        xgdsb.setCreateTime(now);
        xgdsb.setAuthor(form.getAuthor());
        xgdsb.setUserId(form.getUserId());
        xgdsb.setTagTypes(form.getTagTypes());
        saveOrUpdate(xgdsb);
    }

    @Override
    public void DeleteVlog(String id){
        LambdaQueryWrapper<Xgdsb> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Xgdsb::getId,id);
        baseMapper.delete(lambdaQueryWrapper);
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
