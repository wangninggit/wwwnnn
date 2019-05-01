package com.viathink.engine.component;

import com.viathink.core.batch.service.WatchdogService;
import com.viathink.core.business.gene.mapper.ErrorLogMapper;
import com.viathink.core.business.service.CustomerEventService;
import com.viathink.core.collection.service.CollectionService;
import com.viathink.core.log.service.GeneLogService;
import com.viathink.core.monitor.service.MonitorService;
import com.viathink.engine.collection.runnable.ProducerRunnable;
import com.viathink.engine.common.config.MqConfig;
import com.viathink.engine.mq.MqProducer;
import com.viathink.engine.stream.runnable.StreamConsumerRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ThreadManager implements CommandLineRunner {

    //生产者消费者线程实例化
    private final ReleaseManager releaseManager;
    private final MqConfig mqConfig;
    private final MqProducer mqProducer;
    private final CollectionService collectionService;
    private final GeneLogService geneLogService;
    private final WatchdogService watchdogService;
    private final MonitorService monitorService;
    private final ErrorLogMapper errorLogMapper;
    private final CustomerEventService customerEventServiceImpl;

    @Autowired
    public ThreadManager(CollectionService collectionService, ReleaseManager releaseManager, MqConfig mqConfig, MqProducer mqProducer, GeneLogService geneLogService,  WatchdogService watchdogService,  MonitorService monitorService, ErrorLogMapper errorLogMapper, CustomerEventService customerEventServiceImpl) {
        this.collectionService = collectionService;
        this.releaseManager = releaseManager;
        this.mqConfig = mqConfig;
        this.mqProducer = mqProducer;
        this.geneLogService = geneLogService;
        this.watchdogService = watchdogService;
        this.monitorService = monitorService;
        this.errorLogMapper = errorLogMapper;
        this.customerEventServiceImpl = customerEventServiceImpl;
    }

    @Override
    public void run(String... args) {

        // 消费者线程
        StreamConsumerRunnable streamConsumerRunnable = new StreamConsumerRunnable(
                mqConfig.getTopic(),
                "*",
                mqConfig.getConsumerId(),
                mqConfig,
                watchdogService,
                geneLogService,
                monitorService,
                errorLogMapper,
                customerEventServiceImpl
        );
        new Thread(streamConsumerRunnable).start();
        // 生产者线程
        ProducerRunnable producerRunnable = new ProducerRunnable(releaseManager, mqConfig.getTopic(), mqProducer, mqConfig, collectionService);
        new Thread(producerRunnable).start();
    }


}
