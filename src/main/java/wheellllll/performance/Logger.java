package wheellllll.performance;

import org.zeroturnaround.zip.commons.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by sweet on 4/21/16.
 */
public abstract class Logger {
    protected String mLogDir = "./";
    protected String mLogPrefix = "record";
    protected String mLogSuffix = "log";
    protected String mFormatPattern = null;

    protected boolean isArchive = false;

    public void setLogDir(String logDir) {
        File file = new File(logDir);
        file.mkdirs();
        mLogDir = logDir;
    }

    public void setFormatPattern(String formatPattern) {
        mFormatPattern = formatPattern;
    }

    public void setLogPrefix(String logPrefix) {
        mLogPrefix = logPrefix;
    }

    public void setLogSuffix(String logSuffix) {
        mLogSuffix = logSuffix;
    }

    public void setArchive(boolean archive) {
        isArchive = archive;
    }

    public String getLogDir() {
        return mLogDir;
    }

    public String getLogPrefix() {
        return mLogPrefix;
    }

    public String getLogSuffix() {
        return mLogSuffix;
    }

    public String getFormatPattern() {
        return mFormatPattern;
    }
}
