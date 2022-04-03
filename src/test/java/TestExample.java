import cc.stacks.monitor.Obtain;
import cc.stacks.monitor.UseCallback;
import cc.stacks.monitor.Work;
import cc.stacks.monitor.model.UsePacker;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class TestExample implements UseCallback {

    private final Logger logger = Logger.getLogger("TestExample");

    @Test
    public void testWork() {
        try {
            Work w0 = new Work();
            long diff = w0.getNextMinute().toInstant().getEpochSecond() - Calendar.getInstance().toInstant().getEpochSecond();
            logger.info("collection will be executed in " + diff + " seconds...");
            Work w1 = testWork1();
            Work w2 = testWork2();
            Work w3 = testWork3();
            Work w4 = testWork4();
            TimeUnit.SECONDS.sleep(diff + 3);
            w1.stop();
            if(w2!=null) w2.stop();
            w3.stop();
            w4.stop();
        } catch (Exception e) {
            logger.warning("Test aborted, " + e.getMessage());
        }
    }

    public Work testWork1() {
        try {
            // 测试输出到文件
            logger.info("Test work 1");
            Work work = new Work(5);
            work.save("./").run();
            return work;
        } catch (Exception e) {
            logger.warning("Test aborted, " + e.getMessage());
            return null;
        }
    }

    public Work testWork2() {
        try {
            // 测试关闭所有采集器
            logger.info("Test work 2");
            Work work = new Work();
            work.excludeCPU().excludeDisk().excludeMemory().excludeNetwork().run();
            work.stop();
            return work;
        } catch (Exception e) {
            logger.warning("Test aborted, " + e.getMessage());
            return null;
        }
    }

    public Work testWork3() {
        try {
            // 测试回调
            logger.info("Test work 3");
            Work work = new Work(this);
            work.run();
            return work;
        } catch (Exception e) {
            logger.warning("Test aborted, " + e.getMessage());
            return null;
        }
    }

    public Work testWork4() {
        try {
            // 测试无回调构造和异常文件地址
            logger.info("Test work 4");
            Work work = new Work(null, 5);
            work.save("/tmp/").run();
            return work;
        } catch (Exception e) {
            logger.warning("Test aborted, " + e.getMessage());
            return null;
        }
    }

    @Test
    public void testObtain() {
        try {
            // 测试直接获取数据
            logger.info("Test obtain");
            Obtain obtain = new Obtain();
            receive(obtain.syncCollectionData());
            obtain = new Obtain(false, false, true, true);
            receive(obtain.syncCollectionData());
        } catch (Exception e) {
            logger.warning("Test aborted, " + e.getMessage());
        }
    }

    @Override
    public void receive(UsePacker packer) {
        logger.info(packer.getCpu().toString());
        logger.info(packer.getDisk().toString());
        logger.info(packer.getMemory().toString());
        logger.info(packer.getNetwork().toString());
    }
}