package com.wf.dubbo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.rpc.RpcContext;

public class ConsumerDubboTest {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        UserService userService = (UserService)context.getBean("userService"); // 获取远程服务代理
        UserRequest req = new UserRequest();
        req.setName("wangpf");
        UserResponse res = userService.sayHello(req); // 执行远程方法
        System.out.println(res.getData()); // 显示调用结果
        
        //多版本
        UserService userService2 = (UserService)context.getBean("userService2");
        UserResponse res2 = userService2.sayHello(req);
        System.out.println(res2.getData());
        
        //异步调用，只支持dubbo协议
        UserServiceFuture userServiceFuture = (UserServiceFuture)context.getBean("userServiceFuture");
        //此调用会立即返回null
        userServiceFuture.sayHello(req);
        //拿到调用的Future引用，当结果返回后，会被通知和设置到此Future
        Future<UserResponse> future = RpcContext.getContext().getFuture();
        //如果UserResponse已返回，直接拿到返回值，否则线程wait住，等待UserResponse返回后，线程会被notify唤醒
        UserResponse res3 = future.get();
        System.out.println(res3.getData());
	}
}
