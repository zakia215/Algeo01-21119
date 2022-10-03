import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.io.*;

public class Menu {

    public static String getFilePath(Scanner globalScanner, String fileDir) {
        String tempFilePath, fileName;

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

    public static String getOutputFileLoc(Scanner globalScanner, String fileDir) {
        String tempFilePath, fileName;

        System.out.print("Masukkan nama file output: ");
        fileName = globalScanner.nextLine();
        tempFilePath = fileDir + fileName;
        File testFile = new File(tempFilePath);
        if (testFile.exists()) {
            boolean exist = true;
            while (exist) {
                System.out.println("File yang anda masukkan sudah ada");
                System.out.print("Masukkan nama file yang lain: ");
                fileName = globalScanner.nextLine();
                tempFilePath = fileDir + fileName;
                File output = new File(tempFilePath);
                exist = output.exists();
            }
        }

        return tempFilePath;
    }

    public static void outputFile(String content, String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName, StandardCharsets.UTF_8);
            writer.println(content);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
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
        "Pilihan: "
        );
        boolean notDone = true;
        while (notDone) {
            System.out.print(mainMenuList);
            int mainMenuChoice = choice.nextInt();
            choice.nextLine();
            switch (mainMenuChoice) {
                case 1 -> determinant(choice);
                case 2 -> inverseProcedure(choice);
                case 3 -> LinearEquation(choice);
                case 4 -> interpolationProcedure(choice);
                case 5 -> bicubicProcedure(choice);
                case 6 -> regressionProcedure(choice);
                case 7 -> {
                    System.out.println("Program selesai");
                    notDone = false;
                }
                default -> System.out.println("Masukan salah!");
            }
        }
        choice.close();
    }

    public static void determinant(Scanner globalScanner) {
        System.out.println("---------Determinan Matriks---------");
        System.out.println("Metode pencarian determinan matriks");
        System.out.println("1. Metode Reduksi Baris\n2. Metode Kofaktor");
        int methodInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (methodInput != 1 && methodInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi metode\n1. Metode Reduksi Baris\n2. Metode Kofaktor");
            methodInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        boolean reduksi = (methodInput == 1);

        System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");

        int choiceInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");
            choiceInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        boolean fromFile = (choiceInput == 1);
        String filePath, tempFilePath = "";
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir") + "\\test\\";
            tempFilePath = getFilePath(globalScanner, filePath);
        }

        String outputDir = System.getProperty("user.dir") + "\\output\\", outputPath = getOutputFileLoc(globalScanner,outputDir);

        Matrix toFind;
        double determinant;
        if (!fromFile) {
            toFind = Matrix.readMatrix(globalScanner);

        } else {
            MatrixParser willInverse = new MatrixParser(tempFilePath, false, false);
            toFind = willInverse.getParsedMatrix();
        }

        String outputFileContent = "Determinan Matriks";

        if (reduksi) {
            determinant = Matrix.getDeterminantReduction(toFind, true);
            outputFileContent = outputFileContent.concat(" dengan metode reduksi baris:\n" + determinant);
        } else {
            determinant = Matrix.getCofactorDeterminant(toFind, true);
            outputFileContent = outputFileContent.concat(" dengan metode ekspansi kofaktor:\n" + determinant);
        }

        outputFile(outputFileContent, outputPath);

    }

    public static void inverseProcedure(Scanner globalScanner) {
        System.out.println("---------Inverse Matrix---------");
        System.out.println("Metode invers matriks");
        System.out.println("1. Metode Gauss-Jordan\n2. Metode Kofaktor");
        int methodInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (methodInput != 1 && methodInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi masukan\n1. Metode Gauss-Jordan\n2. Metode Kofaktor");
            methodInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        boolean gj = (methodInput == 1);

        System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");

        int choiceInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");
            choiceInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        String filePath, tempFilePath = "", fileName;
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir") + "\\test\\";
            System.out.print("Masukkan nama file txt: ");
            fileName = globalScanner.nextLine();
            tempFilePath = filePath + fileName;
            File testFile = new File(tempFilePath);
            if (!testFile.exists()) {
                boolean exist = false;
                while (!exist) {
                    System.out.println("File yang anda masukkan tidak ada");
                    System.out.print("Masukkan nama file yang valid: ");
                    fileName = globalScanner.nextLine();
                    tempFilePath = filePath + fileName;
                    File fileToInput = new File(tempFilePath);
                    exist = fileToInput.exists();
                }
            }
        }

        String outputDir = System.getProperty("user.dir") + "\\output\\", outputPath = getOutputFileLoc(globalScanner,outputDir);

        Matrix toInverse, inverted;
        if (!fromFile) {
            toInverse = Matrix.readMatrix(globalScanner);
        } else {
            MatrixParser willInverse = new MatrixParser(tempFilePath, false, false);
            toInverse = willInverse.getParsedMatrix();
        }

        String outputFileContent = "Matriks balikan";

        if (gj) {
            outputFileContent = outputFileContent.concat(" dengan metode gauss jordan:\n");
            inverted = Matrix.inverseGaussJordan(toInverse);
        } else {
            outputFileContent = outputFileContent.concat(" dengan metode kofaktor:\n");
            inverted = Matrix.inverseAdjoin(toInverse);
        }

        outputFileContent = outputFileContent.concat(Matrix.displayMatrix(inverted, false));
        outputFile(outputFileContent, outputPath);

        System.out.println("Matriks hasil invers: ");
        Matrix.displayMatrix(inverted, true);

    }

    public static void LinearEquation(Scanner globalScanner) {
        System.out.println("---------Sistem Persamaan Linier---------");
        System.out.println("Metode penyelesaian");
        System.out.println("1. Metode Gauss\n2. Metode Gauss-Jordan\n3. Metode Matriks Balikan\n4. Kaidah Cramer");
        int methodInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (methodInput != 1 && methodInput != 2 && methodInput != 3 && methodInput != 4) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi metode\n1. Metode Gauss\n2. Metode Gauss-Jordan\n3. Metode Matriks Balikan\n4. Kaidah Cramer");
            methodInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");

        int choiceInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");
            choiceInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        String filePath, tempFilePath = "";
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir") + "\\test\\";
            tempFilePath = getFilePath(globalScanner, filePath);
        }

        String outputDir = System.getProperty("user.dir") + "\\output\\", outputPath = getOutputFileLoc(globalScanner,outputDir);

        AugmentedMatrix toSolve;
        Matrix A, B;
        String solution = "";
        if (!fromFile) {
            toSolve = Matrix.readSPL(globalScanner);
        } else {
            MatrixParser willInverse = new MatrixParser(tempFilePath, false, false);
            toSolve = willInverse.getParsedMatrix();
        }

        A = Matrix.disaugment(toSolve, true);
        B = Matrix.disaugment(toSolve, false);

        String output = "Solusi sistem persamaan linier:\n";

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
            case 3 -> solution = Matrix.displayMatrix(Matrix.setResultInvers(A, B, true), false);
            case 4 -> solution = Matrix.displayMatrix(Matrix.setResultCramer(A, B), false);
        }

        output = output.concat(solution);
        outputFile(output, outputPath);
    }

    public static void interpolationProcedure(Scanner globalScanner) {
        System.out.println("---------Interpolasi Linier---------");
        System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");

        int choiceInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");
            choiceInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        String filePath, tempFilePath = "", fileName;
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir") + "\\test\\";
            System.out.print("Masukkan nama file txt: ");
            fileName = globalScanner.nextLine();
            tempFilePath = filePath + fileName;
            File testFile = new File(tempFilePath);
            if (!testFile.exists()) {
                boolean exist = false;
                while (!exist) {
                    System.out.println("File yang anda masukkan tidak ada");
                    System.out.print("Masukkan nama file yang valid: ");
                    fileName = globalScanner.nextLine();
                    tempFilePath = filePath + fileName;
                    File fileToInput = new File(tempFilePath);
                    exist = fileToInput.exists();
                }
            }
        }

        String outputDir = System.getProperty("user.dir") + "\\output\\", outputPath = getOutputFileLoc(globalScanner,outputDir);

        String outputText = Interpolation.runInterpolation(fromFile, tempFilePath, globalScanner);

        outputFile(outputText, outputPath);
    }

    public static void bicubicProcedure(Scanner globalScanner) {
        System.out.println("---------Interpolasi Bicubic---------");
        System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");

        int choiceInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");
            choiceInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        String filePath, tempFilePath = "", fileName;
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir") + "\\test\\";
            System.out.print("Masukkan nama file txt: ");
            fileName = globalScanner.nextLine();
            tempFilePath = filePath + fileName;
            File testFile = new File(tempFilePath);
            if (!testFile.exists()) {
                boolean exist = false;
                while (!exist) {
                    System.out.println("File yang anda masukkan tidak ada");
                    System.out.print("Masukkan nama file yang valid: ");
                    fileName = globalScanner.nextLine();
                    tempFilePath = filePath + fileName;
                    File fileToInput = new File(tempFilePath);
                    exist = fileToInput.exists();
                }
            }
        }

        Bicubic.runBicubic(fromFile, tempFilePath, globalScanner);
    }

    public static void regressionProcedure(Scanner globalScanner) {
        System.out.println("---------Regresi Linier Berganda---------");
        System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");

        int choiceInput = globalScanner.nextInt();
        globalScanner.nextLine();
        while (choiceInput != 1 && choiceInput != 2) {
            System.out.println("Opsi yang anda masukkan salah.");
            System.out.println("Pilih opsi masukan\n1. File\n2. Terminal");
            choiceInput = globalScanner.nextInt();
            globalScanner.nextLine();
        }

        String filePath, tempFilePath = "", fileName;
        boolean fromFile = (choiceInput == 1);
        if (choiceInput == 1) {
            filePath = System.getProperty("user.dir") + "\\test\\";
            System.out.print("Masukkan nama file txt: ");
            fileName = globalScanner.nextLine();
            tempFilePath = filePath + fileName;
            File testFile = new File(tempFilePath);
            if (!testFile.exists()) {
                boolean exist = false;
                while (!exist) {
                    System.out.println("File yang anda masukkan tidak ada");
                    System.out.print("Masukkan nama file yang valid: ");
                    fileName = globalScanner.nextLine();
                    tempFilePath = filePath + fileName;
                    File fileToInput = new File(tempFilePath);
                    exist = fileToInput.exists();
                }
            }
        }

        Regression.runRegression(fromFile, tempFilePath, globalScanner);
    }

    public static void scalingImageProcedure(Scanner globalScanner) {
        System.out.println("\n---------Memperbesar Gambar---------");
        
        String filePath = "", tempFilePath = "", fileName;
        filePath = System.getProperty("user.dir") + "\\test\\";
        System.out.println("Masukkan nama file (jpg/png/other img files) yang akan anda perbesar :");
        fileName = globalScanner.nextLine();
        tempFilePath = filePath + fileName;
        File testFile = new File(tempFilePath);
        boolean fromFile = true;

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
        Regression.runRegression(fromFile, tempFilePath, globalScanner);
    }

}
