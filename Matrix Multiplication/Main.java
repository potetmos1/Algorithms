import java.util.Arrays;
import java.io.FileWriter;
import java.nio.Buffer;
import java.io.BufferedWriter;

public class Main {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int seed = Integer.parseInt(args[1]);
        String mode = args[2];
        double[][] matrixA = Generator.generateMatrixA(seed, n);
        double[][] matrixB = Generator.generateMatrixB(seed, n);
        double[][] result = new double[n][n];
        long runtime = 0;

        long t0 = System.nanoTime();
        switch(mode) {
            case "seqN":
                result = ClassicMatrixMultiplaction.sequentialMultiply(matrixA, matrixB);
                Generator.saveResult(seed, Generator.Mode.SEQ_NOT_TRANSPOSED, result);
                break;
            case "seqA":
                result = ClassicMatrixMultiplaction.sequentialMultiply(matrixA, matrixB);
                Generator.saveResult(seed, Generator.Mode.SEQ_A_TRANSPOSED, result);
                break;
            case "seqB":
                result = ClassicMatrixMultiplaction.sequentialMultiply(matrixA, matrixB);
                Generator.saveResult(seed, Generator.Mode.SEQ_B_TRANSPOSED, result);
                break;
            case "paraN":
                result = ThreadedMatrixMultiply.multiplyMatrix(matrixA, matrixB, 'N');
                Generator.saveResult(seed, Generator.Mode.PARA_NOT_TRANSPOSED, result);
                break;
            case "paraA":
                result = ThreadedMatrixMultiply.multiplyMatrix(matrixA, matrixB, 'A');
                Generator.saveResult(seed, Generator.Mode.PARA_A_TRANSPOSED, result);
                break;
            case "paraB":
                result = ThreadedMatrixMultiply.multiplyMatrix(matrixA, matrixB, 'B');
                Generator.saveResult(seed, Generator.Mode.PARA_B_TRANSPOSED, result);
                break;
            case "test": // gets median time of 7 runs for all and writes to file
            try{
                long t1;
                long[] times = new long[7];
                String filename = "times"+n+".txt";
                FileWriter fw = new FileWriter(filename, false);
                BufferedWriter br = new BufferedWriter(fw);
                StringBuilder sb = new StringBuilder();
                
                
                for(int i=0; i<7; i++){ //seqN
                    t0 = System.nanoTime();
                    result = ClassicMatrixMultiplaction.sequentialMultiply(matrixA, matrixB);
                    Generator.saveResult(seed, Generator.Mode.SEQ_NOT_TRANSPOSED, result);
                    t1 = System.nanoTime();
                    runtime = t1-t0;
                    times[i] = runtime;
                    
                }
                write(br, sb, n, "seqN", times);
                
                for(int i=0; i<7; i++){ //seqA
                    t0 = System.nanoTime();
                    result = ClassicMatrixMultiplaction.sequentialMultiply(matrixA, matrixB);
                    Generator.saveResult(seed, Generator.Mode.SEQ_A_TRANSPOSED, result);
                    t1 = System.nanoTime();
                    runtime = t1-t0;
                    times[i] = runtime;
                }
                write(br, sb, n, "seqA", times);

                for(int i=0; i<7; i++){ //seqB
                    t0 = System.nanoTime();
                    result = ClassicMatrixMultiplaction.sequentialMultiply(matrixA, matrixB);
                    Generator.saveResult(seed, Generator.Mode.SEQ_B_TRANSPOSED, result);
                    t1 = System.nanoTime();
                    runtime = t1-t0;
                    times[i] = runtime;
                }
                write(br, sb, n, "seqB", times);

                for(int i=0; i<7; i++){ //parN
                    t0 = System.nanoTime();
                    result = ThreadedMatrixMultiply.multiplyMatrix(matrixA, matrixB, 'N');
                    Generator.saveResult(seed, Generator.Mode.PARA_NOT_TRANSPOSED, result);
                    t1 = System.nanoTime();
                    runtime = t1-t0;
                    times[i] = runtime;
                }
                write(br, sb, n, "parN", times);

                for(int i=0; i<7; i++){ //parA
                    t0 = System.nanoTime();
                    result = ThreadedMatrixMultiply.multiplyMatrix(matrixA, matrixB, 'A');
                    Generator.saveResult(seed,Generator.Mode.PARA_A_TRANSPOSED, result);
                    t1 = System.nanoTime();
                    runtime = t1-t0;
                    times[i] = runtime;
                }
                write(br, sb, n, "parA", times);

                for(int i=0; i<7; i++){ //parB
                    t0 = System.nanoTime();
                    result = ThreadedMatrixMultiply.multiplyMatrix(matrixA, matrixB, 'B');
                   Generator.saveResult(seed,Generator.Mode.PARA_B_TRANSPOSED, result);
                    t1 = System.nanoTime();
                    runtime = t1-t0;
                    times[i] = runtime;
                }
                write(br, sb, n, "parB", times);

                br.close();
                break;
            } catch(Exception e){
                System.out.println("Error: " + e);
                System.exit(1);
            }
            default:
                System.out.println("Invalid mode");
                System.exit(1);
        }
        long t1 = System.nanoTime();
        runtime = t1-t0;
        System.out.println("Runtime: " + runtime);
    }

    private static void write(BufferedWriter br, StringBuilder sb, int n, String mode, long[] times) {
        try{

        sb.append(mode);
        sb.append(", ");
        sb.append("n:" + n + " ");
        sb.append(" median: ");
        sb.append(times[3] +"ns");
        sb.append("\n");
        br.write(sb.toString());
        br.flush();
        sb.setLength(0);
        } catch(Exception e){
            System.out.println("write error");
            System.exit(1);
        }
    }
}



