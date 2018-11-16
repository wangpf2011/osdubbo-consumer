package com.wf.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerDubboTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer-p2p.xml");
        context.start();
        UserServiceP2P userServiceP2P = (UserServiceP2P)context.getBean("userServiceP2P"); // 获取远程服务代理
        String hello = userServiceP2P.sayHello("world"); // 执行远程方法
        System.out.println( hello ); // 显示调用结果
	}
}
