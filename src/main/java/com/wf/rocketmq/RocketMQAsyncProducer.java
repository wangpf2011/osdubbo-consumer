package com.wf.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 异步发送
 *
 * @version: v1.0.0
 * @author: wangpf
 * @date: 2019年5月20日 上午9:40:04 
 */
public class RocketMQAsyncProducer {
	public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("evcgjx_camera");
        // Specify name server addresses.
        producer.setNamesrvAddr("101.132.32.50:9876");
        //Launch the instance.
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i = 0; i < 100; i++) {
                final int index = i;
                //Create a message instance, specifying topic, tag and message body.
                Message msg = new Message("lnintmqtopic",
                    "TagA",
                    "OrderID188",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                    }
                    @Override
                    public void onException(Throwable e) {
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                    }
                });
        }
        //Shut down once the producer instance is not longer in use.
        //producer.shutdown();
    }
}
