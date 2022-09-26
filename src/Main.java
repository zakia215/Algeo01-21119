public class Main {
    public static void main(String[] args) {
        System.out.println("ini main");
        Matrix A = Matrix.readMatrix(false);
        Matrix B = Matrix.readMatrix(true);
        Matrix.displayMatrix(Matrix.setResultCramer(A, B));
    }
   
}
