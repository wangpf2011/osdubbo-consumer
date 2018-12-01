package com.wf.dubbo.proxy;

/**
 * @Description: 被代理对象
 *
 * @version: v1.0.0
 * @author: wangpf
 * @date: 2018年12月1日 下午11:05:51 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年12月1日     wang           v1.0.0               修改原因
 */
public class SubjectImpl implements Subject {

	@Override
	public void say(String name, int age) {
		String ret = "name="+name+",age="+age;
		System.out.println(ret);
	}

}
