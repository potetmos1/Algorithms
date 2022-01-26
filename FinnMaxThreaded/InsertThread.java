import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class InsertThread implements Runnable{
    CountDownLatch latch;
    FinnMaxMonitor monitor;
    public volatile int[] maxes;
    public int[] arr;
    public int min;

    public InsertThread(CountDownLatch latch, FinnMaxMonitor monitor, 
                        int[] maxes, int[] arr){
        this.latch = latch;
        this.monitor = monitor;
        this.maxes = maxes;
        this.arr = arr;
        this.min = maxes[maxes.length-1];
    }

    public void run(){
        for(int i=0; i<arr.length; i++){
            if(arr[i] > min){
                maxes = monitor.insert(min, maxes);
            }
            latch.countDown();
        }
    }  
}
