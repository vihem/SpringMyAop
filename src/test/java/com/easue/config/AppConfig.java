package com.easue.config;

import com.easue.annotation.EnableAop;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.easue")
@EnableAop //´ò¿ª aop×¢½â
public class AppConfig {
}
