package com.als.tog.web.vlogs.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dkw
 */
@Data
public class LoginForm implements Serializable {
    private static final long serialVersionUID = -4163802062959713344L;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("密码")
    private String passWord;
}
