package com.wf.dubbo.proxy;

/**
 * @Description: 简单的静态代理
 *
 * @version: v1.0.0
 * @author: wangpf
 * @date: 2018年12月1日 下午11:02:54 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年12月1日     wang           v1.0.0               修改原因
 */
public class StaticProxy implements Subject {
	private SubjectImpl subject; // 以真实角色作为代理角色的属性

	public StaticProxy() {
	}

	// 该方法封装了真实对象的say方法
	@Override
	public void say(String name, int age) {
		preRequest();
		if(subject == null) {
			subject = new SubjectImpl();
		}
		subject.say(name, age); // 此处执行真实对象的say方法
		postRequest();
	}

	private void preRequest() {
		System.out.println("方法调用前~~");
	}

	private void postRequest() {
		System.out.println("方法调用后~~");
	}
	
	public static void main(String[] args) {
		Subject sub = new StaticProxy();
        sub.say("wangpf", 20);
	}
}
