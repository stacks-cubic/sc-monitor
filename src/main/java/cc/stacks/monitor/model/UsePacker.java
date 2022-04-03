package cc.stacks.monitor.model;

public class UsePacker {

    private CpuUse cpu;
    private DiskUse disk;
    private MemoryUse memory;
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