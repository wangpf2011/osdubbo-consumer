package com.wf.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description: Proxy+InvocationHandler动态代理类
 *
 * @version: v1.0.0
 * @author: wangpf
 * @date: 2018年12月1日 下午6:47:54 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年12月1日     wang           v1.0.0               修改原因
 */
public class DynamicProxy {
	public static void main(String[] args) throws Exception {
        // 被代理对象，其中Subject为接口，SubjectImpl为接口实现类
        Subject target = new SubjectImpl();
        // 动态生成的代理对象
        // target.getClass().getInterfaces()和Java的反射机制有关，它能够获得这个对象所实现的接口
        Subject porxy = (Subject)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
        	@Override
        	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        		System.out.println("方法调用前~~");
        		Object temp = method.invoke(target, args);
        		System.out.println("方法调用后~~");
                return temp;
        	}
        });
        // 通过代理对象执行方法say
        porxy.say("wangpf", 20);
    }
}
