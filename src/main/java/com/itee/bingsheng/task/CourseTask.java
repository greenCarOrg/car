package com.itee.bingsheng.task;

import com.itee.bingsheng.service.imp.ComboboxService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


@Component
public class CourseTask {

    protected final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getClass());






















    @Scheduled(cron = "0 0 1,3,5,7,9,11,13,15,17,19,21,23 * * ? ")
    @Scheduled(cron = "* 0/10 * * * ? ")
    public void consumeCourses() {

    }

    /**
     * 生成平日班的学生课时表
     */
    @Scheduled(fixedRate = 1000*60*30)
    public void consumeMonthCourses() {
    }

    /**
     * 定时计算课程预收
     */
    @Scheduled(cron = "0 0 13 * * ? ")
    public void prepareConsumeCourses() {

    }

    /**
     * 获取营销中学的数据，每个月的3号生成
     */
    @Scheduled(cron = "0 0 0 3 * ?")
    public void getStudentInfo() {

    }

    /**
     * 月初按月计算平日班的费用
     * 每个月1号0时0分0秒执行
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void countMonthCourses() {
    }

    /**
     * 更新学生的就读状态
     */
    @Scheduled(cron = "* 0/59 * * * ? ")
    public void updateStudentState() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将余额不足学生信息推送给壹家教
     */
    @Scheduled(cron = "* 0/10 * * * ? ")
    public void pushBalanceNoEnoughStudentInfoToYjj() {

    }



    /**
     * 每月定时计算老师月度课收课酬
     * 每天12点触发
     */
    @Scheduled(fixedRate = 1000*60*60*8)
    public void prepareTeacherRewardResult() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 距离7天、3天分别于每天14:10通过站内信的方式通知班主任和主任复核待处理的课次
     * 每月定时发送给有待复核课次的老师、主任（0定时发送；1手动触发）
	 * 每天下午14:10点触发
     * @param
     */
	@Scheduled(cron = "10 10 14 * * ?")
    public synchronized void sendMailConsumeCurriculumToTeacher() {
        try {
			int count = ((int)(Math.random()*90)+10)*1000;
			Thread.sleep(count);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 过了上课时间，精品课下架，全日制班级学生课次自动确认出勤并复核
     */
    @Scheduled(cron = "* 0/10 * * * ? ")
    public void offQualityCourseProduct() {
        //精品课下架
        try {
           
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 每日凌晨2点0分统一自动确认学生课次
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void confirmStudentCurriculum() {

    }
    /**
     * 每日凌晨2点0分统自动发送站内信（系统自动确认的课次）
     * 发送对象：班主任、老师、学生
     */
    @Scheduled(cron = "0 10 14 * * ?")
    public void sendCurriculumStackLetter() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 每天00:00记录当前ERP账户
     */
    @Scheduled(cron = "0 10 00 * * ?")
    public void studentWalletRecordTask() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
