public class Main {



    public static void main(String[] args) {
         String filePath = "D:\\ITB\\Semester 3\\Aljabar Liniear dan Geometri\\Algeo01-21119\\test\\input_4.txt";
         MatrixParser testInput = new MatrixParser(filePath);
         Matrix yBicubic = testInput.getBicubicY(true);
         Matrix xBicubic = Bicubic.getBicubicX();
//        Matrix A = Matrix.readMatrix(true);
//        Matrix B = Matrix.inverseGaussJordan(A);
//        Matrix.displayMatrix(B);
//         AugmentedMatrix testGauss = Matrix.augment(xBicubic, yBicubic);
//         testGauss.setResultGauss(true);
//        System.out.println(testGauss.getDisplayableSolution());
         double[] a = Bicubic.getCoefficient(xBicubic, yBicubic);
         double testA, testB, testC, testD;
//
         testA = Bicubic.predictBicubicValue(0, 0, a);
        testB = Bicubic.predictBicubicValue(0.5, 0.5, a);
        testC = Bicubic.predictBicubicValue(0.25, 0.75, a);
        testD = Bicubic.predictBicubicValue(0.1, 0.9, a);
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
//        Matrix A = Matrix.readMatrix(false);
//        Matrix B = Matrix.readMatrix(true);
//        AugmentedMatrix C = Matrix.augment(A, B);
//        AugmentedMatrix.displayMatrix(C);
//        // Matrix.displayMatrix(res);
//        // System.out.println(A.getElement(0, 0));
//        if (AugmentedMatrix.isThereAllZeroButLastOne(C)) {
//            System.out.println("anjay");
//        }

        for (int i = 0; i < 16; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();

        System.out.println(testA);
        System.out.println(testB);
        System.out.println(testC);
        System.out.println(testD);

    }
}
