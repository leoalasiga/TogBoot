package com.als.tog.web.vlogs.vo;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * @author dkw
 */
public class VlogsVo {
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("摘要")
    private String summary;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
