package wheellllll.performance;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by sweet on 5/3/16.
 */
public class IntegrationTest {
    @Test
    public void testFunction() throws Exception {
        //Initial Interval Logger
        IntervalLogger logger1 = new IntervalLogger();
        logger1.setLogDir("./log");
        logger1.setLogPrefix("test");
        logger1.setInterval(5, TimeUnit.SECONDS);

        logger1.addIndex("loginSuccess");
        logger1.addIndex("loginFail");

        logger1.setFormatPattern("Login Success Number : ${loginSuccess}\nLogin Fail Number : ${loginFail}\n\n");

        //Initial Realtime Logger
        RealtimeLogger logger2 = new RealtimeLogger();
        logger2.setLogDir("./llog");
        logger2.setLogPrefix("test");

        logger2.setFormatPattern("Username : ${username}\nTime : ${time}\nMessage : ${message}\n\n");

        //Initial Archive Manager
        ArchiveManager am1 = new ArchiveManager();
        am1.setArchiveDir("./archive");
        am1.setDatePattern("yyyy-MM-dd HH:mm:ss");

        am1.addLogger(logger1);
        am1.addLogger(logger2);
//        am1.addFolder("./log");
//        am1.addFolder("./llog");
        am1.setInterval(5, TimeUnit.SECONDS);

        ArchiveManager am2 = new ArchiveManager();
        am2.setArchiveDir("./aarchive");
        am2.setDatePattern("yyyy-MM-dd HH:mm:ss");

        am2.addFolder("./archive");
        am2.setInterval(30, TimeUnit.SECONDS);

        logger1.start();
        am1.start();
        am2.start();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 150; ++i) {
                logger1.updateIndex("loginSuccess", 1);
                logger1.updateIndex("loginFail", 2);
                HashMap<String, String> map = new HashMap<>();
                map.put("username", "Sweet");
                map.put("time", "2016-04-21");
                map.put("message", "Hello World - " + logger1.getIndex("loginSuccess"));
                logger2.log(map);
                try {
                    Thread.sleep(1000);
                    System.out.println("" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 150; ++i) {
                logger1.updateIndex("loginSuccess", 1);
                logger1.updateIndex("loginFail", 2);
                HashMap<String, String> map = new HashMap<>();
                map.put("username", "Sweet");
                map.put("time", "2016-04-21");
                map.put("message", "Hello World - " + logger1.getIndex("loginSuccess"));
                logger2.log(map);
                try {
                    Thread.sleep(1000);
                    System.out.println("" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread2.start();

//        Thread.currentThread().join();

        logger1.stop();
        am1.stop();
        am2.stop();
    }
}
