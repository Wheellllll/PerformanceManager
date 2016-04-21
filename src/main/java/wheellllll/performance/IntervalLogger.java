package wheellllll.performance;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by sweet on 4/21/16.
 */
public class IntervalLogger extends Logger {
    private HashMap<String, Integer> indexes = new HashMap<>();
    private HashMap<String, String> details = new HashMap<>();
    private ScheduledExecutorService sc = null;
    private ReadWriteLock mLock = null;
    private int mInitialDelay = 0;
    private int mPeriod = 1;
    private TimeUnit mTimeUnit = TimeUnit.MINUTES;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");

    public IntervalLogger() {
        sc = Executors.newScheduledThreadPool(1);
        mLock = new ReentrantReadWriteLock();
    }

    public void setInitialDelay(int initialDelay) {
        this.mInitialDelay = initialDelay;
    }

    public void setInterval(int period, TimeUnit timeUnit) {
        this.mPeriod = period;
        this.mTimeUnit = timeUnit;
    }

    public void setDateFormat(String dateFormat) {
        df.applyPattern(dateFormat);
    }


    public void start() {
        sc.scheduleAtFixedRate(new Runnable() {
           @Override
           public void run() {
               mLock.readLock().lock();
               File file = new File(mLogDir, mLogPrefix + " " + df.format(new Date()) + "." + mLogSuffix);
               LogUtils.log(file, indexes, false);
               mLock.readLock().unlock();
           }
       },
                mInitialDelay,
                mPeriod,
                mTimeUnit);
    }

    public void stop() {
        sc.shutdown();
    }

    public void addIndex(String index) {
        mLock.writeLock().lock();
        indexes.put(index, 0);
        mLock.writeLock().unlock();
    }

    public void addIndex(String index, String detail) {
        mLock.writeLock().lock();
        indexes.put(index, 0);
        details.put(index, detail);
        mLock.writeLock().unlock();
    }

    public void setIndex(String index, Integer num) {
        mLock.writeLock().lock();
        indexes.put(index, num);
        mLock.writeLock().unlock();
    }

    public void updateIndex(String index, int num) {
        mLock.writeLock().lock();
        indexes.put(index, indexes.get(index) + num);
        mLock.writeLock().unlock();
    }

    public void removeIndex(String index) {
        mLock.writeLock().lock();
        indexes.remove(index);
        mLock.writeLock().unlock();
    }

    public void clear() {
        mLock.writeLock().lock();
        indexes.clear();
        mLock.writeLock().unlock();
    }

}
