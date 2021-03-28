package com.easue.dao;

import org.springframework.stereotype.Repository;

@Repository
public class IndexDao {
    public void query(){
        System.out.println("IndexDao.query()");
    }
    public void query2(){
        System.out.println("IndexDao.query2()");
    }
}
