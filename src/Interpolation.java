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
                    ending = "^" + String.valueOf(i);
                    additional = " -";
                    if (i == 1) {
                        ending = "";
                    }
                    toConcat = additional + " " + (result[i] * -1) + " x" + ending;
                    equation = equation.concat(toConcat);
                } else {
                    ending = "^" + String.valueOf(i);
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

}
