public class Main {
    public static void main(String[] args) {
        Matrix A = Matrix.readMatrix();
        double detA = Matrix.getDeterminantReduction(A);
        System.out.println(detA);
        Matrix.toUpperTriangle(A);
        Matrix.displayMatrix(A);
    }
}
