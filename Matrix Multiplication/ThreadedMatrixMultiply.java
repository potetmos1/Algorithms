public class ThreadedMatrixMultiply {
    
    //transposes the matrix (can you parallellize this efficiently?)
    public static double[][] transposeMatrix(double [][] m){
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    public static double[][] multiplyMatrix(double[][] matrixA, double[][] matrixB, char type){
        int n = matrixA.length;
        int cores = Runtime.getRuntime().availableProcessors();
        int len_sek = n / cores;
        int start = 0;
        double[][] result = new double[n][n];

        if(type=='A'){
            matrixA = transposeMatrix(matrixA);
        }else if(type=='B'){
            matrixB = transposeMatrix(matrixB);
        }
        

        //making threads 
        Thread[] threads = new Thread[cores];
        for(int i=0; i<cores; i++) {
            int stop = start + len_sek;
            if(i == cores) {
                len_sek += n % cores;
            }
            
            threads[i] = new Thread(new MatrixThread(i, start, len_sek, type, matrixA, matrixB, result));
            start = stop;
            threads[i].start();
        }

        for(int i=0; i<cores; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
