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

    private Timer timer;
    /**
     * Use callback
     * <p>Chinese: <b>使用回调</b></p>
     */
    private final UseCallback callback;
    /**
     * Collection interval time (seconds)
     * <p>Chinese: <b>采集间隔时间(秒)</b></p>
     */
    private final long collectionIntervalTime;
    /**
     * Location to save the log
     * <p>Chinese: <b>日志保存位置</b></p>
     */
    private String savePath;
    /**
     * Collection CPU data
     * <p>Chinese: <b>采集处理器信息</b></p>
     */
    private boolean collectionCPU = true;
    /**
     * Collection Memory data
     * <p>Chinese: <b>采集内存信息</b></p>
     */
    private boolean collectionMemory = true;
    /**
     * Collection Disk data
     * <p>Chinese: <b>采集磁盘信息</b></p>
     */
    private boolean collectionDisk = true;
    /**
     * Collection Network data
     * <p>Chinese: <b>采集网络信息</b></p>
     */
    private boolean collectionNetwork = true;

    /**
     * Default construction method (60 seconds interval)
     * <p>Chinese: <b>默认构造方法(间隔60秒)</b></p>
     */
    public Work() {
        this.callback = null;
        this.collectionIntervalTime = 60;
    }

    /**
     * Construct method for customizing interval time
     * <p>Chinese: <b>自定义间隔时间的构造方法</b></p>
     *
     * @param callback Collection interval time (seconds)
     */
    public Work(UseCallback callback) {
        this.callback = callback;
        this.collectionIntervalTime = 60;
    }

    /**
     * Construct method for customizing interval time
     * <p>Chinese: <b>自定义间隔时间的构造方法</b></p>
     *
     * @param collectionIntervalTime Collection interval time (seconds)
     */
    public Work(long collectionIntervalTime) {
        this.callback = null;
        this.collectionIntervalTime = collectionIntervalTime;
    }

    /**
     * Construct method for customizing interval time
     * <p>Chinese: <b>自定义间隔时间的构造方法</b></p>
     *
     * @param collectionIntervalTime Collection interval time (seconds)
     */
    public Work(UseCallback callback, long collectionIntervalTime) {
        this.callback = callback;
        this.collectionIntervalTime = collectionIntervalTime;
    }

    /**
     * Start work
     * <p>Chinese: <b>开始工作</b></p>
     */
    public void run() throws NoCollectorException {
        if (!collectionCPU && !collectionDisk && !collectionMemory && !collectionNetwork)
            throw new NoCollectorException("Minimum 1 collection item needs to be enabled!");
        timer = new Timer();
        Obtain obtain = new Obtain(callback, savePath, collectionCPU, collectionMemory, collectionDisk, collectionNetwork);
        timer.schedule(obtain, getNextMinute(), collectionIntervalTime * 1000);
    }

    /**
     * Stop work
     * <p>Chinese: <b>停止工作</b></p>
     */
    public void stop() {
        if (timer != null) timer.cancel();
        timer = null;
    }

    /**
     * Exclude CPU
     * <p>Chinese: <b>排除CPU</b></p>
     */
    public Work excludeCPU() {
        this.collectionCPU = false;
        return this;
    }

    /**
     * Exclude Memory
     * <p>Chinese: <b>排除内存</b></p>
     */
    public Work excludeMemory() {
        this.collectionMemory = false;
        return this;
    }

    /**
     * Exclude Disk
     * <p>Chinese: <b>排除磁盘</b></p>
     */
    public Work excludeDisk() {
        this.collectionDisk = false;
        return this;
    }

    /**
     * Exclude Network
     * <p>Chinese: <b>排除网络</b></p>
     */
    public Work excludeNetwork() {
        this.collectionNetwork = false;
        return this;
    }

    /**
     * Set the location to save the log, it will not be saved if not set
     * <p>Chinese: <b>设置日志文件保存位置,不设置将不会保存日志</b></p>
     */
    public Work save(String path) {
        this.savePath = path;
        return this;
    }

    /**
     * Get the next minute
     * <p>Chinese: <b>获取下一分钟</b></p>
     */
    public Date getNextMinute() {
        Calendar calendar = Calendar.getInstance();
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.MINUTE, minute + 1);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

}