package com.als.tog.web.vlogs.service.impl;

import com.als.tog.web.vlogs.entity.Xgdsb;
import com.als.tog.web.vlogs.form.TestForm;
import com.als.tog.web.vlogs.mapper.XgdsbMapper;
import com.als.tog.web.vlogs.service.XgdsbService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dkw001
 * @since 2024年04月08日
 */
@Service
@Slf4j
public class XgdsbServiceImpl extends ServiceImpl<XgdsbMapper, Xgdsb> implements XgdsbService {

    @Override
    public void test(TestForm form) {
        log.info("方法开始————————————————————————————");
        log.info("我叫{}————————————————————————————",form.getName());
        Xgdsb xgdsb = baseMapper.selectById(1L);
        System.out.println(xgdsb);
        log.info("方法结束————————————————————————————");
    }
}
