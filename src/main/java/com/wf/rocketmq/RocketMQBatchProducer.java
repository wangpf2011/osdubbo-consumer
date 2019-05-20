package com.wf.rocketmq;

import java.util.ArrayList;
import java.util.List;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 批量消息
 *
 * @version: v1.0.0
 * @author: wangpf
 * @date: 2019年5月20日 上午10:01:37 
 */
public class RocketMQBatchProducer {
	public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("BatchProducerGroup");

        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        List<Message> msgs = new ArrayList<Message>();
        for (int i = 0; i < 1000; i++) {
            try {
                // Create a message instance, specifying topic, tag and message body.
                Message msg = new Message(
                        "batch_send_message_topic" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                msgs.add(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //then you could split the large list into small ones:
        /*ListSplitter splitter = new ListSplitter(msgs);
        while (splitter.hasNext()) {
            try {
                List<Message> listItem = splitter.next();
                producer.send(listItem);
            } catch (Exception e) {
                e.printStackTrace();
                //handle the error
            }
        }*/

        // Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
