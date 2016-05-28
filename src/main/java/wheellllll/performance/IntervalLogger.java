package wheellllll.performance;

import java.awt.*;
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
    private TimeUnit mTimeUnit = TimeUnit.SECONDS;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");

    public IntervalLogger() {
        sc = Executors.newScheduledThreadPool(1);
        mLock = new ReentrantReadWriteLock();
    }

    public IntervalLogger(org.slf4j.Logger _logger) {
        this.logger = _logger;
        isUseLogback = true;
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
                    HashMap<String, String> data = new HashMap<>();
                    for (String key : indexes.keySet()) {
                        data.put(key, Integer.toString(indexes.get(key)));
                    }
                    String message = LogUtils.MapToString(data, getFormatPattern());

                    if (!isUseLogback) {
                        Date date = new Date();
                        File file = new File(mLogDir, mLogPrefix + " " + df.format(date) + "." + mLogSuffix);

                        File logFolder = new File(mLogDir);
                        if (!logFolder.exists()) logFolder.mkdirs();


                        //Limit the total size
                        if (getMaxTotalSize() != -1) {
                            long mFileSize = getMaxFileSize() == -1 ?
                                    message.getBytes().length : Math.min(message.getBytes().length, getMaxFileSize() * getFileSizeUnit().getValue());
                            long expectedSize = getLogDirSize() + mFileSize;
                            long sizeLimit = getMaxTotalSize() * getTotalSizeUnit().getValue();
                            if (expectedSize > sizeLimit) {
                                File[] filesToDelete = null;
                                if (isTruncateLatest()) {
                                    filesToDelete = LogUtils.latestFiles(
                                            new File(getLogDir()), getMaxTotalSize() * getTotalSizeUnit().getValue(), message.getBytes().length);
                                } else {
                                    filesToDelete = LogUtils.earliestFiles(
                                            new File(getLogDir()), getMaxTotalSize() * getTotalSizeUnit().getValue(), message.getBytes().length);
                                }
                                for (File f : filesToDelete) {
                                    f.delete();
                                }
                            }
                        }

                        LogUtils.log(file, message, false, getMaxFileSize() * getFileSizeUnit().getValue());

                    } else {

                        LogUtils.log(this.logger, message);
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
