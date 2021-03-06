package wheellllll.performance;

import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.text.StrSubstitutor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * This class provide methods for logging.
 */

class LogUtils {

    /**
     * Transform a <code>Map<String, String><code/> data to Sting using a given format pattern.
     */
    static String MapToString(Map<String, String> map, String formatPattern) {
        if (formatPattern == null) {
            StrBuilder sb = new StrBuilder();
            for (String key : map.keySet()) {
                String record = String.format("\t%s : %s\n", key, map.get(key));
                sb.append(record);
            }
            return sb.toString();
        } else {
            StrSubstitutor sub = new StrSubstitutor(map);
            return sub.replace(formatPattern);
        }
    }

    /**
     * This method provide writing function.
     * @param file The file need to write to
     * @param message The String need to write
     * @param append Decide whether writing is appended or not
     * @param sizeLimit The max file size limit
     */
    static void log(File file, String message, boolean append, long sizeLimit) {
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        long oFileSize = 0;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsolutePath(), append);
            oFileSize = file.length();
            bufferedWriter = new BufferedWriter(fileWriter);
            if (sizeLimit < 0) {
                bufferedWriter.write(message);
            } else {
                if (oFileSize + message.getBytes().length <= sizeLimit) {
                    bufferedWriter.write(message);
                } else {
                    bufferedWriter.write(message.substring(0, (int) (sizeLimit - oFileSize)));
                }
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

    /**
     * Get the earliest files of a directory sorting by last modified time when the total size of the directory
     * reach the limit.
     * @param dir Directory
     * @param totalSizeLimit The total file size limit of directory
     * @param newFileSize Size of new file which will be created in the directory
    */
    static File[] earliestFiles(File dir, long totalSizeLimit, long newFileSize) {
        assert dir.isDirectory();
        File[] children = dir.listFiles();
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(children));
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.lastModified() < o2.lastModified())
                    return -1;
                else if (o1.lastModified() > o2.lastModified())
                    return 1;
                else
                    return 0;
            }
        });
        long currentDirSize = getDirSize(dir);
        int i = 0;
        while (currentDirSize + newFileSize > totalSizeLimit && i < files.size()) {
            currentDirSize -= files.get(i++).length();
        }
        return files.subList(0, i).toArray(new File[0]);
    }

    /**
     * Get the latest files of a directory sorting by last modified time when the total size of the directory
     * reach the limit.
     * @param dir Directory
     * @param totalSizeLimit The total file size limit of directory
     * @param newFileSize Size of new file which will be created in the directory
     */
    static File[] latestFiles(File dir, long totalSizeLimit, long newFileSize) {
        assert dir.isDirectory();
        File[] children = dir.listFiles();
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(children));
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.lastModified() < o2.lastModified())
                    return 1;
                else if (o1.lastModified() > o2.lastModified())
                    return -1;
                else
                    return 0;
            }
        });
        long currentDirSize = getDirSize(dir);
        int i = 0;
        while (currentDirSize + newFileSize > totalSizeLimit && i < files.size()) {
            currentDirSize -= files.get(i++).length();
        }
        return files.subList(0, i).toArray(new File[0]);
    }

    /**
     * Get size of a directory or a file.
     */
    static long getDirSize(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                long size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {
                return file.length();
            }
        } else {
            return 0;
        }
    }

}
