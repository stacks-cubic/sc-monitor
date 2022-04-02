package cc.stacks.monitor.model;

import oshi.hardware.HWDiskStore;

import java.util.List;

/**
 * Disk usage data model
 * <p>Chinese: <b>磁盘使用数据模型</b></p>
 *
 * @author Skay Zhang <small>( https://github.com/skay-zhang )</small>
 * @version 1.0.0
 */
@SuppressWarnings("all")
public class DiskUse {

    /**
     * Disk read
     * <p>Chinese: <b>磁盘读取</b></p>
     * <p>Unit: <b>B/s</b></p>
     */
    private long read;

    /**
     * Disk write
     * <p>Chinese: <b>磁盘写入</b></p>
     * <p>Unit: <b>B/s</b></p>
     */
    private long write;

    public DiskUse(List<HWDiskStore> prevList, List<HWDiskStore> diskList) {
        long read = 0;
        long write = 0;
        for (HWDiskStore disk : prevList) {
            read -= disk.getReadBytes();
            write -= disk.getWriteBytes();
        }
        for (HWDiskStore disk : diskList) {
            read += disk.getReadBytes();
            write += disk.getWriteBytes();
        }
        this.read = read;
        this.write = write;
    }

    /**
     * Get JSON format io
     * <p>Chinese: <b>获取JSON格式的读写数据</b></p>
     *
     * @return JSON format io
     */
    public String getIoJSON() {
        return "{\"r\":" + this.read + ",\"w\":" + this.write + "}";
    }

    /**
     * Get string format io
     * <p>Chinese: <b>获取读写数据字符串</b></p>
     *
     * @return string format io
     */
    public String getIoString() {
        return "[Disk]\t\tRead: " + this.read + "B/s\t\tWrite: " + this.write + "B/s";
    }

    /**
     * Get disk read rate
     * <p>Chinese: <b>获取磁盘读取速率</b></p>
     *
     * @return Disk read rate
     */
    public long getRead() {
        return read;
    }

    /**
     * Get disk write rate
     * <p>Chinese: <b>获取磁盘写入速率</b></p>
     *
     * @return Disk write rate
     */
    public long getWrite() {
        return write;
    }

    @Override
    public String toString() {
        return "{\"r\":" + this.read + ",\"w\":" + this.write + "}";
    }

}