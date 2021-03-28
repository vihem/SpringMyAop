package com.easue.test;

import com.easue.config.AppConfig;
import com.easue.dao.IndexDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.getBean(IndexDao.class).query();
        context.getBean(IndexDao.class).query2();

    }
}
