package com.als.tog.web.vlogs.service;

import com.als.tog.web.vlogs.entity.TagsInfo;
import com.als.tog.web.vlogs.form.AddTagForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dkw
 */
public interface TagsService extends IService<TagsInfo> {

    List QueryTags();

    void addTags(AddTagForm addTagForm);
}
