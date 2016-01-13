package jtoolkit.scheduler.adapter;

import jtoolkit.scheduler.TemporalTaskDispatcher;
import jtoolkit.scheduler.ScheduleTask;
import jtoolkit.scheduler.ScheduleIterator;

/**
 * 时间任务调度(TemporalTaskDispatch)适配接口。
 *
 * @version 1.0 初始版本${gengeng}
 */
public interface ITTDAdaptable {
    /**
     * 获取调度者。
     * @return TemporalTaskDispatcher
     */
    TemporalTaskDispatcher getTemporalTaskDispatcher();

    /**
     * 获取调度任务。
     * @return SchedulerTask
     */
    ScheduleTask getScheduleTask();

    /**
     * 获取时间迭代器。
     * @return ScheduleIterator
     */
    ScheduleIterator getScheduleIterator();

    /**
     * 设置一个调度任务。
     * @param task SchedulerTask
     */
    void setScheduleTask(ScheduleTask task);

    /**
     * 设置一个时间迭代器。
     * @param timeIterator ScheduleIterator
     */
    void setScheduleIterator(ScheduleIterator timeIterator);

    /**
     * 开始调度。
     */
    void start();

    /**
     * 取消调度引擎。
     */
    void cancelScheduler();

    /**
     * 取消调度任务。
     * @return boolean
     */
    boolean cancelScheduleTask();

    /**
     * 获取附加对象。
     * @return Object
     */
    Object getIncreObject();

    /**
     * 增加附加对象。
     * @param object Object
     */
    void setIncreObject(Object object);
}
