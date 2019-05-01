package com.viathink.engine.common.config;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Data
@Configuration
public class MqConfig {

    @Value("${mq.config.ProducerId}")
    public String producerId;

    @Value("${mq.config.ConsumerId}")
    public String consumerId;

    @Value("${mq.config.AccessKey}")
    public String accessKey;

    @Value("${mq.config.SecretKey}")
    public String secretKey;

    @Value("${mq.config.Topic}")
    public String topic;

    @Value("${mq.config.ONSAddr}")
    public String onsAddr;

    @Value("${mq.config.sendMsgTimeoutMillis}")
    public String sendMsgTimeoutMillis;

    @Value("${mq.config.suspendTimeMillis}")
    public String suspendTimeMillis;

    @Value("${mq.config.maxReconsumeTimes}")
    public String maxReconsumeTimes;

    //生产者的配置，创建生产者
    @Bean
    public OrderProducer orderProducer (){
        Properties producerProperties = new Properties();
        producerProperties.setProperty(PropertyKeyConst.ProducerId, producerId);
        producerProperties.setProperty(PropertyKeyConst.AccessKey, accessKey);
        producerProperties.setProperty(PropertyKeyConst.SecretKey, secretKey);
        producerProperties.setProperty(PropertyKeyConst.ONSAddr, onsAddr);
        producerProperties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, sendMsgTimeoutMillis);
        return ONSFactory.createOrderProducer(producerProperties);
    }

}
