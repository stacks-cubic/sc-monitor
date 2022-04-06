package cc.stacks.monitor;

import cc.stacks.monitor.model.UsePacker;

/**
 * Use callback
 * <p>Chinese: <b>使用回调</b></p>
 *
 * @author Skay Zhang <small>( https://github.com/skay-zhang )</small>
 * @version 1.0.0
 */
public interface UseCallback {

    /**
     * Receiving data
     * <p>Chinese: <b>接收数据</b></p>
     *
     * @param packer Use packer
     */
    void receive(UsePacker packer);

}