public class Main {

    public static void displayMenu() {
        String choice;

        System.out.println("Menu");
        System.out.println("1. Sistem Persamaan Linier");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks Balikan");
        System.out.println("4. Interpolasi Polinom");
        System.out.println("5. Interpolasi Bicubic");
        System.out.println("6. Regresi Linier Berganda");
        System.out.println("7. Keluar");
        System.out.println("----------------------------------");
        System.out.print("Pilihan Menu: ");
    }

    public static void main(String[] args) {
        String filePath = "D:\\ITB\\Semester 3\\Aljabar Liniear dan Geometri\\Algeo01-21119\\test\\input_0c.txt";
        MatrixParser testInput = new MatrixParser(filePath);
        AugmentedMatrix regData = testInput.getParsedMatrix();

        regData.setResultGauss(false);
        System.out.println(regData.getDisplayableSolution());

//        AugmentedMatrix linReg = testInput.getRegressionMatrix();

//        linReg.setResultGauss(true);
//
//        double[] coefficient = Regression.getBeta(linReg, regData);
//        String toPrint = Regression.getRegressionEquation(coefficient);
//        System.out.println(toPrint);
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



    }
}
