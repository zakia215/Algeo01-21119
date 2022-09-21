public class AugmentedMatrix extends Matrix {

    private String[] solution;
    private double[] result;

    public AugmentedMatrix(int row, int col) {
        super(row, col);
        this.result = new double[col - 1];
    }

    public void setResultGauss(boolean jordan) {
        double[] res = new double[getColNum() - 1];
        double b;
        String lineRes = "";
        char curVar = 'u';

        toEchelon(this, jordan);
        if (hasUniqueSolution(this)) {
            for (int i = getRowNum() - 1; i >= 0; i--) {
                b = getElement(i, (getColNum() - 1));
                for (int j = getColNum() - 2; j > i; j--) {
                    b -= (getElement(i, j) * res[j]);
                }
                res[i] = b;
            }
        } else if (hasManySolution(this)) {
            for (int k = 0; k < getColNum() - 2; k++) {
                res[k] = Double.NaN;
            }
            for (int i = getRowNum() - 1; i >= 0 ; i--) {
                if (allRowZero(this, i)) {
                    curVar -= 1;
                    this.solution[i] = "\n" + curVar;
                } else {

                }
            }
        }
        this.result = res;
    }

    public double[] getResult() {
        return this.result;
    }

    public static boolean allRowZero(AugmentedMatrix m, int row) {
        for (int i = 0; i < m.getColNum(); i++) {
            if (m.getElement(row, i) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean allZeroButOne(AugmentedMatrix m, int row) {
        for (int i = 0; i < m.getColNum() - 1; i++) {
            if (m.getElement(row, i) != 0) {
                return false;
            }
        }
        return (m.getElement(row, (m.getColNum() - 1)) != 0);
    }

    public static boolean hasUniqueSolution(AugmentedMatrix m) {
        return (!allRowZero(m, (m.getRowNum() - 1)) && !allZeroButOne(m, (m.getRowNum() - 1)));
    }

    public static boolean hasNoSolution(AugmentedMatrix m) {
        return (allZeroButOne(m, (m.getRowNum() - 1)));
    }

    public static boolean hasManySolution(AugmentedMatrix m) {
        return allRowZero(m, (m.getRowNum() - 1));
    }
}
