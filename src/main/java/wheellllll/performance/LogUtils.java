package wheellllll.performance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * This class provide methods for logging.
 *
 * @author LiaoShanhe
 */

public class LogUtils {
    /**
     * A <code>SimpleDateFormat</code> to format time to customer standard
     */
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void log(HashMap<String, Integer> indexes) {
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        try {
            File file = new File(String.format("clientRecord_%s.log", df.format(new Date())));
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getName(), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(String.format("\tLog at : %s\n", df.format(new Date())));
            for (String key : indexes.keySet()) {
                String record = String.format("\t%s : %d\n", key, indexes.get(key));
                bufferedWriter.write(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
