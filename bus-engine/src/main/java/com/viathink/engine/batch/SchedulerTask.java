package com.viathink.engine.batch;

import com.viathink.core.batch.scheduler.service.SchedulerTaskService;
import com.viathink.core.batch.service.WatchdogService;
import com.viathink.core.business.gene.service.HolidayDayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class SchedulerTask {
    private Logger logger = LoggerFactory.getLogger(SchedulerTask.class);

    private final SchedulerTaskService geneSchedulerTaskService;

    private final WatchdogService watchdogService;
    private final HolidayDayService holidayDayService;

    @Autowired
    public SchedulerTask(WatchdogService watchdogService, HolidayDayService holidayDayService, SchedulerTaskService geneSchedulerTaskService) {
        this.watchdogService = watchdogService;
        this.holidayDayService = holidayDayService;
        this.geneSchedulerTaskService = geneSchedulerTaskService;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    //@Scheduled(cron = "0 0/10 * * * ?")
    public void task(){
        logger.info("SchedulerTask 定时任务执行开始...");
        geneSchedulerTaskService.task();
        watchdogService.handle();
        logger.info("SchedulerTask 定时任务执行结束...");
    }

    @Scheduled(cron = "0 1 0 1 1 ?")
    public void getHolidayTask(){
        //获取当前年份
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        holidayDayService.addHolidays(year);
    }

}
