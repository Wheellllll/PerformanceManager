package wheellllll.performance;

import org.zeroturnaround.zip.ZipUtil;
import org.zeroturnaround.zip.commons.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by sweet on 4/10/16.
 */
public class ArchiveManager {
    private String mArchiveDir = "./";
    private String mArchivePrefix = "record";
    private String mArchiveSuffix = "zip";

    private ScheduledExecutorService sc = null;
    private int mInitialDelay = 1;
    private int mPeriod = 1;
    private TimeUnit mTimeUnit = TimeUnit.MINUTES;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");

    private ArrayList<Logger> loggers;

    public ArchiveManager() {
        loggers = new ArrayList<>();
        sc = Executors.newScheduledThreadPool(1);
    }

    public void setInitialDelay(int initialDelay) {
        mInitialDelay = initialDelay;
    }

    public void setInterval(int period, TimeUnit timeUnit) {
        mPeriod = period;
        mTimeUnit = timeUnit;
    }

    public void setDatePattern(String datePattern) {
        df.applyPattern(datePattern);
    }

    public void start() {
        sc.scheduleAtFixedRate(() -> {
            File tmpFolder = new File(System.getProperty("java.io.tmpdir") + "/.wheellllll");
            if (!tmpFolder.exists()) tmpFolder.mkdirs();

            File destArchive = new File(mArchiveDir, mArchivePrefix + " " + df.format(new Date()) + "." + mArchiveSuffix);
            try {
                ZipUtil.pack(tmpFolder, destArchive);
                for (Logger logger : loggers) {
                    logger.cleanTmpFolder();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        },
                mInitialDelay,
                mPeriod,
                mTimeUnit);
    }

    public void stop() {
        sc.shutdown();
    }

    public void addLogger(Logger logger) {
        logger.setArchive(true);
        loggers.add(logger);
    }

    public void removeLogger(Logger logger) {
        logger.setArchive(true);
        loggers.remove(logger);
    }

    public void setArchiveDir(String archiveDir) {
        File file = new File(archiveDir);
        file.mkdirs();
        mArchiveDir = archiveDir;
    }

    public void setArchivePrefix(String archivePrefix) {
        mArchivePrefix = archivePrefix;
    }

    public String getArchiveDir() {
        return mArchiveDir;
    }

    public String getArchivePrefix() {
        return mArchivePrefix;
    }

    public String getArchiveSuffix() {
        return mArchiveSuffix;
    }
}
