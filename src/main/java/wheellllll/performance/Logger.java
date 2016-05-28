package wheellllll.performance;

import java.io.File;

/**
 * Created by sweet on 4/21/16.
 */
public abstract class Logger {
    protected String mLogDir = "./";
    protected String mLogPrefix = "record";
    protected String mLogSuffix = "log";
    protected String mFormatPattern = null;

    protected int maxFileSize = -1;
    protected SizeUnit fileSizeUnit = SizeUnit.B;
    protected int maxTotalSize = -1;
    protected SizeUnit totalSizeUnit = SizeUnit.B;
    protected boolean truncateLatest = false;

    protected boolean isArchive = false;
    protected org.slf4j.Logger logger;
    protected boolean isUseLogback = false;

    public void setArchive(boolean archive) {
        isArchive = archive;
    }

    public String getLogDir() {
        return mLogDir;
    }

    public void setLogDir(String logDir) {
        File file = new File(logDir);
        file.mkdirs();
        mLogDir = logDir;
    }

    public long getLogDirSize() {
        File file = new File(mLogDir);
        return LogUtils.getDirSize(file);
    }

    public String getLogPrefix() {
        return mLogPrefix;
    }

    public void setLogPrefix(String logPrefix) {
        mLogPrefix = logPrefix;
    }

    public String getLogSuffix() {
        return mLogSuffix;
    }

    public void setLogSuffix(String logSuffix) {
        mLogSuffix = logSuffix;
    }

    public String getFormatPattern() {
        return mFormatPattern;
    }

    public void setFormatPattern(String formatPattern) {
        mFormatPattern = formatPattern;
    }

    public void setMaxFileSize(int size, SizeUnit unit) {
        if (size < 0)
            return;
        maxFileSize = size;
        fileSizeUnit = unit;
        if (maxTotalSize != -1 && maxFileSize * fileSizeUnit.getValue() > maxTotalSize * totalSizeUnit.getValue()) {
            maxTotalSize = size;
            totalSizeUnit = unit;
        }
    }

    public void setMaxTotalSize(int size, SizeUnit unit) {
        if (size < 0)
            return;
        maxTotalSize = size;
        fileSizeUnit = unit;
        if (maxFileSize == -1) {
            maxFileSize = size;
            fileSizeUnit = unit;
        } else if (maxFileSize * fileSizeUnit.getValue() > maxTotalSize * totalSizeUnit.getValue()){
            maxFileSize = size;
            fileSizeUnit = unit;
        }
    }

    public boolean isTruncateLatest() {
        return truncateLatest;
    }

    public void setTruncateLatest(boolean truncateLatest) {
        this.truncateLatest = truncateLatest;
    }

    public int getMaxFileSize() {
        return maxFileSize;
    }

    public SizeUnit getFileSizeUnit() {
        return fileSizeUnit;
    }

    public int getMaxTotalSize() {
        return maxTotalSize;
    }

    public SizeUnit getTotalSizeUnit() {
        return totalSizeUnit;
    }

    public enum SizeUnit {
        GB(1024 * 1024 * 1024), MB(1024 * 1024), KB(1024), B(1);
        private long value;

        SizeUnit(long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }
    }
}
