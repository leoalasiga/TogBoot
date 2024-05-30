package com.als.tog.web.vlogs.mapper;

import com.als.tog.web.vlogs.entity.Xgdsb;
import com.als.tog.web.vlogs.form.QueryVlogsForm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author dkw001
 * @since 2024年04月08日
 */
@Mapper
public interface XgdsbMapper extends BaseMapper<Xgdsb> {
    IPage<Xgdsb> selectByType(Page<Xgdsb> page, @Param("form") QueryVlogsForm form);
}
