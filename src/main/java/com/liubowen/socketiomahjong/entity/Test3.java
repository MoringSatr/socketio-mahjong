package com.liubowen.socketiomahjong.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author liubowen
 * @date 2017/12/14 13:07
 * @description
 */
@Data
@Table(name = "test_3")
public class Test3 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "test_1_id")
    // @JoinColumn(name = "test_1_id")
    private int test1Id;

    @Column(name = "sex")
    private String sex;

    public Test3(String sex) {
        this.sex = sex;
    }
}
