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
 * Created by sweet on 4/10/16.
 */
public class PerformanceManager {
    private HashMap<String, Integer> indexes = new HashMap<>();
    private ScheduledExecutorService sc = null;
    private int mInitialDelay = 0;
    private int mPeriod = 1;
    private TimeUnit mTimeUnit = TimeUnit.MINUTES;
    private ReadWriteLock mLock = new ReentrantReadWriteLock();

    private String logPath = "./";
    private String logPrefix = "record_%s.log";
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

    public PerformanceManager() {
        sc = Executors.newScheduledThreadPool(1);
    }

    public void setInitialDelay(int initialDelay) {
        this.mInitialDelay = initialDelay;
    }

    public void setPeriod(int period) {
        this.mPeriod = period;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.mTimeUnit = timeUnit;
    }

    public void setLogPrefix(String prefix) {
        logPrefix = prefix + "_%s.log";

    }

    public void setLogPath(String path) {
        File file = new File(path);
        file.mkdirs();
        logPath = path;
    }

    public void start() {
        sc.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                mLock.readLock().lock();
                File file = new File(logPath + "/" + String.format(logPrefix, df.format(new Date())));
                LogUtils.log(file, indexes);
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

    public void setIndex(String index, Integer num) {
        mLock.writeLock().lock();
        indexes.put(index, num);
        mLock.writeLock().unlock();
    }
}
