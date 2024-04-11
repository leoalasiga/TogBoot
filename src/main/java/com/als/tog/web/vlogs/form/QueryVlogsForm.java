package com.als.tog.web.vlogs.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author dkw
 */
@Data
@ApiModel
public class QueryVlogsForm implements Serializable {

    private static final long serialVersionUID = 2880474257034337021L;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("摘要")
    private String summary;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
