import cc.stacks.monitor.Work;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

class TestExample {

    private final Logger logger = Logger.getLogger("TestExample");

    @Test
    void testWork() {
        try {
            logger.info("Start test work");
            Work work = new Work(5);
            long diff = work.getNextMinute().toInstant().getEpochSecond() - Calendar.getInstance().toInstant().getEpochSecond();
            work.run();
            logger.info("Work has started, collection will be executed in " + diff + " seconds...");
            TimeUnit.SECONDS.sleep(diff + 8);
            work.stop();
            TimeUnit.SECONDS.sleep(1);
            work = new Work();
            work.excludeCPU().excludeDisk().excludeMemory().excludeNetwork().run();
            logger.info("Work completed, test finished");
        } catch (Exception e) {
            logger.warning("Test aborted, " + e.getMessage());
        }
    }

}