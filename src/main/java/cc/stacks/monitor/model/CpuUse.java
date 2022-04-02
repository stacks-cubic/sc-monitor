package cc.stacks.monitor.model;

import oshi.hardware.CentralProcessor;

import java.text.DecimalFormat;

/**
 * CPU usage data model
 * <p>Chinese: <b>CPU使用数据模型</b></p>
 *
 * @author Skay Zhang <small>( https://github.com/skay-zhang )</small>
 * @version 1.0.0
 */
@SuppressWarnings("all")
public class CpuUse {

    /**
     * CPU idle time not caused by IO
     * <p>Chinese: <b>CPU闲置时间(非IO)</b></p>
     */
    private long IDLE;
    /**
     * CPU idle time due to IO
     * <p>Chinese: <b>CPU闲置时间(IO导致)</b></p>
     */
    private long IO_WAIT;
    /**
     * CPU hardware IRQ
     * <p>Chinese: <b>CPU硬中断时间</b></p>
     */
    private long IRQ;
    /**
     * CPU soft IRQ
     * <p>Chinese: <b>CPU软中断时间</b></p>
     */
    private long SOFT_IRQ;
    /**
     * Handling the CPU time of virtual machine processes
     * <p>Chinese: <b>CPU占用时间(处理虚拟机进程)</b></p>
     */
    private long STEAL;
    /**
     * Handling the CPU time of system processes
     * <p>Chinese: <b>CPU占用时间(处理系统进程)</b></p>
     */
    private long SYSTEM;
    /**
     * Handling CPU time of user processes
     * <p>Chinese: <b>CPU占用时间(处理用户进程)</b></p>
     */
    private long USER;
    /**
     * Handling CPU time of high priority user processes
     * <p>Chinese: <b>CPU占用时间(处理高优先级用户进程)</b></p>
     */
    private long NICE;
    /**
     * Total CPU clock
     * <p>Chinese: <b>CPU时钟总和</b></p>
     */
    private long TOTAL;

    public CpuUse(long[] prevTicks, long[] ticks) {
        this.IDLE = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        this.IO_WAIT = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        this.IRQ = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        this.SOFT_IRQ = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        this.STEAL = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        this.SYSTEM = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        this.USER = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        this.NICE = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        this.TOTAL = USER + NICE + SYSTEM + IDLE + IO_WAIT + IRQ + SOFT_IRQ + STEAL;
    }

    /**
     * Get CPU system usage
     * <p>Chinese: <b>获取CPU系统使用率</b></p>
     *
     * @return CPU system usage
     */
    public String getSystemUsage() {
        return new DecimalFormat("#.##").format(this.SYSTEM * 1.0 / this.TOTAL);
    }

    /**
     * Get CPU user usage
     * <p>Chinese: <b>获取CPU用户使用率</b></p>
     *
     * @return CPU user usage
     */
    public String getUserUsage() {
        return new DecimalFormat("#.##").format(this.USER * 1.0 / this.TOTAL);
    }

    /**
     * Get CPU usage rate
     * <p>Chinese: <b>获取CPU使用率</b></p>
     *
     * @return CPU usage rate
     */
    public String getUsageRate() {
        return new DecimalFormat("#.##").format(1.0 - (this.IDLE * 1.0 / this.TOTAL));
    }

    /**
     * Get the CPU idle rate due to IO
     * <p>Chinese: <b>获取CPU闲置率(因IO导致)</b></p>
     *
     * @return CPU io wait rate
     */
    public String getIoWaitRate() {
        return new DecimalFormat("#.##").format(this.IO_WAIT * 1.0 / this.TOTAL);
    }

    /**
     * Get JSON format rate
     * <p>Chinese: <b>获取JSON格式的使用率数据</b></p>
     *
     * @return JSON format rate
     */
    public String getRateJSON() {
        return "{\"sys\":" + this.getSystemUsage() + ",\"user\":" + this.getUserUsage() + ",\"use\":" + this.getUsageRate() + ",\"io\":" + this.getIoWaitRate() + "}";
    }

    /**
     * Get string format rate
     * <p>Chinese: <b>获取使用率数据字符串</b></p>
     *
     * @return string format rate
     */
    public String getRateString() {
        return "[Cpu]\t\tSystemUsage: " + this.getSystemUsage() + "%\t\tUserUsage: " + this.getUserUsage() + "%\t\tUtilization: " + this.getUsageRate() + "%\t\tIoWaitRate: " + this.getIoWaitRate()+"%";
    }

    @Override
    public String toString() {
        return "{\"IDLE\":" + this.IDLE + ",\"IO_WAIT\":" + this.IO_WAIT + ",\"IRQ\":" + this.IRQ + ",\"SOFT_IRQ\":" + this.SOFT_IRQ + ",\"STEAL\":" + this.STEAL + ",\"SYSTEM\":" + this.SYSTEM + ",\"USER\":" + this.USER + ",\"NICE\":" + this.NICE + ",\"TOTAL\":" + this.TOTAL + "}";
    }

}