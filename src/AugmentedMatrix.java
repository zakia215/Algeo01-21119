public class AugmentedMatrix extends Matrix {

    private String[][] solution;
    private String displayableSolution;

    public AugmentedMatrix(int row, int col) {
        super(row, col);
    }

    public static int leadingOneRow(AugmentedMatrix m, int colNum) {
        int idx = -1;
        outerLoop:
        for (int i = 0; i < m.getRowNum(); i++) {
            if (m.getElement(i, colNum) == 1) {
                for (int j = colNum - 1; j >= 0 ; j--) {
                    if (m.getElement(i, j) != 0) {
                        continue outerLoop;
                    }
                }
                idx = i;
            }
        }
        return idx;
    }

    public static String[] addParametric(String[] e1, String[] e2, int params) {
        String[] result;
        result = e1.clone();
        result[0] = String.valueOf((Double.parseDouble(e1[0]) + Double.parseDouble(e2[0])));
        for (int i = 1; i < 1+(2*params); i+=2) {
            result[i] = String.valueOf((Double.parseDouble(e1[i]) + Double.parseDouble(e2[i])));
        }
        return result;
    }

    public static String[] constParametric(String[] e, int params, double c) {
        String[] result;
        result = e.clone();
        result[0] = String.valueOf((Double.parseDouble(e[0]) * c));
        for (int i = 1; i < 1+(2*params); i+=2) {
            result[i] = String.valueOf((c * Double.parseDouble(e[i])));
        }
        return result;
    }

    public static int paramNum(int i, int[] arrIdx, int len) {
        int idx = -1;
        for (int j = 0; j < len; j++) {
            if (i == arrIdx[j]) {
                idx = j;
            }
        }
        return idx;
    }

    public void setResultGauss(boolean jordan) {
        String[][] lines;
        char curVar;
        int[] varOnly = new int[getColNum() - 1];
        int count = 0, par;

        toEchelon(this, jordan);
        if (!hasNoSolution(this)) {
            for (int i = 0; i < getColNum() - 1; i++) {
                if (leadingOneRow(this, i) == -1) {
                    varOnly[count] = i;
                    count++;
                }
            }
            lines = new String[getColNum()][1 + 2 * count];
            for (int i = 0; i < getColNum(); i++) {
                for (int j = 0; j < 2 * count + 1; j++) {
                    lines[i][j] = "0";
                }
            }
            for (int i = 0; i < getColNum(); i++) {
                curVar = 't';
                for (int j = count - 1; j >= 0; j--) {
                    lines[i][2*(j+1)] = String.valueOf(curVar);
                    curVar -= 1;
                }
            }
            for (int i = getColNum() - 2; i >= 0; i--) {
                par = paramNum(i, varOnly, count);
                if (par == -1) {
                    lines[getColNum() - 1][0] = String.valueOf(getElement(leadingOneRow(this, i), getColNum() - 1));
                    for (int j = 0; j < 1 + 2 * count; j++) {
                        lines[i][j] = lines[getColNum() - 1][j];
                    }
                    for (int j = i + 1; j < getColNum() - 1; j++) {
                        lines[i] = addParametric(lines[i], constParametric(lines[j], count, -(getElement(leadingOneRow(this, i), j))), count);
                    }
                } else {
                    lines[i][par*2+1] = "1";
                }
            }
            this.solution = lines;
            getSolutionString(this, count);
        } else {
            this.solution = new String[1][1];
            this.solution[0][0] = "Tidak ada solusi";
            getSolutionString(this, -1);
        }
    }

    public String getDisplayableSolution() {
        return displayableSolution;
    }

    public double getSolutionElementValue(int i, int j) {
        return Double.parseDouble(this.solution[i][j]);
    }

    public static boolean allZeroButOne(AugmentedMatrix m, int row) {
        for (int i = 0; i < m.getColNum() - 1; i++) {
            if (m.getElement(row, i) != 0) {
                return false;
            }
        }
        return (m.getElement(row, (m.getColNum() - 1)) != 0);
    }

    public static void getSolutionString(AugmentedMatrix m, int params) {
        int j;
        boolean allZero;

        if (hasNoSolution(m)) {
            m.displayableSolution = "Tidak ada solusi";
        } else {
            m.displayableSolution = "";
            if (params != 0) {
                for (int i = 0; i < m.getColNum() - 1; i++) {
                    if (i != 0) {
                        m.displayableSolution = m.displayableSolution.concat(("\n"));
                    }
                    m.displayableSolution = m.displayableSolution.concat(("X" + (i + 1) + " = "));
                    j = 0;
                    while (j < 2 * params + 1) {
                        if (j == 0 && Double.parseDouble(m.solution[i][j]) != 0) {
                            m.displayableSolution = m.displayableSolution.concat(m.solution[i][j]);
                            allZero = true;
                            for (int k = 1; k < 2 * params + 1; k+=2) {
                                if (Double.parseDouble(m.solution[i][k]) != 0) {
                                    allZero = false;
                                }
                            }
                            if (!allZero) {
                                m.displayableSolution = m.displayableSolution.concat((" + "));
                            }
                        } else if (Math.floorMod(j, 2) == 1) {
                            if (Double.parseDouble(m.solution[i][j]) == 0) {
                                j += 2;
                                continue;
                            } else if (Double.parseDouble(m.solution[i][j]) == 1) {
                                j += 1;
                                continue;
                            } else if (Double.parseDouble(m.solution[i][j]) == -1) {
                                m.displayableSolution = m.displayableSolution.substring(0, (m.displayableSolution.length() - 2));
                                m.displayableSolution = m.displayableSolution.concat("- ");
                                j += 1;
                                continue;
                            }
                            m.displayableSolution = m.displayableSolution.concat("(" + m.solution[i][j] + ")");
                        } else if (j > 0){
                            m.displayableSolution = m.displayableSolution.concat(m.solution[i][j]);
                            if (j != 2 * params) {
                                allZero = true;
                                for (int k = j + 1; k < 2 * params + 1; k+=2) {
                                    if (Double.parseDouble(m.solution[i][k]) != 0) {
                                        allZero = false;
                                    }
                                }
                                if (!allZero) {
                                    m.displayableSolution = m.displayableSolution.concat(" +");
                                }
                            }
                        }
                        j += 1;
                    }
                }
            } else {
                for (int i = 0; i < m.getColNum() - 1; i++) {
                    if (i != 0) {
                        m.displayableSolution = m.displayableSolution.concat("\n");
                    }
                    m.displayableSolution = m.displayableSolution.concat("X" + (i + 1) + " = " + m.solution[i][0]);
                }
            }
        }
    }

    public static boolean hasNoSolution(AugmentedMatrix m) {
        return (allZeroButOne(m, (m.getRowNum() - 1)));
    }
}
