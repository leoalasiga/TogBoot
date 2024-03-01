package com.als.tog;

import com.als.tog.samples.mapper.SampleUserMapper;
import com.als.tog.samples.pojo.SampleUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @description:
 * @author: liujiajie
 * @date: 2024/2/29 14:16
 */
@SpringBootTest
public class SampleTest {

    @Autowired
    private SampleUserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<SampleUser> userList = userMapper.selectList(null);
        Assert.isTrue(6 == userList.size(), "");
        userList.forEach(System.out::println);
    }

}
