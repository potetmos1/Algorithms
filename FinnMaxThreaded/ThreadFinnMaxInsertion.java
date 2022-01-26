import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.*;

public class ThreadFinnMaxInsertion {
    
    public static int[] finnMax(int[] arr, int n){
        int maxes[] = new int[n];
        int min;

        //places n first numbers into array
            maxes = Arrays.copyOfRange(arr, 0, n);

        //reverse insertion sort maxes
        for(int i=1; i<maxes.length; i++){
            int key = maxes[i];
            int j = i-1;

            while(j >= 0 && key > maxes[j]){
                maxes[j+1] = maxes[j];
                --j;
            }
            maxes[j + 1] = key;
        }
        min = maxes[maxes.length-1];
        
        //one item insertion sort with threads
        int cores = Runtime.getRuntime().availableProcessors();
        int perCore = cores / n;
        int rest = cores % n;
        int end;
        CountDownLatch latch = new CountDownLatch(cores);
        FinnMaxMonitor monitor = new FinnMaxMonitor();

        for(int i=0; i<cores;){
            if(i==0){
                end = i + perCore + rest;
            }else{
                end = i + perCore;
            }
            int[] coreArr = Arrays.copyOfRange(arr, i, end);
            InsertThread it = new InsertThread(latch, monitor, maxes, coreArr);
            Thread t = new Thread(it);
            t.start();
            i += end;
        }

        try{
            latch.await();
        }catch(InterruptedException e){
            System.out.println("Feil");
        }
        return maxes;
    }

    public static void main(String[] args) {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Available cores: " + cores);
        int[] arr = new int[Integer.parseInt(args[1])];
        Random r = new Random();

        for(int i=0; i<arr.length; i++){
            arr[i] = r.nextInt(Integer.parseInt(args[1]));
        }
        int[] maxes = finnMax(arr, Integer.parseInt(args[0]));
        System.out.println(Arrays.toString(maxes));
    }
}
