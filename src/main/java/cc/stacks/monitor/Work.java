package cc.stacks.monitor;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Collection work class
 * <p>Chinese: <b>采集工作</b></p>
 *
 * @author Skay Zhang <small>( https://github.com/skay-zhang )</small>
 * @version 1.0.0
 */
public class Work {

    // 采集间隔时间(秒)
    private final long collectionIntervalTime;
    // 保存位置
    private String savePath;
    // 采集处理器信息
    private boolean collectionCPU = true;
    // 采集内存信息
    private boolean collectionMemory = true;
    // 采集磁盘信息
    private boolean collectionDisk = true;
    // 采集网络信息
    private boolean collectionNetwork = true;

    public Work() {
        this.collectionIntervalTime = 60;
    }

    public Work(long collectionIntervalTime) {
        this.collectionIntervalTime = collectionIntervalTime;
    }

    public void run() throws NoCollectorException {
        if (!collectionCPU && !collectionDisk && !collectionMemory && !collectionNetwork)
            throw new NoCollectorException("Minimum 1 collection item needs to be enabled!");
        Timer timer = new Timer();
        Obtain obtain = new Obtain(savePath, collectionCPU, collectionMemory, collectionDisk, collectionNetwork);
        timer.schedule(obtain, getNextMinute(), collectionIntervalTime * 1000);
    }

    public Work excludeCPU() {
        this.collectionCPU = false;
        return this;
    }

    public Work excludeMemory() {
        this.collectionMemory = false;
        return this;
    }

    public Work excludeDisk() {
        this.collectionDisk = false;
        return this;
    }

    public Work excludeNetwork() {
        this.collectionNetwork = false;
        return this;
    }

    public Work save(String path) {
        this.savePath = path;
        return this;
    }

    private Date getNextMinute() {
        Calendar calendar = Calendar.getInstance();
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.MINUTE, minute + 1);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();

    }

}