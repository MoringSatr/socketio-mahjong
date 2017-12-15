package com.liubowen.socketiomahjong.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author liubowen
 * @date 2017/12/14 13:07
 * @description
 */
@Data
@Table(name = "test_1")
public class Test1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "test_2_id")
    private int test2Id;

//    @Column(name = "aa")
//    private List aa;
//
//    @Transient
//    private Test2 test2;
//
//    @Transient
//    private List<Test3> test3s;

    public Test1(String name, List aa, Test2 test2, List<Test3> test3s) {
        this.name = name;
//        this.aa = aa;
//        this.test2Id = test2.getId();
//        this.test2 = test2;
//        this.test3s = test3s;
    }
}
