import cc.stacks.monitor.NoCollectorException;
import cc.stacks.monitor.Work;

public class testWork {

    public static void main(String[] args) {
        try {
            Work work = new Work(10);
            work.run();
        } catch (NoCollectorException e) {
            // Minimum 1 collection item needs to be enabled!
        }
    }

}