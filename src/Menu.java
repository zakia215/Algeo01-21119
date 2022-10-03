import java.util.Scanner;
import java.io.*;

public class Menu {
    
    static int determinantFile = 1, inverseFile = 1, gaussFile = 1, jordanFile = 1, cramerFile = 1, inverseMethodFile = 1, interpolationFile = 1, bicubicFile = 1, regressionFile = 1;
    
    public static void mainMenu() {
        System.out.println("|-|-|-|-|-|-|-|     SELAMAT DATANG DI MATRIX CALC++     |-|-|-|-|-|-|-|");
        Scanner choice = new Scanner(System.in);
        String mainMenuList = String.join(
            System.getProperty("line.separator"),
            "\n---------MAIN MENU---------",
        "Silakan pilih menu :",
        "1. Determinan",
        "2. Matriks Balikan",
        "3. Sistem Persamaan Linier",
        "4. Interpolasi Linier",
        "5. Interpolasi Bicubic",
        "6. Regresi Linier Berganda",
        "7. Memperbesar Gambar",
        "8. Keluar",
        "Pilihan : "
        );
        boolean notDone = true;
        while (notDone) {
            System.out.print(mainMenuList);
            int mainMenuChoice = choice.nextInt();
            choice.nextLine();
            switch (mainMenuChoice) {
                case 1 -> determinantProcedure(choice);
                case 2 -> inverseProcedure(choice);
                case 3 -> LinearEquation(choice);
                case 4 -> interpolationProcedure(choice);
                case 5 -> bicubicProcedure(choice);
                case 6 -> regressionProcedure(choice);
                case 7 -> scalingImageProcedure(choice);
                case 8 -> {
                    System.out.println("PROGRAM SELESAI. HATUR SUWUN !");
                    notDone = false;
                }
                default -> System.out.println("Masukan salah!");
            }
        }
        choice.close();
    }

    public static void determinantProcedure(Scanner globalScanner) {
        System.out.println("\n---------Determinan Matriks---------");
        System.out.println("Metode pencarian determinan matriks");
        System.out.print("1. Metode Reduksi Baris\n2. Metode Kofaktor\nPilihan : ");
        int methodInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (methodInput != 1 && methodInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.print(">>> Pilih opsi metode\n1. Metode Reduksi Baris\n2. Metode Kofaktor\nPilihan : ");
            methodInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        boolean reduksi = (methodInput == 1);

        System.out.print(">>> Pilih opsi masukan\n1. File\n2. Terminal\nPilihan : ");

        int choiceInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.print(">>> Pilih opsi masukan\n1. File\n2. Terminal\nPilihan : ");
            choiceInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        boolean fromFile = (choiceInput == 1);
        String filePath = "", tempFilePath = "";
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir") + "\\test\\";
            tempFilePath = getFilePath(globalScanner, filePath);
        }

        Matrix toFind;
        if (!fromFile) {
            toFind = Matrix.readMatrix(globalScanner);

        } else {
            MatrixParser willInverse = new MatrixParser(tempFilePath, false, false);
            toFind = willInverse.getParsedMatrix();
        }

        if (reduksi) {
            Matrix.getDeterminantReduction(toFind, true);
        } else {
            Matrix.getCofactorDeterminant(toFind, true);
        }


    }

    public static String getFilePath(Scanner globalScanner, String fileDir) {
        String tempFilePath = "", fileName;

        System.out.print("Masukkan nama file txt: ");
        fileName = globalScanner.nextLine();
        tempFilePath = fileDir + fileName;
        File testFile = new File(tempFilePath);
        if (!testFile.exists()) {
            boolean exist = false;
            while (!exist) {
                System.out.println("File yang anda masukkan tidak ada");
                System.out.print("Masukkan nama file yang valid: ");
                fileName = globalScanner.nextLine();
                tempFilePath = fileDir + fileName;
                File fileToInput = new File(tempFilePath);
                exist = fileToInput.exists();
            }
        }

        return tempFilePath;
    }


    public static void LinearEquation(Scanner globalScanner) {
        System.out.println("\n---------Sistem Persamaan Linier---------");
        System.out.println("Metode penyelesaian");
        System.out.print("1. Metode Gauss\n2. Metode Gauss-Jordan\n3. Metode Matriks Balikan\n4. Kaidah Cramer\nPilihan : ");
        int methodInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (methodInput != 1 && methodInput != 2 && methodInput != 3 && methodInput != 4) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.print(">>> Pilih opsi metode\n1. Metode Gauss\n2. Metode Gauss-Jordan\n3. Metode Matriks Balikan\n4. Kaidah Cramer\nPilihan : ");
            methodInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        System.out.print(">>> Pilih opsi masukan\n1. File\n2. Terminal\nPilihan : ");

        int choiceInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.print(">>> Pilih opsi masukan\n1. File\n2. Terminal\nPilihan : ");
            choiceInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        String filePath = "", tempFilePath = "";
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir") + "\\test\\";
            tempFilePath = getFilePath(globalScanner, filePath);
        }

