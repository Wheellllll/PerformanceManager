package wheellllll.performance;

import org.apache.commons.lang3.text.StrSubstitutor;

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

    public static void log(File file, HashMap<String, String> indexes, boolean append) {
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsolutePath(), append);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (String key : indexes.keySet()) {
                String record = String.format("\t%s : %s\n", key, indexes.get(key));
                bufferedWriter.write(record);
            }
        } catch (Exception e) {
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

    public static void log(File file, HashMap<String, String> indexes, String formatPattern, boolean append) {
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsolutePath(), append);
            bufferedWriter = new BufferedWriter(fileWriter);
            StrSubstitutor sub = new StrSubstitutor(indexes);
            String resolvedString = sub.replace(formatPattern);
            bufferedWriter.write(resolvedString);
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

    public static void log(File file, String message, boolean append) {
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsolutePath(), append);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(message);
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
