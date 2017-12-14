package com.liubowen.socketiomahjong.test;

import com.google.common.collect.Lists;
import com.liubowen.socketiomahjong.entity.Test1;
import com.liubowen.socketiomahjong.entity.Test2;
import com.liubowen.socketiomahjong.entity.Test3;
import com.liubowen.socketiomahjong.mapper.Test1Mapper;
import com.liubowen.socketiomahjong.mapper.Test2Mapper;
import com.liubowen.socketiomahjong.mapper.Test3Mapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author liubowen
 * @date 2017/12/14 13:21
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestMabits {

    @Resource
    private Test1Mapper test1Mapper;

    @Resource
    private Test2Mapper test2Mapper;

    @Resource
    private Test3Mapper test3Mapper;

    @Test
    public void testFindTest1() {
        Test1 test1 = this.test1Mapper.findTest1ById(5);
        log.info("test1 : {}", test1.toString());
    }

    @Test
    public void testSaveTest1() {
        Test2 test2 = new Test2("刘德华");
        List<Test3> test3s = Lists.newArrayList();
        Test3 test31 = new Test3("test31");
        Test3 test32 = new Test3("test32");
        Test3 test33 = new Test3("test33");
        Test3 test34 = new Test3("test34");
        Test3 test35 = new Test3("test35");
        test3s.add(test31);
        test3s.add(test32);
        test3s.add(test33);
        test3s.add(test34);
        test3s.add(test35);
        Test1 test1 = new Test1("张三2", Lists.newArrayList(11, 22, 333, 44), test2, test3s);
        this.test1Mapper.insertUseGeneratedKeys(test1);
        // this.test1Mapper.updateByPrimaryKey(test1);
        log.info("test1 : {}", test1.toString());
    }

    @Test
    public void testFindTest2() {
        Test2 test2 = this.test2Mapper.selectByPrimaryKey(1);
        log.info("test2 : {}", test2.toString());

    }

    @Test
    public void testFindTest3() {
        Test3 test3 = this.test3Mapper.selectByPrimaryKey(1);
        log.info("test3 : {}", test3.toString());

    }

    @Test
    public void testFindTest3s() {
        List<Test3> test3s = this.test3Mapper.selectByTest1Id(1);
        log.info("test3s : {}", Arrays.toString(test3s.toArray()));

    }
}
