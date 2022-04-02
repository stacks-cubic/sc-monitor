import cc.stacks.monitor.Work;

public class testWork {

    public static void main(String[] args) {
        System.out.println("Work init");
        Work work = new Work(10);
        System.out.println("Work run");
        work.save("./").run();
    }

}