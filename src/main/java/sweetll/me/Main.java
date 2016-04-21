package sweetll.me;

import wheellllll.performance.ArchiveManager;
import wheellllll.performance.IntervalLogger;
import wheellllll.performance.RealtimeLogger;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        // write your code here


        //Initial Interval Logger
        IntervalLogger logger1 = new IntervalLogger();
        logger1.setLogDir("./log");
        logger1.setLogPrefix("test");
        logger1.setInterval(1, TimeUnit.SECONDS);

        logger1.addIndex("loginSuccess");
        logger1.addIndex("loginFail");

        logger1.setFormatPattern("Login Success Number : ${loginSuccess}\nLogin Fail Number : ${loginFail}\n\n");

        //Initial Realtime Logger
        RealtimeLogger logger2 = new RealtimeLogger();
        logger2.setLogDir("./llog");
        logger2.setLogPrefix("test");

        logger2.setFormatPattern("Username : ${username}\nTime : ${time}\nMessage : ${message}\n\n");

        //Initial Archive Manager
        ArchiveManager am = new ArchiveManager();
        am.setArchiveDir("./archive");
        am.setDatePattern("yyyy-MM-dd HH:mm");

        am.addLogger(logger1);
        am.addLogger(logger2);
        am.setInterval(1, TimeUnit.DAYS);


        logger1.start();
        am.start();

        for (int i = 0; i < 300; ++i) {
            logger1.updateIndex("loginSuccess", 1);
            logger1.updateIndex("loginFail", 2);
            HashMap<String, String> map = new HashMap<>();
            map.put("username", "Sweet");
            map.put("time", "2016-04-21");
            map.put("message", "Hello World - " + i);
            logger2.log(map);
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
}
