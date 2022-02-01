import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.*;

public class ThreadFinnMaxInsertion {
    
    public static int[] finnMax(int[] arr, int k, int n){
        int maxes[] = new int[k];
        int min;

        //places n first numbers into array
            maxes = Arrays.copyOfRange(arr, 0, k);

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
        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        int[] arr = new int[n];
        Random r = new Random();
        long[] times = new long[7];
        
        System.out.println("Available cores: " + cores);

        for(int i=0; i<7; i++){
            for(int l=0; l<arr.length; l++){
                arr[l] = r.nextInt(n);
            }
            long t0 = System.nanoTime();
            int[] maxes = finnMax(arr, k, n);
            long t1 = System.nanoTime();
            long runtime = t1-t0;
            times[i] = runtime;
            System.out.println(Arrays.toString(maxes));
        }
        
        Arrays.sort(times);
        System.out.println(Arrays.toString(times));
        System.out.println("Median: " + times[3]);
    }
}
