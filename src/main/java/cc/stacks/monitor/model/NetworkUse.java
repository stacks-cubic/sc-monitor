package cc.stacks.monitor.model;

import oshi.hardware.NetworkIF;

import java.util.List;

/**
 * Network usage data model
 * <p>Chinese: <b>网络使用数据模型</b></p>
 *
 * @author Skay Zhang <small>( https://github.com/skay-zhang )</small>
 * @version 1.0.0
 */
@SuppressWarnings("all")
public class NetworkUse {

    /**
     * Network upload
     * <p>Chinese: <b>网络上传</b></p>
     * <p>Unit: <b>B/s</b></p>
     */
    private long upload;
    /**
     * Network download
     * <p>Chinese: <b>网络下载</b></p>
     * <p>Unit: <b>B/s</b></p>
     */
    private long download;

    public NetworkUse(List<NetworkIF> prevList, List<NetworkIF> IFList) {
        long upload = 0;
        long download = 0;
        for (NetworkIF net : prevList) {
            upload -= net.getBytesSent();
            download -= net.getBytesRecv();
        }
        for (NetworkIF net : IFList) {
            upload += net.getBytesSent();
            download += net.getBytesRecv();
        }
        this.upload = upload;
        this.download = download;
    }

    /**
     * Get JSON format transmission
     * <p>Chinese: <b>获取JSON格式的传输数据</b></p>
     *
     * @return JSON format transmission
     */
    public String getTransmissionJSON() {
        return "{\"up\":" + this.upload + ",\"down\":" + this.download + "}";
    }

    /**
     * Get string format transmission
     * <p>Chinese: <b>获取传输数据字符串</b></p>
     *
     * @return string format transmission
     */
    public String getTransmissionString() {
        return "[Network]\tUpload: " + this.upload + "B/s\t\tDownload: " + this.download+"B/s";
    }

    /**
     * Get network upload speed
     * <p>Chinese: <b>获取网络上传速率</b></p>
     *
     * @return Network upload speed
     */
    public long getUpload() {
        return upload;
    }

    /**
     * Get network download speed
     * <p>Chinese: <b>获取网络下载速率</b></p>
     *
     * @return Network download speed
     */
    public long getDownload() {
        return download;
    }

    @Override
    public String toString() {
        return "{\"up\":" + this.upload + ",\"down\":" + this.download + "}";
    }
}
