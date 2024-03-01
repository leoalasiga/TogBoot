package com.als.tog;

import com.als.tog.samples.mapper.UserMapper;
import com.als.tog.samples.pojo.User;
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
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(6 == userList.size(), "");
        userList.forEach(System.out::println);
    }

}
