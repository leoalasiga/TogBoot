package com.als.tog.web.vlogs.service.impl;

import com.als.tog.web.vlogs.entity.TagsInfo;
import com.als.tog.web.vlogs.form.AddTagForm;
import com.als.tog.web.vlogs.mapper.TagsMapper;
import com.als.tog.web.vlogs.service.TagsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dkw
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, TagsInfo> implements TagsService {

    @Override
    public List QueryTags(){
        List list = this.list();
        return list;
    }

    @Override
    public void addTags(AddTagForm addTagForm){
        TagsInfo tagsInfo = new TagsInfo();
        tagsInfo.setTag(addTagForm.getTag());
        tagsInfo.setType(addTagForm.getType());
        saveOrUpdate(tagsInfo);
    }
}
