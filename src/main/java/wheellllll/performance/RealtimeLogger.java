package wheellllll.performance;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by sweet on 4/21/16.
 */
public class RealtimeLogger extends Logger {
    private ReadWriteLock mLock = null;

    public RealtimeLogger() {
        mLock = new ReentrantReadWriteLock();
    }

    public void log(HashMap<String, String> data) {
        mLock.readLock().lock();
        File file = new File(mLogDir, mLogPrefix + "." + mLogSuffix);

        File logFolder = new File(mLogDir);
        if (!logFolder.exists()) logFolder.mkdirs();

        if (getFormatPattern() == null) {
            LogUtils.log(file, data, true);
        }
        else {
            LogUtils.log(file, data, getFormatPattern(), true);
        }
        mLock.readLock().unlock();
    }

    public void log(String data) {
        mLock.readLock().lock();
        File file = new File(mLogDir, mLogPrefix + "." + mLogSuffix);

        File logFolder = new File(mLogDir);
        if (!logFolder.exists()) logFolder.mkdirs();

        if (getFormatPattern() == null) {
            LogUtils.log(file, data, true);
        }
        else {
            LogUtils.log(file, data, true);
        }
        mLock.readLock().unlock();
    }
}
