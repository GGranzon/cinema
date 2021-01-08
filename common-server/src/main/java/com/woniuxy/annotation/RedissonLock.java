package com.woniuxy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonLock {
    String lockKey();

    int timeout() default 20;

    int lockTimeout() default 20;

    String prefix() default "";

    String suffix() default "";

    int index() default 0;

    String fieldName() default "";

}
