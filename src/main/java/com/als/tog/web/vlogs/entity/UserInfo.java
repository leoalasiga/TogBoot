package com.als.tog.web.vlogs.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dkw001
 * @since 2024年04月08日
 */
@Data
@TableName("userInfo")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -4644066405545577485L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("account")
    private String account;

    @TableField("passWord")
    private String passWord;

}
