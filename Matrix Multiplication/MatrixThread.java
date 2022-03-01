

public class MatrixThread implements Runnable {
    
    private int id, start, len, len_sek, stop;
    public char type;
    public double[][] matrixA, matrixB, matrixC;

    public MatrixThread(int id, int start, int len_sek, char type, double[][] matrixA, double[][] matrixB, double[][] matrixC) {
        this.id = id;
        this.start = start;
        this.type = type;
        this.len_sek = len_sek;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixC = matrixC;
    }

    @Override
    public void run() {
        len = matrixA.length;
        stop = start + len_sek;

        switch (type) {
            case 'N':
                for(int i = start; i < stop; i++) {
                    for(int j = 0; j < len; j++) {
                        for(int k = 0; k < len; k++) {
                            matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                        }
                    }
                }
                break;
            case 'A':
                for(int i = start; i < stop; i++) {
                    for(int j = 0; j < len; j++) {
                        for(int k = 0; k < len; k++) {
                            matrixC[i][j] += matrixA[k][i] * matrixB[k][j];
                        }
                    }
                }
                break;
            case 'B':
                for(int i = start; i < stop; i++) {
                    for(int j = 0; j < len; j++) {
                        for(int k = 0; k < len; k++) {
                            matrixC[i][j] += matrixA[i][k] * matrixB[j][k];
                        }
                    }
                }
                break;
            default:
                System.out.println("Invalid type");
                System.exit(1);
                break;
        }
    }
}
