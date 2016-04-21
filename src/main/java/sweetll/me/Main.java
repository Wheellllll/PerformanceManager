package sweetll.me;

import wheellllll.performance.LogUtils;
import wheellllll.performance.PerformanceManager;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        // write your code here
        PerformanceManager mPM = new PerformanceManager();


//        mPM.setLogPath("./log");
//        mPM.setLogPrefix("test");
//
//        mPM.setTimeUnit(TimeUnit.SECONDS);
//
//        mPM.addIndex("loginSuccess");
//        mPM.addIndex("loginFail");
//
//        mPM.start();
//
//        for (int i = 0; i < 10; ++i) {
//            mPM.updateIndex("loginSuccess", 1);
//            mPM.updateIndex("loginFail", 2);
//            try {
//                Thread.sleep(1000);
//                System.out.println("" + i);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        mPM.stop();
    }



/*
 Username: Sweet
 Time: 2016-04-20 16_42_25
 Message: foo

 Username: Black
 Time: 2016-4-20 16_42_30
 Message: bar

 ...
 ...

    pm.setWorkDir("./WorkDir");
    pm.setHistoryDir("./HistoryDir");
    pm.setFormatPattern("");

    pm.setArchiveMode(true);
    pm.setArchiveDir("./ArchiveDir");

    temp1
    log6 log 7 log8

    temp2
    log1 log2 log3 log4 log5

    4-20.zip
    4-21.zip
    4-22.zip
    4-23.zip
*/
}
