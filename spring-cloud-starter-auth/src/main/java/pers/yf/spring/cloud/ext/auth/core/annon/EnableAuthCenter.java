package pers.yf.spring.cloud.ext.auth.core.annon;


import org.springframework.context.annotation.Import;
import pers.yf.spring.cloud.ext.auth.config.AuthZuulConfiguation;

import java.lang.annotation.*;

@Import(AuthZuulConfiguation.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableAuthCenter {

}



