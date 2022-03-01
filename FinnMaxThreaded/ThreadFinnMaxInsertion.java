import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class ThreadFinnMaxInsertion {
    public static int[] finnMax(int[] arr, int k, int n){
        int[] maxes = new int[k];
        int cores = Runtime.getRuntime().availableProcessors();
        int perCore = n / cores;
        int rest = n % cores;
        int start = 0;
        int end;
        int[][] threadArrays = new int[cores][k];
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        Thread[] threads = new Thread[cores];

        //gives part of array to threads and starts them
        for(int i=0; i<cores; i++){
            end = start + perCore;
            if(i==0) end+=rest;
            threads[i] = new Thread(new InsertThread(
                        Arrays.copyOfRange(arr, start, end), threadArrays, k, i));
            threads[i].start();
            start = end;
        }

        //joining threads
        for(int i=0; i<cores; i++){
            try{
                threads[i].join();
            }catch(InterruptedException e){
                System.out.println("couldn't join thread");
            }
        }

        for(int i=0; i<cores; i++){
            for(int j=0; j<k-1; j++){
                pq.add(threadArrays[i][j]);
            }
        }
        for(int i=0; i<k; i++){
            maxes[i] = pq.remove();
        }
        return maxes;
    }
}
