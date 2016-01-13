package jtoolkit.scheduler;

import java.util.Date;

/**
 * 时间任务调度模型。
 * <p>简介：根据具体的时间迭代器执行具体的调度任务。一个具体的时间迭代器实现
 *         接口ScheduleIterator，一个具体的调度任务继承所有调度任务的抽
 *         象父类SchedulerTask，实现run()。
 *
 * @version 1.0 初始版本{gengeng}
 */
public class TemporalTaskDispatcher {

    /**调度引擎*/
    private final Scheduler scheduler;
    /**调度任务*/
    private ScheduleTask scheduleTask;
    /**时间迭代器*/
    private ScheduleIterator timeIterator;

    public TemporalTaskDispatcher(ScheduleTask task, ScheduleIterator iterator) {
        this.scheduleTask = task;
        this.timeIterator = iterator;
        scheduler = new Scheduler(this);
    }

    /**
     * 获得调度引擎。
     * @return Scheduler
     */
    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * 获得调度任务。
     * @return SchedulerTask
     */
    public ScheduleTask getScheduleTask() {
        return scheduleTask;
    }

    /**
     * 设置调度任务。
     * @return SchedulerTask
     */
    public void setScheduleTask(ScheduleTask task) {
        if (task != null) {
            this.scheduleTask = task;
        }
    }

    /**
     * 获得时间迭代器。
     * @return ScheduleIterator
     */
    public ScheduleIterator getScheduleIterator() {
        return timeIterator;
    }

    /**
     * 开始，默认是当前时间参与调度。
     */
    public void start() {
        start(true);
    }

    /**
     * 开始。
     *
     * @param currentJoin boolean 当前时间参与调度否
     */
    public void start(boolean currentJoin) {
        if (currentJoin) {
            scheduler.schedule(scheduleTask, timeIterator, new Date());
        } else {
            scheduler.schedule(scheduleTask, timeIterator);
        }
    }

    /**
     * 取消调度引擎。
     */
    public void cancelScheduler() {
        scheduler.cancel();
    }

    /**
     * 取消调度任务。
     * 取消调度任务之后，下次重新调度一个新任务的时候建议使用start(false)方法。
     */
    public boolean cancelScheduleTask() {
        return scheduleTask.cancel();
    }

    /**
     * test
     * @param args String[]
     */
    public static void main(String[] args) {
        TemporalTaskDispatcher alarmClock;
        ScheduleTask task1 = new ScheduleTask() {
            public void run() {
                System.out.println("task1" + getScheduler().getCurrentDate());
            }
        };
        ScheduleTask task2 = new ScheduleTask() {
            public void run() {
                System.out.println("task2" + getScheduler().getCurrentDate());
            }
        };

        ScheduleIterator it1 = new IntervalIterator(2000);
        ScheduleIterator it2 = new IntervalIterator(3000);
        alarmClock = new TemporalTaskDispatcher(task1, it1);
        alarmClock.start();
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException ex) {
//        }
//        alarmClock.cancelScheduleTask();
//        System.out.println("ScheduleTask canceled...");
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException ex) {
//        }
        alarmClock.getScheduleIterator();
        alarmClock.setScheduleTask(task2);
        alarmClock.start();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException ex) {
//        }
//        alarmClock.cancelScheduler();
//        System.out.println("Scheduler canceled...");
    }
}
