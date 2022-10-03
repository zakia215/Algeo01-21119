import java.util.*;
public class Regression {

    public static double[] getBeta(AugmentedMatrix am, AugmentedMatrix data) {
        double[] result = new double[am.getColNum()];

        for (int i = 0; i < am.getColNum() - 1; i++) {
            result[i] = am.getSolutionElementValue(i, 0);
        }

        result[result.length - 1] = findEpsilon(result, data);

        return result;
    }

    public static double findEpsilon(double[] result, AugmentedMatrix regData) {
        double e, sum = 0, y, beta;

        for (int i = 0; i < regData.getRowNum(); i++) {
            y = regData.getElement(i, (regData.getColNum() - 1));
            for (int j = 0; j < result.length - 1; j++) {
                beta = result[j];
                if (j != 0) {
                    beta *= regData.getElement(i, (j - 1));
                }
                y -= beta;
            }
            sum += y;
        }

        e = sum / regData.getRowNum();

        return e;
    }

    public static String setRegressionEquation(double[] betas) {
        String equation = "y =", end, start, toInsert, value;
        for (int i = 0; i < betas.length; i++) {
            if (betas[i] != 0) {
                end = " X" + (i);
                value = String.valueOf(betas[i]);
                start = " + ";
                if (i == 0) {
                    start = "";
                    end = "";
                } else if (i == betas.length - 1) {
                    end = "";
                }
                if (betas[i] < 0) {
                    start = " - ";
                    value = String.valueOf((betas[i] * -1));
                }
                toInsert = start + value + end;
                equation = equation.concat(toInsert);
            }
        }
        return equation;
    }

    public static double predictRegressionValue(double[] betas, double[] xValues) {
        double prediction = 0;

        for (int i = 0; i < betas.length; i++) {
            if (i == 0 || i == betas.length - 1) {
                prediction += betas[i];
            } else {
                prediction += betas[i] * xValues[i - 1];
            }
        }

        return prediction;
    }

    public static void runRegression(boolean fromFile, String filePath, Scanner globalScanner) {
        AugmentedMatrix rm, data;
        double[] betaValues, toPredict;
        String equation;
        int n, m;
        double curSum, a, b, predictedValue;

        String outputDir = System.getProperty("user.dir") + "\\output\\", outputPath = Menu.getOutputFileLoc(globalScanner,outputDir);

        if (fromFile) {
            MatrixParser getReg = new MatrixParser(filePath, false, true);
            data = getReg.getParsedMatrix();
            rm = getReg.getRegressionMatrix();
            toPredict = getReg.getRegressionPoint();
        } else {
            System.out.print("Masukan nilai n: ");
            n = globalScanner.nextInt();
            System.out.print("Masukan nilai m: ");
            m = globalScanner.nextInt();
            data = new AugmentedMatrix(n, (m + 1));
            rm = new AugmentedMatrix((m + 1), (m + 2));
            toPredict = new double[m];

            System.out.println("Masukkan nilai x dan y dipisahkan dengan spasi");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m + 1; j++) {
                    data.setElement(i, j, globalScanner.nextDouble());
                }
            }

            System.out.println("Masukkan nilai x yang ingin diprediksi: ");
            for (int i = 0; i < m; i++) {
                toPredict[i] = globalScanner.nextDouble();
            }
            globalScanner.nextLine();

            for (int i = 0; i < m + 1; i++) {
                for (int j = 0; j < (m + 2); j++) {
                    curSum = 0;
                    for (int k = 0; k < n; k++) {
                        if (i == 0) {
                            a = 1;
                        } else {
                            a = data.getElement(k, (i - 1));
                        }
                        if (j == 0) {
                            b = 1;
                        } else {
                            b = data.getElement(k, (j - 1));
                        }
                        curSum += (a * b);
                    }
                    rm.setElement(i, j, curSum);
                }
            }
        }
        rm.setResultGauss(true);
        betaValues = getBeta(rm, data);
        equation = setRegressionEquation(betaValues);
        predictedValue = predictRegressionValue(betaValues, toPredict);
        System.out.println("Persamaan regresi:");
        System.out.println(equation);
        System.out.print("Prediksi nilai untuk ");
        String outputContent = "Persamaan regresi:\n" + equation + "\nPrediksi nilai untuk ";
        for (int i = 0; i < rm.getRowNum() - 1; i++) {
            if (i != 0) {
                System.out.print(", ");
                outputContent = outputContent.concat(", ");
            }
            System.out.print("X" + (i + 1) + " = " + toPredict[i]);
            outputContent = outputContent.concat("X" + (i + 1) + " = " + toPredict[i]);
        }
        System.out.print(" : " + predictedValue);
        outputContent = outputContent.concat(" : " + predictedValue);

        Menu.outputFile(outputContent, outputPath);
    }

}
