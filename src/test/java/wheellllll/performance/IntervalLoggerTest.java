package wheellllll.performance;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import wheellllll.performance.IntervalLogger;

import java.util.IllegalFormatCodePointException;

import static org.junit.Assert.*;

/**
 * Created by summer on 4/26/16.
 */
public class IntervalLoggerTest {

    @Test
    public void testSetInitialDelay() throws Exception {

    }

    @Test
    public void testSetInterval() throws Exception {

    }

    @Test
    public void testSetDateFormat() throws Exception {

    }

    @Test
    public void testStart() throws Exception {

    }

    @Test
    public void testStop() throws Exception {

    }

    @Test
    public void testAddIndex() throws Exception {
        IntervalLogger logger = new IntervalLogger();
        String index = "test";
        Integer in = new Integer(1);
        logger.addIndex(index);
        logger.updateIndex(index,in);
        Integer result = logger.getIndex(index);
        assertEquals(result,in);
    }

    @Test
    public void testSetIndex() throws Exception {
        IntervalLogger logger = new IntervalLogger();
        String index = "test";
        Integer in = new Integer(8);
        logger.addIndex(index);
        logger.setIndex(index,in);
        Integer result = logger.getIndex(index);
        assertEquals(result,in);
    }

    @Test
    public void testUpdateIndex() throws Exception {
        IntervalLogger logger = new IntervalLogger();
        String index = "test";
        Integer in = new Integer(8);
        logger.addIndex(index);
        logger.updateIndex(index,in);
        Integer result = logger.getIndex(index);
        assertEquals(result,in);
    }

    @Test
    public void testGetIndex() throws Exception {
        IntervalLogger logger = new IntervalLogger();
        String index = "test";
        Integer in = new Integer(6);
        logger.addIndex(index);
        logger.updateIndex(index,in);
        Integer result = logger.getIndex(index);
        assertEquals(result,in);
    }

    @Test(expected=NullPointerException.class)
    public void testRemoveIndex() throws Exception {
        IntervalLogger logger = new IntervalLogger();
        String index = "test";
        logger.addIndex(index);
        logger.removeIndex(index);
        logger.getIndex(index);
    }

    @Test
    public void testSetMaxFileSize() throws Exception {
        IntervalLogger logger = new IntervalLogger();
        logger.setMaxFileSize(1, Logger.SizeUnit.B);
        assertEquals(1, logger.getMaxFileSize());
        assertEquals(Logger.SizeUnit.B, Logger.SizeUnit.B);
    }

    @Test
    public void testSetMaxTotalSize() throws Exception {
        IntervalLogger logger = new IntervalLogger();
        logger.setMaxTotalSize(10, Logger.SizeUnit.MB);
        assertEquals(10, logger.getMaxTotalSize());
        assertEquals(Logger.SizeUnit.MB, Logger.SizeUnit.MB);
    }

    @Test
    public void testClear() throws Exception {

    }
}