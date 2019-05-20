package com.wf.rocketmq.test;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocketMQProducer {
	
	private Logger logger = LoggerFactory.getLogger(RocketMQProducer.class);

	private static RocketMQProducer rocketMQProducer;
	
	private static DefaultMQProducer defaultMQProducer;
	private static String producerGroup;
	private static String namesrvAddr;
	
    public static RocketMQProducer getInstance() {
    	if(rocketMQProducer == null) {
    		rocketMQProducer = new RocketMQProducer();
    	}
    	
    	if(defaultMQProducer == null) {
    		try {
    			producerGroup = "evcgjx_camera"; //Global.getConfig("rocketmq.producer.group");
    			namesrvAddr = "101.132.32.50:9876"; //Global.getConfig("rocketmq.namesrvAddr");
    			defaultMQProducer = new DefaultMQProducer(producerGroup);
    			defaultMQProducer.setNamesrvAddr(namesrvAddr);
    			defaultMQProducer.start();
    		} catch (MQClientException e) {
    			if(defaultMQProducer != null) {
    				defaultMQProducer.shutdown();
    			}
    			defaultMQProducer = null;
    			rocketMQProducer = null;
    			e.printStackTrace();
    		}
    	}
    	
    	return rocketMQProducer;
    }
    
    public void sendMessage(String topic, String content) {
    	if(defaultMQProducer != null) {
    		try {
				Message msg = new Message(topic /* Topic */,
						"evcgjx" /* Tag */,
						content.getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
						);
				//Call send message to deliver message to one of brokers.
				SendResult sendResult = defaultMQProducer.send(msg);
				logger.info("发送消息："+sendResult);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
    	}
    }

    public void destroy() {
		if(defaultMQProducer != null) {
			defaultMQProducer.shutdown();
		}
	}
}
