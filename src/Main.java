public class Main {
    public static void main(String[] args) {
        Matrix A = Matrix.readMatrix();
        Matrix.displayMatrix(A);
        Matrix.toEchelon(A, true);
        Matrix.displayMatrix(A);
    }
}
