import java.util.Arrays;
import java.util.Random;

public class Main {
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        char mode = args[2].charAt(0);
        long[] times = new long[7];
        long t0, t1;
        int[] arr = new int[n];
        Random r = new Random();
        
        if(k > n){
            System.out.println("k has to be smaller than n");
            System.exit(1);
        }    
        for(int i=0; i<arr.length; i++){
            arr[i] = r.nextInt(n);
        }

        switch(mode){
            case 'b': //Badsort
                System.out.println("Running badSort");
                for(int i=0; i<7; i++){
                    t0 = System.nanoTime();
                    int res[] = BadSort.badSort(arr, k);
                    System.out.println(Arrays.toString(res));
                    t1 = System.nanoTime();
                    times[i] = t1-t0;
                }
                break;
            case 'i': //insertion sort
            System.out.println("Running reverse insertion sort");
                for(int i=0; i<7; i++){
                    t0 = System.nanoTime();
                    int res[] = FinnMaxInsertion.finnMax(arr, k);
                    System.out.println(Arrays.toString(res));
                    t1 = System.nanoTime();
                    times[i] = t1-t0;
                }
                break;
            case 'p': //parrellized insertion sort
                System.out.println("Running parallized reverse insertion sort");
                for(int i=0; i<7; i++){
                    t0 = System.nanoTime();
                    int res[] = ThreadFinnMaxInsertion.finnMax(arr, k, n);
                    System.out.println(Arrays.toString(res));
                    t1 = System.nanoTime();
                    times[i] = t1-t0;
                }
                break;
            default:
                System.out.println("Not a valid mode, has to be 'b', 'i' or 'p'");
                System.exit(1);
        }
        Arrays.sort(times);
        String modestr = "badSort";
        if(mode == 'i'){modestr="reverse insertion sort";}
        if(mode == 'p'){modestr="parrellized reverse insertion sort";}
        System.out.println("Median time, for "+ modestr +": "+ times[4]);
    }
}