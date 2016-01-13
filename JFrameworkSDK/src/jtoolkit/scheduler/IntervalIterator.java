package jtoolkit.scheduler;

import java.util.Calendar;
import java.util.Date;

/**
 * 【间隔调度】迭代器.
 *
 * @version 1.0 初始版本
 */
public class IntervalIterator
    implements ScheduleIterator {

    private final Calendar calendar = Calendar.getInstance();
    /** 间隔时间,单位是毫秒*/
    private final int interval;

    public IntervalIterator(int interval) {
        this.interval = interval;
    }

    /**
     * 产生下一个调度的具体时间.
     * @return Date
     */
    public Date next() {
        calendar.add(Calendar.MILLISECOND, interval);
        return calendar.getTime();
    }

}
