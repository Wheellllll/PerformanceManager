package wheellllll.performance;

import org.zeroturnaround.zip.ZipUtil;
import org.zeroturnaround.zip.commons.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by FanLiang on 5/2 0002.
 */
public class WeeklyArchive  {
    private String mFromDir = "./";
    private String mToDir = "./";
    private File file1;
    private File file2;

    private int mInitialDelay = 1;
    private int mPeriod = 7;
    private TimeUnit mTimeUnit = TimeUnit.SECONDS;

    private ScheduledExecutorService sc = null;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");

    public void setWeeklyArchiveDir(String fromDir,String toDir) {
        file1 = new File(fromDir);
        file2 = new File(toDir);
        if(!file1.exists()) file1.mkdirs();
        if(!file2.exists()) file2.mkdirs();
        mFromDir = fromDir;
        mToDir = toDir;
        files = file1.listFiles();
    }

    public  WeeklyArchive(){
        files = new File[10];
        sc = Executors.newScheduledThreadPool(1);
    }

    private File[] files;



    public String getWeeklyArcDir(){ return mToDir; }

    public void start() {
//        sc.scheduleAtFixedRate(()-> {
//                    File tmpFolder = new File(System.getProperty("java.io.tmpdir") + "/.wheellllll");
                    File tmpFolder = new File("E:/大三（下）/软件复用/PM/tmp");
                    System.out.println("doing start...");
                    try{
                       if (!tmpFolder.exists()) {
                           tmpFolder.mkdirs();
                           System.out.println("tmp not exist");
                           }
                    }catch (Exception e){
                       e.printStackTrace();
                   }

                    for (int i = 0; i < 2; i++) {
                            ZipUtil.unpack(files[i], tmpFolder);
                    }

                    File destArchive = new File(mToDir, "WeeklyArc " + df.format(new Date()) + ".zip");
                    try {
                            ZipUtil.pack(tmpFolder, destArchive);
                            FileUtils.cleanDirectory(tmpFolder);
                        System.out.println("doing packing...");
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
//                },
//                mInitialDelay,
//                mPeriod,
//                mTimeUnit);
        System.out.println("3");
    }

    public void stop() {
        sc.shutdown();
    }

    public static void main(String[] args){
        WeeklyArchive test= new WeeklyArchive();
        test.setWeeklyArchiveDir("./from","./to");
        test.start();
        test.stop();
    }



}
