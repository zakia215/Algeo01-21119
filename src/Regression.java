
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
                end = " X" + String.valueOf((i));
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

    public static void runRegression(boolean fromFile, String filePath) {
        AugmentedMatrix rm, data;
        double[] betaValues;
        String equation;
        int n, m;
        double curSum, a, b;

        if (fromFile) {
            MatrixParser getReg = new MatrixParser(filePath, false);
            data = getReg.getParsedMatrix();
            rm = getReg.getRegressionMatrix();
        } else {
            Scanner regScanner = new Scanner(System.in);
            System.out.print("Masukan nilai n: ");
            n = regScanner.nextInt();
            System.out.print("Masukan nilai m: ");
            m = regScanner.nextInt();
            data = new AugmentedMatrix(n, (m + 1));
            rm = new AugmentedMatrix(m, (m + 1));

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < 2; j++) {
                    data.setElement(i, j, regScanner.nextDouble());
                }
            }

            regScanner.close();

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < (m + 1); j++) {
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
        betaValues = getBeta(rm, data);
        equation = setRegressionEquation(betaValues);
        System.out.println(equation);
    }

}
