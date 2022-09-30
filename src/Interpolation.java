import java.util.*;

public class Interpolation {

    public static double[] getEquation(AugmentedMatrix am) {
        double[] result = new double[am.getRowNum()];
        for (int i = 0; i < am.getRowNum(); i++) {
            result[i] = am.getSolutionElementValue(i, 0);
        }
        return result;
    }

    public static String getEquationLine(double[] result) {
        String equation = "y =", toConcat, additional, ending;

        // Constant not included
        for (int i = result.length - 1; i > 0; i--) {
            additional = "";
            if (result[i] != 0) {
                if (result[i] < 0) {
                    ending = "^" + i;
                    additional = " -";
                    if (i == 1) {
                        ending = "";
                    }
                    toConcat = additional + " " + (result[i] * -1) + " x" + ending;
                    equation = equation.concat(toConcat);
                } else {
                    ending = "^" + i;
                    if (i == 1) {
                        ending = "";
                    }
                    if (i != result.length - 1) {
                        additional = " +";
                    }
                    toConcat = additional + " " + result[i] + " x" + ending;
                    equation = equation.concat(toConcat);
                }
            }
        }

        if (result[0] < 0) {
            toConcat = " - " + (result[0]*-1);
        } else {
            toConcat = " + " + result[0];
        }

        equation = equation.concat(toConcat);

        return equation;
    }

    public static double predictValue(double x, double[] result) {
        double y = 0;
        for (int i = result.length - 1; i > 0; i--) {
            y += result[i] * Math.pow(x, i);
        }
        y += result[0];
        return y;
    }

    public static void runInterpolation(boolean file, String filePath) {
        AugmentedMatrix im;
        double[] resultCoefficient;
        String equationLine;
        double toPredict, predictedValue;

        if (file) {
            MatrixParser parsedMatrix = new MatrixParser(filePath, false, true);
            im = parsedMatrix.getInterpolationMatrix();
            im.setResultGauss(true);
            resultCoefficient = getEquation(im);
            equationLine = getEquationLine(resultCoefficient);
            toPredict = parsedMatrix.getInterpolationPoint();

        } else {
            int n;
            double toInsert;
            Scanner imScanner = new Scanner(System.in);

            System.out.print("Masukkan n: ");
            n = imScanner.nextInt();
            while (n <= 1) {
                System.out.print("Nilai n harus lebih dari 1\nMasukkan n: ");
                n = imScanner.nextInt();
            }
            im = new AugmentedMatrix(n, (n + 1));

            int i = 0;

            System.out.println("Masukkan titik-titik: ");
            while (i < n) {
                if (imScanner.hasNextDouble()) {
                    toInsert = imScanner.nextDouble();
                    for (int j = 0; j < n; j++) {
                        im.setElement(i, j, Math.pow(toInsert, j));
                    }
                    toInsert = imScanner.nextDouble();
                    im.setElement(i, n, toInsert);
                }
                i += 1;
            }

            System.out.println("Masukkan nilai x yang ingin diprediksi nilai y-nya: ");
            toPredict = imScanner.nextDouble();
            im.setResultGauss(true);
            resultCoefficient = getEquation(im);
            equationLine = getEquationLine(resultCoefficient);
            imScanner.close();
        }

        predictedValue = predictValue(toPredict, resultCoefficient);

        System.out.println("Persamaan yang dihasilkan: ");
        System.out.println(equationLine);
        System.out.println("Nilai yang diprediksi untuk " + toPredict + " adalah: " + predictedValue);
    }

}
