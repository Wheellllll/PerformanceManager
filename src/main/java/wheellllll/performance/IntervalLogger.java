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
    private ScheduledExecutorService sc = null;
    private ReadWriteLock mLock = null;
    private int mInitialDelay = 1;
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
        sc.scheduleAtFixedRate(() -> {
            mLock.readLock().lock();
            Date date = new Date();
            File file = new File(mLogDir, mLogPrefix + " " + df.format(date) + "." + mLogSuffix);
            File tmpFile = new File(getTmpFolder(), mLogPrefix + " " + df.format(date) + "." + mLogSuffix);

            HashMap<String, String> data = new HashMap<>();
            for (String key : indexes.keySet()) {
                 data.put(key, Integer.toString(indexes.get(key)));
            }

            if (getFormatPattern() == null) {
                LogUtils.log(file, data, false);
                if (isArchive) LogUtils.log(tmpFile, data, false);
            }
            else {
                if (isArchive) LogUtils.log(tmpFile, data, false);
                LogUtils.log(file, data, getFormatPattern(), false);
            }
            mLock.readLock().unlock();
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

    public int getIndex(String index) {
        mLock.readLock().lock();
        int value = indexes.get(index);
        mLock.readLock().unlock();
        return value;
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
