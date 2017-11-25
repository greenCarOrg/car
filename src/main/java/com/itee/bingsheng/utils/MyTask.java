package com.itee.bingsheng.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by abc on 2016/7/11.
 */
public class MyTask {

    Timer timer;

    private String orderId;

    private int sccId;
    private String sccUuid;
    private String studentUuid;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getSccId() {
        return sccId;
    }

    public void setSccId(int sccId) {
        this.sccId = sccId;
    }

    public String getSccUuid() {
        return sccUuid;
    }

    public void setSccUuid(String sccUuid) {
        this.sccUuid = sccUuid;
    }

    public String getStudentUuid() {
        return studentUuid;
    }

    public void setStudentUuid(String studentUuid) {
        this.studentUuid = studentUuid;
    }

    public MyTask(String orderId, int sccId) {
        this.orderId = orderId;
        this.sccId = sccId;
        timer = new Timer();
        timer.schedule(new RemindTask(), 20 * 1000);
    }

    public MyTask(int sccId,String sccUuid,String studentUuid) {
        this.sccId = sccId;
        this.sccUuid = sccUuid;
        this.studentUuid = studentUuid;
        timer = new Timer();
        timer.schedule(new RemindTask(), 20 * 1000);
    }

    class RemindTask extends TimerTask {
        @Override
        public void run() {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                timer.cancel();
            }
        }
    }
}
