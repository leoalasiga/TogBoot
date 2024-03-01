package com.als.tog.samples.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`sample_user`")
public class SampleUser {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}