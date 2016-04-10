package sweetll.me;

import wheellllll.performance.PerformanceManager;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        // write your code here
        PerformanceManager mPM = new PerformanceManager();

        mPM.setTimeUnit(TimeUnit.SECONDS);

        mPM.addIndex("loginSuccess");
        mPM.addIndex("loginFail");

        mPM.start();

        for (int i = 0; i < 10; ++i) {
            mPM.updateIndex("loginSuccess", 1);
            mPM.updateIndex("loginFail", 2);
            try {
                Thread.sleep(1000);
                System.out.println("" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        mPM.stop();

    }
}
