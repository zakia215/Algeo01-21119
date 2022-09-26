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

    public static String getRegressionEquation(double[] betas) {
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

}
