package wheellllll.performance;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by summer on 4/26/16.
 */
public class RealtimeLoggerTest {

    @Test
    public void testLog() throws Exception {

    }

    @Test
    public void testLog1() throws Exception {

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
}