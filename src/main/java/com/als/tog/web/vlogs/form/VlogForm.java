package com.als.tog.web.vlogs.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dkw
 */
@Data
@ApiModel
public class VlogForm implements Serializable {

    private static final long serialVersionUID = 7352547705728577742L;

    @ApiModelProperty("字典名称")
    private String key;

    @ApiModelProperty("字典名称")
    private String value;

    @ApiModelProperty("字典名称")
    private Integer status;
}
