import java.util.Arrays;

public class ClassicMatrixMultiplaction {

    //transposes the matrix
    public static double[][] transposeMatrix(double [][] m){
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    //multiplies two matrices A and B and returns the result
    public static double[][] sequentialMultiply(double[][] A, double[][] B) {
        int n = A.length;
        double[][] result = new double[n][n];
        
        for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
                for(int k=0;k<n;k++){
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return result;
    }

    //transposes matrix a, multiplies it with matrix b and returns the result
    public static double[][] sequentialTransAMultiply(double[][] A, double[][] B) {
        int n = A.length;
        double[][] result = new double[n][n];
        A = transposeMatrix(A);

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                for(int k=0;k<n;k++) {
                    result[i][j] += A[k][i] * B[k][j];
                }
            }
        }
        return result;
    }


    //transposes matrix b, multiplies it with matrix a and returns the result
    public static double[][] sequentialTransBMultiply(double[][] A, double[][] B) {
        int n = A.length;
        double[][] result = new double[n][n];
        B = transposeMatrix(B);

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                for(int k=0;k<n;k++) {
                    result[i][j] += A[i][k] * B[j][k];
                }
            }
        }
        return result;
    }
}
