import java.util.Scanner;
import java.io.*;

public class Menu {

    static int determinantFile = 1, inverseFile = 1, gaussFile = 1, jordanFile = 1, cramerFile = 1, inverseMethodFile = 1, interpolationFile = 1, bicubicFile = 1, regressionFile = 1;
    public static void determinant() {
        System.out.println("---------Determinan Matriks---------");
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Metode pencarian determinan matriks");
        System.out.println("1. Metode Reduksi Baris\n2. Metode Kofaktor");
        int methodInput = inputScanner.nextInt();
        while (methodInput != 1 && methodInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi metode\n1. Metode Reduksi Baris\n2. Metode Kofaktor");
            methodInput = inputScanner.nextInt();
        }

        boolean reduksi = (methodInput == 1);

        System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");

        int choiceInput = inputScanner.nextInt();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");
            choiceInput = inputScanner.nextInt();
        }

        String filePath = "", tempFilePath = "";
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir" + "\\test");
            System.out.println("Masukkan nama file txt:");
            tempFilePath = filePath + inputScanner.nextLine();
            File inputFile = new File(tempFilePath);
            if (!inputFile.exists()) {
                boolean realFile = false;
                while (!realFile) {
                    System.out.println("File yang anda masukkan tidak ada");
                    System.out.println("Masukkan nama file txt yang valid: ");
                    tempFilePath = filePath + inputScanner.nextLine();
                    File tempInputFile = new File(tempFilePath);
                    realFile = tempInputFile.exists();
                }
            }
        }

        Matrix toFind;
        double determinant;
        if (!fromFile) {
            toFind = Matrix.readMatrix(false);

        } else {
            MatrixParser willInverse = new MatrixParser(tempFilePath, false, false);
            toFind = willInverse.getParsedMatrix();
        }

        if (reduksi) {
            determinant = Matrix.getDeterminantReduction(toFind);
        } else {
            determinant = Matrix.getCofactorDeterminant(toFind);
        }

        System.out.print("Determinan matriks: " + determinant);

    }

    public static void mainMenu() {
        System.out.println("++++++ MAIN MENU +++++++");
        Scanner choice = new Scanner(System.in);
        String mainMenuList = String.join(
            System.getProperty("line.separator"),
        "Silakan pilih menu :",
        "1. Determinan",
        "2. Matriks Balikan",
        "3. Sistem Persamaan Linier",
        "4. Interpolasi Linier",
        "5. Interpolasi Bicubic",
        "6. Regresi Linier Berganda",
        "7. Keluar",
        "\n"
        );
        System.out.println(mainMenuList);
        int mainMenuChoice = choice.nextInt();
        boolean notDone = true;
        while (notDone) {
            switch (mainMenuChoice) {
                case 1 -> determinant();
                case 2 -> inverseProcedure();
                case 3 -> LinearEquation();
                case 4 -> interpolationProcedure();
                case 5 -> bicubicProcedure();
                case 6 -> regressionProcedure();
                case 7 -> {
                    System.out.println("Program selesai");
                    notDone = false;
                }
                default -> System.out.println("Masukan salah!");
            }
        }

        choice.close();
    }

    public static void LinearEquation() {
        System.out.println("---------Sistem Persamaan Linier---------");
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Metode penyelesaian");
        System.out.println("1. Metode Gauss\n2. Metode Gauss-Jordan\n3. Metode Matriks Balikan\n4. Kaidah Cramer");
        int methodInput = inputScanner.nextInt();
        while (methodInput != 1 && methodInput != 2 && methodInput != 3 && methodInput != 4) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi metode\n1. Metode Gauss\n2. Metode Gauss-Jordan\n3. Metode Matriks Balikan\n4. Kaidah Cramer");
            methodInput = inputScanner.nextInt();
        }

        System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");

        int choiceInput = inputScanner.nextInt();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");
            choiceInput = inputScanner.nextInt();
        }

        String filePath = "", tempFilePath = "";
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir" + "\\test");
            System.out.println("Masukkan nama file txt:");
            tempFilePath = filePath + inputScanner.nextLine();
            File inputFile = new File(tempFilePath);
            if (!inputFile.exists()) {
                boolean realFile = false;
                while (!realFile) {
                    System.out.println("File yang anda masukkan tidak ada");
                    System.out.println("Masukkan nama file txt yang valid: ");
                    tempFilePath = filePath + inputScanner.nextLine();
                    File tempInputFile = new File(tempFilePath);
                    realFile = tempInputFile.exists();
                }
            }
        }

        AugmentedMatrix toSolve;
        Matrix A, B;
        String solution;
        if (!fromFile) {
            toSolve = Matrix.readSPL();
        } else {
            MatrixParser willInverse = new MatrixParser(tempFilePath, false, false);
            toSolve = willInverse.getParsedMatrix();
        }

        A = Matrix.disaugment(toSolve, true);
        B = Matrix.disaugment(toSolve, false);

        System.out.println("Solusi sistem persamaan linier: ");
        switch (methodInput) {
            case 1 -> {
                toSolve.setResultGauss(false);
                solution = toSolve.getDisplayableSolution();
                System.out.println(solution);
            }
            case 2 -> {
                toSolve.setResultGauss(true);
                solution = toSolve.getDisplayableSolution();
                System.out.println(solution);
            }
            case 3 -> Matrix.setResultInvers(A, B);
            case 4 -> Matrix.setResultCramer(A, B);
        }

    }

    public static void inverseProcedure() {
        System.out.println("---------Inverse Matrix---------");
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Metode invers matriks");
        System.out.println("1. Metode Gauss-Jordan\n2. Metode Kofaktor");
        int methodInput = inputScanner.nextInt();
        while (methodInput != 1 && methodInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi masukan\n1. Metode Gauss-Jordan\n2. Metode Kofaktor");
            methodInput = inputScanner.nextInt();
        }

        boolean gj = (methodInput == 1);

        System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");

        int choiceInput = inputScanner.nextInt();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");
            choiceInput = inputScanner.nextInt();
        }

        String filePath = "", tempFilePath = "";
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir" + "\\test");
            System.out.println("Masukkan nama file txt:");
            tempFilePath = filePath + inputScanner.nextLine();
            File inputFile = new File(tempFilePath);
            if (!inputFile.exists()) {
                boolean realFile = false;
                while (!realFile) {
                    System.out.println("File yang anda masukkan tidak ada");
                    System.out.println("Masukkan nama file txt yang valid: ");
                    tempFilePath = filePath + inputScanner.nextLine();
                    File tempInputFile = new File(tempFilePath);
                    realFile = tempInputFile.exists();
                }
            }
        }

        Matrix toInverse, inverted;
        if (!fromFile) {
            toInverse = Matrix.readMatrix(false);

        } else {
            MatrixParser willInverse = new MatrixParser(tempFilePath, false, false);
            toInverse = willInverse.getParsedMatrix();
        }

        if (gj) {
            inverted = Matrix.inverseGaussJordan(toInverse);
        } else {
            inverted = Matrix.inverseAdjoin(toInverse);
        }

        System.out.println("Matriks hasil invers: ");
        Matrix.displayMatrix(inverted);

    }

    public static void interpolationProcedure() {
        System.out.println("---------Interpolasi Linier---------");
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");

        int choiceInput = inputScanner.nextInt();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");
            choiceInput = inputScanner.nextInt();
        }

        String filePath = "", tempFilePath;
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir" + "\\test");
            System.out.println("Masukkan nama file txt:");
            tempFilePath = filePath + inputScanner.nextLine();
            File inputFile = new File(tempFilePath);
            if (!inputFile.exists()) {
                boolean realFile = false;
                while (!realFile) {
                    System.out.println("File yang anda masukkan tidak ada");
                    System.out.println("Masukkan nama file txt yang valid: ");
                    tempFilePath = filePath + inputScanner.nextLine();
                    File tempInputFile = new File(tempFilePath);
                    realFile = tempInputFile.exists();
                }
            }
        }

        Interpolation.runInterpolation(fromFile, filePath);
    }

    public static void bicubicProcedure() {
        System.out.println("---------Interpolasi Bicubic---------");
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");

        int choiceInput = inputScanner.nextInt();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");
            choiceInput = inputScanner.nextInt();
        }

        String filePath = "", tempFilePath;
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir" + "\\test");
            System.out.println("Masukkan nama file txt:");
            tempFilePath = filePath + inputScanner.nextLine();
            File inputFile = new File(tempFilePath);
            if (!inputFile.exists()) {
                boolean realFile = false;
                while (!realFile) {
                    System.out.println("File yang anda masukkan tidak ada");
                    System.out.println("Masukkan nama file txt yang valid: ");
                    tempFilePath = filePath + inputScanner.nextLine();
                    File tempInputFile = new File(tempFilePath);
                    realFile = tempInputFile.exists();
                }
            }
        }

        Bicubic.runBicubic(fromFile, filePath);
    }

    public static void regressionProcedure() {
        System.out.println("---------Regresi Linier Berganda---------");
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");

        int choiceInput = inputScanner.nextInt();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");
            choiceInput = inputScanner.nextInt();
        }

        String filePath = "", tempFilePath = "";
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir" + "\\test");
            System.out.println("Masukkan nama file txt:");
            tempFilePath = filePath + inputScanner.nextLine();
            File inputFile = new File(tempFilePath);
            if (!inputFile.exists()) {
                boolean realFile = false;
                while (!realFile) {
                    System.out.println("File yang anda masukkan tidak ada");
                    System.out.println("Masukkan nama file txt yang valid: ");
                    tempFilePath = filePath + inputScanner.nextLine();
                    File tempInputFile = new File(tempFilePath);
                    realFile = tempInputFile.exists();
                }
            }

        }

        Regression.runRegression(fromFile, tempFilePath);
    }

}
