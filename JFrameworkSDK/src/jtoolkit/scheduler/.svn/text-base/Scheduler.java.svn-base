package jtoolkit.scheduler;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 调度引擎。
 * <p>调度程序的核心所在。该类提供两种开放的调度接口，二者的区别在于是否允许首次调度，也就是程序
 *    执行的开始是否进行调度。
 *
 * @version 1.0 初始版本
 */
public class Scheduler {

    class SchedulerTimerTask
        extends TimerTask {

        private ScheduleTask schedulerTask;
        private ScheduleIterator iterator;

        public SchedulerTimerTask(ScheduleTask schedulerTask, ScheduleIterator iterator) {
            this.schedulerTask = schedulerTask;
            this.iterator = iterator;
        }

        public void run() {
            schedulerTask.run();
            reschedule(schedulerTask, iterator);
        }
    }

    private final Timer timer = new Timer();
    private Date time;
    private TemporalTaskDispatcher ttDispatcher;
    private boolean isTimerCancel;

    /**
     *
     */
    public Scheduler() {
    }

    /**
     *
     * @param ttDispatcher TemporalTaskDispatcher
     */
    public Scheduler(TemporalTaskDispatcher ttDispatcher) {
        this.ttDispatcher = ttDispatcher;
    }

    /**
     *
     * @return TemporalTaskDispatcher
     */
    public TemporalTaskDispatcher getTTDispatcher() {
        return ttDispatcher;
    }

    /**
     * 取消调度。如果取消则整个调度计划将被取消。
     */
    public void cancel() {
        timer.cancel();
        isTimerCancel = true;
    }

    /**
     * 开始调度，首次不参与调度。
     *
     * @param schedulerTask SchedulerTask    调度任务
     * @param      iterator ScheduleIterator 调度时间迭代器
     */
    public void schedule(ScheduleTask schedulerTask, ScheduleIterator iterator) {
        schedule(schedulerTask, iterator, null);
    }

    /**
     * 开始调度，首次参与调度。
     *
     * @param schedulerTask SchedulerTask    调度任务
     * @param      iterator ScheduleIterator 调度时间迭代器
     * @param      dateTime Date             首次调度时间（当前时间）
     */
    public void schedule(ScheduleTask schedulerTask, ScheduleIterator iterator, Date dateTime) {
        isTimerCancel = false;
        if (dateTime != null) {
            time = dateTime;
        } else {
            time = iterator.next();
        }
        if (time == null) {
            schedulerTask.cancel();
        } else {
            synchronized (schedulerTask.lock) {
                if (schedulerTask.state != ScheduleTask.VIRGIN) {
                    throw new IllegalStateException("任务处于调度中或者已经被取消！");
                }
                schedulerTask.state = ScheduleTask.SCHEDULED;
                schedulerTask.scheduler = this;
                schedulerTask.timerTask = new SchedulerTimerTask(schedulerTask, iterator);
                if (!isTimerCancel) {
                    timer.schedule(schedulerTask.timerTask, time);
                }
            }
        }
    }

    /**
     * 返回当前调度任务执行的具体时间。
     *
     * @return Date
     */
    public Date getCurrentDate() {
        return time;
    }

    /**
     * 重复调度。
     *
     * @param schedulerTask SchedulerTask    调度任务
     * @param      iterator ScheduleIterator 调度时间迭代器
     */
    private void reschedule(ScheduleTask schedulerTask, ScheduleIterator iterator) {
        time = iterator.next();
        if (time == null) {
            schedulerTask.cancel();
        } else {
            synchronized (schedulerTask.lock) {
                if (schedulerTask.state != ScheduleTask.CANCELLED) {
                    schedulerTask.scheduler = this;
                    schedulerTask.timerTask = new SchedulerTimerTask(schedulerTask, iterator);
                    if (!isTimerCancel) {
                        timer.schedule(schedulerTask.timerTask, time);
                    }
                }
            }
        }
    }

}
