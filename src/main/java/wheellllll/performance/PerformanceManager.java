package wheellllll.performance;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by sweet on 4/10/16.
 */
public class PerformanceManager {
    private HashMap<String, Integer> indexes = new HashMap<>();
    private ScheduledExecutorService sc = null;
    private int mInitialDelay = 0;
    private int mPeriod = 1;
    private TimeUnit mTimeUnit = TimeUnit.MINUTES;

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

    public void start() {
        sc.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                LogUtils.log(indexes);
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
        indexes.put(index, 0);
    }

    public void updateIndex(String index, int num) {
        indexes.put(index, indexes.get(index) + num);
    }
}
