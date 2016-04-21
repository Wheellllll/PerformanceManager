package sweetll.me;

import wheellllll.performance.ArchiveManager;
import wheellllll.performance.IntervalLogger;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        // write your code here
//        System.out.println(System.getProperty("java.io.tmpdir"));
        ArchiveManager am = new ArchiveManager();
        am.setArchiveDir("./archive");

        IntervalLogger logger1 = new IntervalLogger();
        logger1.setLogDir("./log");
        logger1.setLogPrefix("test");
        logger1.setInterval(1, TimeUnit.SECONDS);

        logger1.addIndex("loginSuccess");
        logger1.addIndex("loginFail");

        am.addLogger(logger1);
        am.setInterval(2, TimeUnit.SECONDS);

        logger1.start();
        am.start();

        for (int i = 0; i < 10; ++i) {
            logger1.updateIndex("loginSuccess", 1);
            logger1.updateIndex("loginFail", 2);
            try {
                Thread.sleep(1000);
                System.out.println("" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        logger1.stop();
        am.stop();
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
