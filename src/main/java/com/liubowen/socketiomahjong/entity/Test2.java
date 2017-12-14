package com.liubowen.socketiomahjong.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author liubowen
 * @date 2017/12/14 13:07
 * @description
 */
@Data
@Table(name = "test_2")
public class Test2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "habits")
    private String habits;

    public Test2(String habits) {
        this.habits = habits;
    }
}
