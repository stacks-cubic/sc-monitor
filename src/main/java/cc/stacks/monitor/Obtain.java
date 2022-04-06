package cc.stacks.monitor;

import cc.stacks.monitor.model.*;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Obtain monitoring data
 * <p>Chinese: <b>获取监控数据</b></p>
 *
 * @author Skay Zhang <small>( https://github.com/skay-zhang )</small>
 * @version 1.0.0
 */
public class Obtain extends TimerTask {

    /**
     * Use callback
     * <p>Chinese: <b>使用回调</b></p>
     */
    private final UseCallback callback;
    /**
     * Log file save location
     * <p>Chinese: <b>保存位置</b></p>
     */
    private final String savePath;
    /**
     * Collection CPU data
     * <p>Chinese: <b>采集处理器数据</b></p>
     */
    private final boolean collectionCPU;
    /**
     * Collection memory data
     * <p>Chinese: <b>采集内存数据</b></p>
     */
    private final boolean collectionMemory;
    /**
     * Collection disk data
     * <p>Chinese: <b>采集磁盘数据</b></p>
     */
    private final boolean collectionDisk;
    /**
     * Collection network data
     * <p>Chinese: <b>采集网络数据</b></p>
     */
    private final boolean collectionNetwork;
    /**
     * System info object
     * <p>Chinese: <b>系统信息对象</b></p>
     */
    private final SystemInfo systemInfo = new SystemInfo();
    /**
     * Datetime formatter object
     * <p>Chinese: <b>日期格式化对象</b></p>
     */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Default constructor (no callback, no save, collect all data)
     * <p>Chinese: <b>默认构造方法(无回调不保存,采集所有数据)</b></p>
     */
    public Obtain() {
        this.callback = null;
        this.savePath = null;
        this.collectionCPU = true;
        this.collectionMemory = true;
        this.collectionDisk = true;
        this.collectionNetwork = true;
    }

    /**
     * Construction methods for custom collection items
     * <p>Chinese: <b>自定义采集项目的构造方法</b></p>
     */
    public Obtain(boolean cpu, boolean memory, boolean disk, boolean network) {
        this.callback = null;
        this.savePath = null;
        this.collectionCPU = cpu;
        this.collectionMemory = memory;
        this.collectionDisk = disk;
        this.collectionNetwork = network;
    }

    /**
     * Construction method for customizing collection items and save locations
     * <p>Chinese: <b>自定义采集项目和保存位置的构造方法</b></p>
     */
    public Obtain(String savePath, boolean cpu, boolean memory, boolean disk, boolean network) {
        this.callback = null;
        this.savePath = savePath;
        this.collectionCPU = cpu;
        this.collectionMemory = memory;
        this.collectionDisk = disk;
        this.collectionNetwork = network;
    }

    /**
     * Construct methods for customizing collection items, save locations and callbacks
     * <p>Chinese: <b>自定义采集项目、保存位置和回调的构造方法</b></p>
     */
    public Obtain(UseCallback callback, String savePath, boolean cpu, boolean memory, boolean disk, boolean network) {
        this.callback = callback;
        this.savePath = savePath;
        this.collectionCPU = cpu;
        this.collectionMemory = memory;
        this.collectionDisk = disk;
        this.collectionNetwork = network;
    }

    @Override
    public void run() {
        UsePacker packer = syncCollectionData();
        if (callback != null) callback.receive(packer);
    }

    /**
     * Synchronized data collection
     * <p>Chinese: <b>同步采集数据</b></p>
     *
     * @return Using data wrappers
     */
    public UsePacker syncCollectionData(){
        UsePacker packer = new UsePacker();
        Logger logger = Logger.getLogger("Obtain");
        StringBuilder builder = new StringBuilder("{");
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        builder.append("\"time\":").append(System.currentTimeMillis()).append(",");
        getOneSecondData(packer, logger, hardware, builder);
        getSingleData(packer, logger, hardware, builder);
        save2File(logger, builder);
        return packer;
    }

    /**
     * Get one-second data
     * <p>Chinese: <b>获取1秒数据</b></p>
     * <p>Contains: <b>CPU、Disk、Network</b></p>
     *
     * @param logger   log object
     * @param hardware Hardware abstraction layer object
     * @param builder  Place the character square to be saved to the file
     */
    private void getOneSecondData(UsePacker packer, Logger logger, HardwareAbstractionLayer hardware, StringBuilder builder) {
        try {
            long[] prevTicks = hardware.getProcessor().getSystemCpuLoadTicks();
            List<HWDiskStore> prevDiskList = hardware.getDiskStores();
            List<NetworkIF> prevNetworkList = hardware.getNetworkIFs();
            TimeUnit.SECONDS.sleep(1);
            // Collection CPU data
            if (collectionCPU) {
                CpuUse cpuUse = new CpuUse(prevTicks, hardware.getProcessor().getSystemCpuLoadTicks());
                builder.append("\"cpu\":").append(cpuUse.getRateJSON()).append(",");
                logger.info(cpuUse.getRateString());
                packer.setCpu(cpuUse);
            }
            // Collection Disk data
            if (collectionDisk) {
                DiskUse diskUse = new DiskUse(prevDiskList, hardware.getDiskStores());
                builder.append("\"disk\":").append(diskUse.getIoJSON()).append(",");
                logger.info(diskUse.getIoString());
                packer.setDisk(diskUse);
            }
            // Collection Network data
            if (collectionNetwork) {
                NetworkUse networkUse = new NetworkUse(prevNetworkList, hardware.getNetworkIFs());
                builder.append("\"net\":").append(networkUse.getTransmissionJSON()).append(",");
                logger.info(networkUse.getTransmissionString());
                packer.setNetwork(networkUse);
            }
        } catch (Exception e) {
            logger.warning("Thread error, " + e.getMessage());
            // Restore interrupted
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Get a single data
     * <p>Chinese: <b>获取1次数据</b></p>
     * <p>Contains: <b>Memory</b></p>
     *
     * @param logger   log object
     * @param hardware Hardware abstraction layer object
     * @param builder  Place the character square to be saved to the file
     */
    private void getSingleData(UsePacker packer, Logger logger, HardwareAbstractionLayer hardware, StringBuilder builder) {
        // Collection Memory data
        if (collectionMemory) {
            MemoryUse memoryUse = new MemoryUse(hardware.getMemory());
            builder.append("\"memory\":").append(memoryUse.getRateJSON()).append(",");
            logger.info(memoryUse.getRateString());
            packer.setMemory(memoryUse);
        }
    }

    /**
     * Save log to file
     * <p>Chinese: <b>将日志保存到文件</b></p>
     *
     * @param logger  log object
     * @param builder Place the character square to be saved to the file
     */
    private void save2File(Logger logger, StringBuilder builder) {
        if (savePath != null && savePath.length() > 0) {
            String content = builder.toString();
            if (content.length() > 3) content = content.substring(0, content.length() - 1) + "}\n";
            String path = buildFilePath();
            File file = new File(path);
            try (FileWriter writer = new FileWriter(path, file.exists())) {
                writer.write(content);
            } catch (Exception e) {
                logger.warning("File writer error, " + e.getMessage());
            }
        }
    }

    /**
     * Generate the full file save path
     * <p>Chinese: <b>生成完整文件地址</b></p>
     *
     * @return file save path
     */
    private String buildFilePath() {
        int pathLength = savePath.length();
        String fileName = "scm-" + formatter.format(LocalDate.now()) + ".log";
        boolean division = savePath.lastIndexOf("/") != pathLength - 1;
        return (savePath + (division ? "/" : "")).replace("\\", "/") + fileName;
    }

}