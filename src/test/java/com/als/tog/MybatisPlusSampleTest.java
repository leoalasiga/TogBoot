package com.als.tog;

import com.als.tog.samples.mapper.UserMapper;
import com.als.tog.samples.pojo.User;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

/**
 * @description:
 * @author: liujiajie
 * @date: 2024/2/29 14:39
 */
@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MybatisPlusSampleTest {
    @Autowired
    private UserMapper sampleMapper;

    @Test
    void testInsert() {
        User sample = new User();
        sample.setAge(6);
        sample.setName("leos");
        sample.setEmail("aaaa@123.com");
        sampleMapper.insert(sample);
        Assert.notNull(sample.getId(), "");
    }
}
