package jenterprise.bof.classes.AppExplorerObject.noticeService;

import jtoolkit.scheduler.adapter.JTTDAdapter;
import jtoolkit.scheduler.ScheduleTask;
import jtoolkit.scheduler.ScheduleIterator;
import jtoolkit.scheduler.IntervalIterator;
import jtoolkit.scheduler.TemporalTaskDispatcher;

/**
 * FMIS公告适配器。
 *
 * @version 1.0 初始版本${gengeng}
 */
public class JNoticeAdapter
    extends JTTDAdapter {

    /**
     *
     * @param increObj Object
     * @param taskObj  Object
     * @param timeObj  Object
     */
    public JNoticeAdapter(Object increObj, Object taskObj, Object timeObj) {
        super(increObj, taskObj, timeObj);
        Integer time = (Integer) timeObj;
        scheduleTask  = new JNoticeScheduleTask(increObj, taskObj);
        timeIterator  = new IntervalIterator(time.intValue());
        ttDispatcher  = new TemporalTaskDispatcher(scheduleTask, timeIterator);
    }

    /**
     *
     * @param     task ScheduleTask
     * @param iterator ScheduleIterator
     */
    public JNoticeAdapter(ScheduleTask task, ScheduleIterator iterator) {
        super(task, iterator);
    }

    /**
     *
     * @param     task ScheduleTask
     * @param iterator ScheduleIterator
     * @param      obj Object
     */
    public JNoticeAdapter(ScheduleTask task, ScheduleIterator iterator, Object obj) {
        super(task, iterator, obj);
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
     * @todo Implement this jtoolkit.scheduler.adapter.JTTDAdapter method
     */
    public Object getResultObject() {
        return null;
    }

}
