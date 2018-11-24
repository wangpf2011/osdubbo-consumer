package com.wf.dubbo.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: JWT单点登录Session跨域--注解Anonymous
 *
 * @version: v1.0.0
 * @author: wangpf
 * @date: 2018年11月24日 下午10:21:46 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年11月24日     wang           v1.0.0               修改原因
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anonymous {

}
