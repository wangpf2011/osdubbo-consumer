package com.wf.rocketmq.test;

public class RocketMQTest {

	public static void main(String[] args) {
		RocketMQProducer.getInstance().sendMessage("lnintmqtopic", "wwww");
	}
}
