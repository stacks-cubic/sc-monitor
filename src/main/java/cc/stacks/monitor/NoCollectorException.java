package cc.stacks.monitor;

/**
 * No collector exception
 * <p>Chinese: <b>无可用采集器</b></p>
 *
 * @author Skay Zhang <small>( https://github.com/skay-zhang )</small>
 * @version 1.0.0
 */
public class NoCollectorException extends Exception{

    public NoCollectorException(String msg){
        super(msg);
    }

}