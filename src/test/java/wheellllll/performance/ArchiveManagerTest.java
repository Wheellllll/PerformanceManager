package wheellllll.performance;

import org.junit.Test;
import wheellllll.performance.ArchiveManager;

import static org.junit.Assert.*;

/**
 * Created by summer on 4/25/16.
 */
public class ArchiveManagerTest {

    @Test
    public void testSetInitialDelay() throws Exception {

    }

    @Test
    public void testSetInterval() throws Exception {

    }

    @Test
    public void testSetDatePattern() throws Exception {

    }

    @Test
    public void testStart() throws Exception {

    }

    @Test
    public void testStop() throws Exception {

    }

    @Test
    public void testAddLogger() throws Exception {

    }

    @Test
    public void testRemoveLogger() throws Exception {

    }

    @Test
    public void testSetArchiveDir() throws Exception {
        ArchiveManager manager = new ArchiveManager();
        String dir = "test";
        manager.setArchiveDir(dir);
        assertEquals(manager.getArchiveDir(),dir);
    }

    @Test
    public void testSetArchivePrefix() throws Exception {
        ArchiveManager manager = new ArchiveManager();
        String prefix = "test";
        manager.setArchivePrefix(prefix);
        assertEquals(manager.getArchivePrefix(),prefix);
    }

    @Test
    public void testGetArchiveDir() throws Exception {
        ArchiveManager manager = new ArchiveManager();
        String dir = "test";
        manager.setArchiveDir(dir);
        assertEquals(manager.getArchiveDir(),dir);
    }

    @Test
    public void testGetArchivePrefix() throws Exception {
        ArchiveManager manager = new ArchiveManager();
        String prefix = "test";
        manager.setArchivePrefix(prefix);
        assertEquals(manager.getArchivePrefix(),prefix);
    }

    @Test
    public void testGetArchiveSuffix() throws Exception {
        ArchiveManager manager = new ArchiveManager();
        assertEquals("log",manager.getArchiveSuffix());
    }
}