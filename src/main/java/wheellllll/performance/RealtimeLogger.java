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

        String message = LogUtils.MapToString(data, getFormatPattern());

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

        LogUtils.log(file, message, true, getMaxFileSize() * getFileSizeUnit().getValue());
        mLock.readLock().unlock();
    }

    public void log(String data) {
        mLock.readLock().lock();
        File file = new File(mLogDir, mLogPrefix + "." + mLogSuffix);

        File logFolder = new File(mLogDir);
        if (!logFolder.exists()) logFolder.mkdirs();

        //Limit the total size
        if (getMaxTotalSize() != -1) {
            long mFileSize = getMaxFileSize() == -1 ?
                    data.getBytes().length : Math.min(data.getBytes().length, getMaxFileSize() * getFileSizeUnit().getValue());
            long expectedSize = getLogDirSize() + mFileSize;
            long sizeLimit = getMaxTotalSize() * getTotalSizeUnit().getValue();
            if (expectedSize > sizeLimit) {
                File[] filesToDelete = null;
                if (isTruncateLatest()) {
                    filesToDelete = LogUtils.latestFiles(
                            new File(getLogDir()), getMaxTotalSize() * getTotalSizeUnit().getValue(), data.getBytes().length);
                } else {
                    filesToDelete = LogUtils.earliestFiles(
                            new File(getLogDir()), getMaxTotalSize() * getTotalSizeUnit().getValue(), data.getBytes().length);
                }
                for (File f : filesToDelete) {
                    f.delete();
                }
            }
        }

        LogUtils.log(file, data, true, getMaxFileSize() * getFileSizeUnit().getValue());
        mLock.readLock().unlock();
    }
}
