import java.util.concurrent.locks.*;

public class FinnMaxMonitor {
    public int[] maxes;
    public int min;
    Lock lock = new ReentrantLock();
    
    public int[] insert(int num, int[] arr){
        lock.lock();
        try{
            this.maxes = arr;
            this.min = num;

            for(int i=0; i<arr.length; i++){
                int j = maxes.length -2;
                while(min > arr[j] && j>0){
                    maxes[j+1] = maxes[j];
                    j--;
                }
                maxes[j+1] = min;
            }
        }finally{
            lock.unlock();
        }
        return maxes;
    }

    public int[] getMaxes(){
        return maxes;
    }

    public int getMin(){
        return min;
    }
}
