package com.als.tog.web.vlogs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dkw
 */
@Data
@TableName("tagsInfo")
public class TagsInfo {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("type")
    private String type;

    @TableField("tag")
    private String tag;
}
