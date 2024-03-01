package com.als.tog;

import com.als.tog.samples.mapper.SampleUserMapper;
import com.als.tog.samples.pojo.SampleUser;
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
    private SampleUserMapper sampleMapper;

    @Test
    void testInsert() {
        SampleUser sample = new SampleUser();
        sample.setAge(6);
        sample.setName("leos");
        sample.setEmail("aaaa@123.com");
        sampleMapper.insert(sample);
        Assert.notNull(sample.getId(), "");
    }
}
