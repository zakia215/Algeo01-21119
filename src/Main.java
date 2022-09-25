public class Main {
    public static void main(String[] args) {
        // String filePath = "D:\\ITB\\Semester 3\\Aljabar Liniear dan Geometri\\Algeo01-21119\\test\\input_3a.txt";
        // MatrixParser testInput = new MatrixParser(filePath);
        // AugmentedMatrix testResult = testInput.getParsedMatrix();
//        String[] a = new String[] {"0", "-2", "s", "3", "t"};
//        String[] b = new String[] {"3", "5", "s", "10", "t"};
//        b = AugmentedMatrix.constParametric(b, 2, -1);
//        String[] c;
//        c = AugmentedMatrix.addParametric(a, b, 2);
//        for (int i = 0; i < 5; i++) {
//            System.out.print(c[i] + " ");
//        }
        // System.out.println();
        // testResult.setResultGauss(true);
        // for (int i = 0; i < testResult.getColNum() - 1; i++) {
        //     if (i != 0) {
        //         System.out.println();
        //     }
        //     for (int j = 0; j < 5; j++) {
        //         if (j != 0) {
        //             System.out.print(" ");
        //         }
        //         System.out.print(testResult.getSolution()[i][j]);
        //     }
        // }
        // System.out.println(testResult.getDisplayableSolution());
        Matrix A = Matrix.readMatrix(true);
        // Matrix B = Matrix.readMatrix(true);
        // AugmentedMatrix C = Matrix.augment(A, B);
        // AugmentedMatrix.displayMatrix(C);
        // Matrix.displayMatrix(res);
        // System.out.println(A.getElement(0, 0));
        // if (AugmentedMatrix.isThereAllZeroButLastOne(C)) {
        //     System.out.println("anjay");
        // }
        Matrix.displayMatrix(Matrix.inverseGaussJordan(A));
    }
}
