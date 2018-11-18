package com.wf.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerDubboTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        UserService userService = (UserService)context.getBean("userService"); // 获取远程服务代理
        UserRequest req = new UserRequest();
        req.setName("wpf");
        UserResponse res = userService.sayHello(req); // 执行远程方法
        System.out.println(res.getData()); // 显示调用结果
        
        UserService userService2 = (UserService)context.getBean("userService2"); // 获取远程服务代理
        UserResponse res2 = userService2.sayHello(req); // 执行远程方法
        System.out.println(res2.getData()); // 显示调用结果
	}
}
