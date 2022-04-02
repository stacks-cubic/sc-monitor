package cc.stacks.monitor.model;

import oshi.hardware.GlobalMemory;

import java.text.DecimalFormat;

/**
 * Memory usage data model
 * <p>Chinese: <b>内存使用数据模型</b></p>
 *
 * @author Skay Zhang <small>( https://github.com/skay-zhang )</small>
 * @version 1.0.0
 */
@SuppressWarnings("all")
public class MemoryUse {

    /**
     * Available Memory
     * <p>Chinese: <b>可用内存</b></p>
     */
    private long AVAILABLE;

    /**
     * Total Memory
     * <p>Chinese: <b>内存总和</b></p>
     */
    private long TOTAL;

    public MemoryUse(GlobalMemory memory) {
        // 可用内存
        this.AVAILABLE = memory.getAvailable();
        // 总内存
        this.TOTAL = memory.getTotal();
    }

    /**
     * Get Memory usage
     * <p>Chinese: <b>获取内存使用量</b></p>
     *
     * @return Memory usage
     */
    public long getUsageNumber() {
        return this.TOTAL - this.AVAILABLE;
    }

    /**
     * Get Memory usage rate
     * <p>Chinese: <b>获取内存使用率</b></p>
     *
     * @return Memory usage rate
     */
    public String getUsageRate() {
        return new DecimalFormat("#.##").format(this.getUsageNumber() * 1.0 / this.TOTAL);
    }

    /**
     * Get JSON format rate
     * <p>Chinese: <b>获取JSON格式的使用率数据</b></p>
     *
     * @return JSON format rate
     */
    public String getRateJSON() {
        return "{\"number\":" + this.getUsageNumber() + ",\"rate\":" + this.getUsageRate() + "}";
    }

    /**
     * Get string format rate
     * <p>Chinese: <b>获取使用率数据字符串</b></p>
     *
     * @return string format rate
     */
    public String getRateString() {
        return "[Memory]\tUsageNumber: " + this.getUsageNumber() + "B\t\tUsageRate: " + this.getUsageRate()+"%";
    }

    @Override
    public String toString() {
        return "{\"AVAILABLE\":" + this.AVAILABLE + ",\"TOTAL\":" + this.TOTAL + "}";
    }

}