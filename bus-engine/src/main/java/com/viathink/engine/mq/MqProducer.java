package com.viathink.engine.mq;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class MqProducer implements InitializingBean,DisposableBean {

    private Logger logger = LoggerFactory.getLogger(MqProducer.class);
    private final OrderProducer orderProducer;

    @Autowired
    public MqProducer(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @Override
    public void afterPropertiesSet() {
        logger.info("生产者初始化");
        orderProducer.start();
    }

    public Boolean sendMessage(Message message,String shardingKey){
        try {
            SendResult sendResult = orderProducer.send(message, shardingKey);
            assert sendResult != null;
            logger.info(new Date() + " Send mq message success! Topic is:" + message.getTopic() + ", tag is: " + message.getTag() + ", msgKey is: " + message.getKey()+ new String(message.getBody()));
            return true;
        } catch (ONSClientException e) {
            e.printStackTrace();
            logger.error("simpleMQProducer send message failed: " + message.getTopic());
            //出现异常意味着发送失败，为了避免消息丢失，建议缓存该消息然后进行重试。
            return false;
        }
    }

    @Override
    public void destroy() {
        logger.info("orderProducer destory...");
        orderProducer.shutdown();
    }

}
