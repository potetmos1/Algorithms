import java.util.Arrays;
public class InsertThread implements Runnable{
    private int[] arr;
    private int[][] threadArrays;
    private int k, num;

    public InsertThread(int[] arr, int[][]threadArrays, int k, int num){
        this.arr = arr;
        this.k = k;
        this.threadArrays = threadArrays;
        this.num = num;
    }
    
    public void run(){
        //for the first elements
        for(int i=1; i<k; i++){
            int key = i;
            int j = i-1;

            while(j >= 0 && arr[key] > arr[j]){
                insert(arr, key, j);
                key--;
                --j;
            }
        }
        
        //sorting array
        for(int i=k -1; i<arr.length; i++){
            if(arr[i] > arr[k-1]){
                insert(arr, i, k-1);
                int pos1 = k -1;
                int pos2 = pos1-1;
                while(pos2 >= 0 && arr[pos1] > arr[pos2]){
                    insert(arr, pos1, pos2);
                    pos1--;
                    pos2--;
                }
            }
        }
        threadArrays[num] = Arrays.copyOfRange(arr, 0, k-1);
    }
    

    private static void insert(int[] arr, int pos1, int pos2){
        int tmp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = tmp;
    }
}
