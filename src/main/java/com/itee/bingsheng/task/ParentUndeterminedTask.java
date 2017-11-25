package com.itee.bingsheng.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Component
public class ParentUndeterminedTask {

    private Logger logger = Logger.getLogger(ParentUndeterminedTask.class.getName());


    @Scheduled(cron = "0 0 2 * * ?")
    public void prepareTeacherRewardOfHour() {
        try {



        } catch (Exception e) {
            e.printStackTrace();
        }
    }




















    /**
     * 定时任务处理未确定学生状态的家长
     */
    public synchronized void autoDetermine() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