        AugmentedMatrix toSolve;
        Matrix A, B;
        String solution;
        if (!fromFile) {
            toSolve = Matrix.readSPL(globalScanner);
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
            case 3 -> Matrix.setResultInvers(A, B, true);
            case 4 -> Matrix.setResultCramer(A, B);
        }

    }

    public static void inverseProcedure(Scanner globalScanner) {
        System.out.println("\n---------Inverse Matrix---------");
        System.out.println("Metode invers matriks");
        System.out.print("1. Metode Gauss-Jordan\n2. Metode Kofaktor\nPilihan : ");
        int methodInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (methodInput != 1 && methodInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.print(">>> Pilih opsi masukan\n1. Metode Gauss-Jordan\n2. Metode Kofaktor\nPilihan : ");
            methodInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        boolean gj = (methodInput == 1);

        System.out.print(">>> Pilih opsi masukan\n1. File\n2. Terminal\nPilihan : ");

        int choiceInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.print(">>> Pilih opsi masukan\n1. File\n2. Terminal\nPilihan : ");
            choiceInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        String filePath = "", tempFilePath = "", fileName;
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir") + "\\test\\";
            System.out.print("Masukkan nama file txt: ");
            fileName = globalScanner.nextLine();
            tempFilePath = filePath + fileName;
            File testFile = new File(tempFilePath);

            boolean exist = testFile.exists();
            while (!exist) {
                System.out.println("File yang anda masukkan tidak ada");
                System.out.print("Masukkan nama file yang valid: ");
                fileName = globalScanner.nextLine();
                tempFilePath = filePath + fileName;
                File fileToInput = new File(tempFilePath);
                exist = fileToInput.exists();
            }
        }

        Matrix toInverse, inverted;
        if (!fromFile) {
            toInverse = Matrix.readMatrix(globalScanner);
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

    public static void interpolationProcedure(Scanner globalScanner) {
        System.out.println("\n---------Interpolasi Linier---------");
        System.out.print(">>> Pilih opsi masukan\n1. File\n2. Terminal\nPilihan : ");

        int choiceInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.print(">>> Pilih opsi masukan\n1. File\n2. Terminal\nPilihan : ");
            choiceInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        String filePath = "", tempFilePath = "", fileName;
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir") + "\\test\\";
            System.out.print("Masukkan nama file txt: ");
            fileName = globalScanner.nextLine();
            tempFilePath = filePath + fileName;
            File testFile = new File(tempFilePath);

            boolean exist = testFile.exists();
            while (!exist) {
                System.out.println("File yang anda masukkan tidak ada");
                System.out.print("Masukkan nama file yang valid: ");
                fileName = globalScanner.nextLine();
                tempFilePath = filePath + fileName;
                File fileToInput = new File(tempFilePath);
                exist = fileToInput.exists();
            }
        }

        Interpolation.runInterpolation(fromFile, tempFilePath);
    }

    public static void bicubicProcedure(Scanner globalScanner) {
        System.out.println("\n---------Interpolasi Bicubic---------");
        System.out.print(">>> Pilih opsi masukan\n1. File\n2. Terminal\nPilihan : ");

        int choiceInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.print(">>> Pilih opsi masukan\n1. File\n2. Terminal\nPilihan : ");
            choiceInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        String filePath = "", tempFilePath = "", fileName = "";
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir") + "\\test\\";
            System.out.print("Masukkan nama file txt: ");
            fileName = globalScanner.nextLine();
            tempFilePath = filePath + fileName;
            File testFile = new File(tempFilePath);

            boolean exist = testFile.exists();
            while (!exist) {
                System.out.println("File yang anda masukkan tidak ada");
                System.out.print("Masukkan nama file yang valid: ");
                fileName = globalScanner.nextLine();
                tempFilePath = filePath + fileName;
                File fileToInput = new File(tempFilePath);
                exist = fileToInput.exists();
            }
        }

        Bicubic.runBicubic(fromFile, tempFilePath, globalScanner);
    }

    public static void regressionProcedure(Scanner globalScanner) {
        System.out.println("\n---------Regresi Linier Berganda---------");
        System.out.print(">>>>>> Pilih opsi masukan\n1. File\n2. Terminal\nPilihan : ");

        int choiceInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.print(">>> Pilih opsi masukan\n1. File\n2. Terminal\nPilihan : ");
            choiceInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        String filePath = "", tempFilePath = "", fileName;
        boolean fromFile = (choiceInput == 1);
        if (fromFile) {
            filePath = System.getProperty("user.dir") + "\\test\\";
            System.out.print("Masukkan nama file txt: ");
            fileName = globalScanner.nextLine();
            tempFilePath = filePath + fileName;
            File testFile = new File(tempFilePath);

            boolean exist = testFile.exists();
            while (!exist) {
                System.out.println("File yang anda masukkan tidak ada");
                System.out.print("Masukkan nama file yang valid: ");
                fileName = globalScanner.nextLine();
                tempFilePath = filePath + fileName;
                File fileToInput = new File(tempFilePath);
                exist = fileToInput.exists();
            }
        }

        Regression.runRegression(fromFile, tempFilePath);
    }

    public static void scalingImageProcedure(Scanner globalScanner) {
        System.out.println("\n---------Memperbesar Gambar---------");
        
        String filePath = "", tempFilePath = "", fileName;
        filePath = System.getProperty("user.dir") + "\\test\\";
        System.out.println("Masukkan nama file (jpg/png/other img files) yang akan anda perbesar :");
        fileName = globalScanner.nextLine();
        tempFilePath = filePath + fileName;
        File testFile = new File(tempFilePath);

        boolean exist = testFile.exists();
        while (!exist) {
            System.out.println("File yang anda masukkan tidak ada");
            System.out.print("Masukkan nama file yang valid: ");
            fileName = globalScanner.nextLine();
            tempFilePath = filePath + fileName;
            File fileToInput = new File(tempFilePath);
            exist = fileToInput.exists();
        }

        System.out.println("\nMasukkan faktor pembesar (bilangan bulat) : ");
        int n = globalScanner.nextInt();
        globalScanner.nextLine();

        Matrix imageMatrixAlpha = ImageScaling.getImageMatrix(tempFilePath, 'a');
        Matrix imageMatrixRed = ImageScaling.getImageMatrix(tempFilePath, 'r');
        Matrix imageMatrixGreen = ImageScaling.getImageMatrix(tempFilePath, 'g');
        Matrix imageMatrixBlue = ImageScaling.getImageMatrix(tempFilePath, 'b');

        int imageType = ImageScaling.getImageType(tempFilePath);

        Matrix borderedImageMatrixAlpha = ImageScaling.getBorderedMatrix(imageMatrixAlpha);
        Matrix borderedImageMatrixRed = ImageScaling.getBorderedMatrix(imageMatrixRed);
        Matrix borderedImageMatrixGreen = ImageScaling.getBorderedMatrix(imageMatrixGreen);
        Matrix borderedImageMatrixBlue = ImageScaling.getBorderedMatrix(imageMatrixBlue);

        Matrix scaledImageMatrixAlpha = ImageScaling.getScaledMatrix(borderedImageMatrixAlpha, n);
        Matrix scaledImageMatrixRed = ImageScaling.getScaledMatrix(borderedImageMatrixRed, n);
        Matrix scaledImageMatrixGreen = ImageScaling.getScaledMatrix(borderedImageMatrixGreen, n);
        Matrix scaledImageMatrixBlue = ImageScaling.getScaledMatrix(borderedImageMatrixBlue, n);

        String outputFilePath = "", outputFileName;
        System.out.print("\nMasukkan nama file gambar yang telah diperbesar :");
        outputFileName = globalScanner.nextLine();

        filePath = System.getProperty("user.dir") + "\\test\\";
        outputFilePath = filePath + outputFileName;

        System.out.print("\nMasukkan jenis gambar (jpg/png) :");
        String tipeGambar = globalScanner.nextLine();

        boolean valid = tipeGambar.equals("png") || tipeGambar.equals("jpg");
        while (valid == false) {
            System.out.println("Jenis gambar yang anda masukkan tidak valid!");
            System.out.print("Masukkan jenis gambar yang valid (jpg/png): ");
            tipeGambar = globalScanner.nextLine();
            valid = (tipeGambar == "jpg" || tipeGambar == "png");
        }

        ImageScaling.convertMatrix(scaledImageMatrixAlpha, scaledImageMatrixRed, scaledImageMatrixGreen, scaledImageMatrixBlue, outputFilePath, imageType, tipeGambar);
        System.out.println("selesai");
    }

}
