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
        File file = new File(mLogDir, mLogPrefix + mLogSuffix);
        if (getFormatPattern() == null) {
//            LogUtils.log(file, data, false);
        }
        else {
//            LogUtils.log(file, data, getFormatPattern(), false);
        }
        mLock.readLock().unlock();
    }
}
