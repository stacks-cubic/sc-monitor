package cc.stacks.monitor.model;

/**
 * Use packer
 * <p>Chinese: <b>使用包装器</b></p>
 *
 * @author Skay Zhang <small>( https://github.com/skay-zhang )</small>
 * @version 1.0.0
 */
@SuppressWarnings("all")
public class UsePacker {

    /**
     * CPU usage data model
     * <p>Chinese: <b>CPU使用数据模型</b></p>
     */
    private CpuUse cpu;
    /**
     * Disk usage data model
     * <p>Chinese: <b>磁盘使用数据模型</b></p>
     */
    private DiskUse disk;
    /**
     * Memory usage data model
     * <p>Chinese: <b>内存使用数据模型</b></p>
     */
    private MemoryUse memory;
    /**
     * Network usage data model
     * <p>Chinese: <b>网络使用数据模型</b></p>
     */
    private NetworkUse network;

    public CpuUse getCpu() {
        return cpu;
    }

    public void setCpu(CpuUse cpu) {
        this.cpu = cpu;
    }

    public DiskUse getDisk() {
        return disk;
    }

    public void setDisk(DiskUse disk) {
        this.disk = disk;
    }

    public MemoryUse getMemory() {
        return memory;
    }

    public void setMemory(MemoryUse memory) {
        this.memory = memory;
    }

    public NetworkUse getNetwork() {
        return network;
    }

    public void setNetwork(NetworkUse network) {
        this.network = network;
    }
}