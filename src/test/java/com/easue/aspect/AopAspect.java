package com.easue.aspect;

import com.easue.annotation.AfterEasue;
import com.easue.annotation.AopJ;
import com.easue.annotation.AroundEasue;
import com.easue.annotation.BeforeEasue;
import org.springframework.stereotype.Component;

@AopJ
@Component
public class AopAspect {
    @BeforeEasue("com.easue.dao")
    public void testBefore(){
        System.out.println("before   ------------------  ���Գɹ���");
    }
    @AfterEasue("com.easue.dao")
    public void testAfter(){
        System.out.println("after     ------------------  ���Գɹ���");
    }
    @AroundEasue("com.easue.dao")
    public void testAround(){
        System.out.println("around   ------------------  ���Գɹ���");
    }

}
