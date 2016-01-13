package jtoolkit.scheduler;

import java.util.TimerTask;

/**
 * 调度任务抽象父类。
 * <p>一个具体的调度任务继承至该类，实现<code>run()</code>方法。
 *
 * @version 1.0 初始版本
 */
public abstract class ScheduleTask
    implements Runnable {

    final Object lock = new Object();

    int state = VIRGIN;
    static final int VIRGIN    = 0;
    static final int SCHEDULED = 1;
    static final int CANCELLED = 2;

    TimerTask timerTask;
    Scheduler scheduler;
    protected Object increObject;

    protected ScheduleTask() {
        this(null);
    }

    protected ScheduleTask(Object obj) {
        this.increObject = obj;
    }

    public abstract void run();

    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * 取消该调度任务。
     * @return boolean
     */
    public boolean cancel() {
        synchronized (lock) {
            if (timerTask != null) {
                timerTask.cancel();
            }
            boolean result = (state == SCHEDULED);
            state = CANCELLED;
            return result;
        }
    }

    /**
     * 获取调度任务执行时间长度。
     * @return long
     */
    public long scheduledExecutionTime() {
        synchronized (lock) {
            return timerTask == null ? 0 : timerTask.scheduledExecutionTime();
        }
    }

}
