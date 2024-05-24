package com.als.tog.web.vlogs.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dkw
 */
@Data
public class AddTagForm implements Serializable {

    private static final long serialVersionUID = -1542386908255169194L;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("标签")
    private String tag;
}
