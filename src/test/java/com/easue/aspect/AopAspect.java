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
        System.out.println("before   ------------------  测试成功！");
    }
    @AfterEasue("com.easue.dao")
    public void testAfter(){
        System.out.println("after     ------------------  测试成功！");
    }
    @AroundEasue("com.easue.dao")
    public void testAround(){
        System.out.println("around   ------------------  测试成功！");
    }

}
