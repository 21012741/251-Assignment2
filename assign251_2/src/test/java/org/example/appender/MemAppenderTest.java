package org.example.appender;


import org.apache.log4j.Logger;
import org.example.MyAppenderTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class MemAppenderTest {

    private MemAppender memAppender;
    private static final Logger logger = Logger.getLogger(MyAppenderTest.class.getName());

    @BeforeEach
    public void init() {
        memAppender = (MemAppender) Logger.getRootLogger().getAppender("myAppender");
    }

    @Test
    void printLogs() {
        logger.info("test");
        memAppender.printLogs();
    }

    @Test
    void getEventStrings() {
        assertEquals(memAppender.getEventStrings().size(), 0);
        logger.info("test");
        assertEquals(memAppender.getEventStrings().size(), 1);
    }

    @Test
    public void getCurrentLogs() {
        assertEquals( 0,memAppender.getCurrentLogs().size());
        logger.info("test");
        assertEquals( 1,memAppender.getCurrentLogs().size());
    }


    @Test
    void getMaxSize() {
        assertEquals(memAppender.getMaxSize(),100);
    }

    @Test
    void getDiscardedLogCount() {
        for(int i=0;i< memAppender.getMaxSize()+1;i++){
            logger.info("test");
        }
        assertEquals(1L,memAppender.getDiscardedLogCount());
    }
}