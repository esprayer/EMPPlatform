package jtoolkit.scheduler.adapter;

import jtoolkit.scheduler.TemporalTaskDispatcher;
import jtoolkit.scheduler.ScheduleTask;
import jtoolkit.scheduler.ScheduleIterator;

/**
 * TTD适配器的抽象父类。
 * @version 1.0
 */
public abstract class JTTDAdapter
    implements ITTDAdaptable {

    protected TemporalTaskDispatcher ttDispatcher;
    protected ScheduleTask scheduleTask;
    protected ScheduleIterator timeIterator;
    protected Object increObject;
    protected Object taskObject;
    protected Object timeObject;

    /**
     *
     * @param increObj Object
     * @param taskObj  Object
     * @param timeObj  Object
     */
    protected JTTDAdapter(Object increObj, Object taskObj, Object timeObj) {
        this.increObject = increObj;
        this.taskObject  = taskObj;
        this.timeObject  = timeObj;
    }

    /**
     *
     * @param task     SchedulerTask
     * @param iterator ScheduleIterator
     */
    protected JTTDAdapter(ScheduleTask task, ScheduleIterator iterator) {
        this(task, iterator, null);
    }

    /**
     *
     * @param task     ScheduleTask
     * @param iterator ScheduleIterator
     * @param obj      Object
     */
    protected JTTDAdapter(ScheduleTask task, ScheduleIterator iterator, Object obj) {
        this.scheduleTask = task;
        this.timeIterator = iterator;
        this.increObject  = obj;
        ttDispatcher = new TemporalTaskDispatcher(scheduleTask, timeIterator);
    }

    /**
     *
     * @return         TemporalTaskDispatcher
     * @todo Implement this jtoolkit.scheduler.adapter.ITTDAdaptable method
     */
    public TemporalTaskDispatcher getTemporalTaskDispatcher() {
        return ttDispatcher;
    }

    /**
     *
     * @return         ScheduleTask
     * @todo Implement this jtoolkit.scheduler.adapter.ITTDAdaptable method
     */
    public ScheduleTask getScheduleTask() {
        return scheduleTask;
    }

    /**
     *
     * @return         ScheduleIterator
     * @todo Implement this jtoolkit.scheduler.adapter.ITTDAdaptable method
     */
    public ScheduleIterator getScheduleIterator() {
        return timeIterator;
    }

    /**
     *
     * @param task     ScheduleTask
     * @todo Implement this jtoolkit.scheduler.adapter.ITTDAdaptable method
     */
    public void setScheduleTask(ScheduleTask task) {
        if (task != null) {
            scheduleTask = task;
        }
    }

    /**
     *
     * @param timeIterator ScheduleIterator
     * @todo Implement this jtoolkit.scheduler.adapter.ITTDAdaptable method
     */
    public void setScheduleIterator(ScheduleIterator iterator) {
        if (iterator != null) {
            timeIterator = iterator;
        }
    }

    /**
     *
     * @todo Implement this jtoolkit.scheduler.adapter.ITTDAdaptable method
     */
    public void cancelScheduler() {
        ttDispatcher.cancelScheduler();
    }

    /**
     *
     * @return boolean
     * @todo Implement this jtoolkit.scheduler.adapter.ITTDAdaptable method
     */
    public boolean cancelScheduleTask() {
        return ttDispatcher.cancelScheduleTask();
    }

    /**
     *
     * @todo Implement this jtoolkit.scheduler.adapter.ITTDAdaptable method
     */
    public void start() {
        ttDispatcher.start();
    }

    /**
     *
     * @return Object
     * @todo Implement this jtoolkit.scheduler.adapter.ITTDAdaptable method
     */
    public Object getIncreObject() {
        return increObject;
    }

    /**
     *
     * @param object Object
     * @todo Implement this jtoolkit.scheduler.adapter.ITTDAdaptable method
     */
    public void setIncreObject(Object object) {
        increObject = object;
    }

    /**
     * 获取最终的结果。
     * @return Object
     */
    public abstract Object getResultObject();

}
