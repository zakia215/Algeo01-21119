import java.util.*;

public class Matrix {
    private double[][] mem;
    private int rowNum;
    private int colNum;


    // Constructor
    public Matrix(int row, int col) {
        this.colNum = col;
        this.rowNum = row;
        mem = new double[row][col];
    }

    // Getter
    public double[][] getMem() {
        return mem;
    }
    public int getRowNum() {
        return rowNum;
    }
    public int getColNum() {
        return colNum;
    }
    public double getElement(int i, int j) {
        return this.mem[i][j];
    }

    // Setter
    public void setMem(double[][] mem) {
        this.mem = mem;
    }
    public void setRow(int i, double[] row) {
        this.mem[i] = row;
    }
    public void setElement(int i, int j, double value) {
        this.mem[i][j] = value;
    }
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }
    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    // Matrix Validation
    public static boolean isMatrixSizeEqual(Matrix m1, Matrix m2) {
        return m1.getRowNum() == m2.getRowNum() && m1.getColNum() == m2.getRowNum();
    }
    public static boolean isMatrixMultipliable(Matrix m1, Matrix m2) {
        return m1.getColNum() == m2.getRowNum();
    }
    public static boolean isSymmetric(Matrix m) {
        return m.getColNum() == m.getRowNum();
    }
    public static boolean isIdentity(Matrix m) {
        if (isSymmetric(m)) {
            for (int i = 0; i < m.getRowNum(); i++) {
                for (int j = 0; j < m.getColNum(); j++) {
                    if (i == j && m.getElement(i, j) != 1) {
                        return false;
                    } else if (m.getElement(i, j) != 0) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * returns true if the matrix m is an upper triangle matrix (the matrix of which elements below the diogonal are
     * zero).*/
    public static boolean isUpperTriangle(Matrix m) {
        for (int i = 1; i < m.getRowNum(); i++) {
            for (int j = 0; j < i; j++) {
                if (m.getElement(i, j) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Return a boolean that represent whether the matrix passed is an echelon, reduced echelon or neither. The function
     * will pass an argument of type Matrix and a boolean to indicate whether the desired validation is for a reduced
     * or not reduced echelon.
     */
    public static boolean isEchelon(Matrix m, boolean reduced) {
        int oneCol = 0;
        boolean hasSeenOne;
        for (int i = 0; i < m.getRowNum(); i++) {
            hasSeenOne = false;
            for (int j = 0; j < m.getColNum(); j++) {
                if (!hasSeenOne && m.getElement(i, j) != 0 && m.getElement(i, j) != 1) {
                    return false;
                } else if (m.getElement(i, j) == 1) {
                    hasSeenOne = true;
                    if (i != 0 && j <= oneCol) {
                        return false;
                    }
                    if (reduced) {
                        for (int k = 0; k < m.getRowNum(); k++) {
                            if (k != i && m.getElement(k, j) != 0) {
                                return false;
                            }
                        }
                    }
                    oneCol = j;
                } else if (hasSeenOne){
                    break;
                }
            }
        }
        return true;
    }

    // Terminal Access
    public static Matrix readMatrix() {
        int row, col;
        Scanner matrixInput = new Scanner(System.in);
        System.out.print("Number of rows: ");
        row = matrixInput.nextInt();
        System.out.print("Number of columns: ");
        col = matrixInput.nextInt();
        Matrix res = new Matrix(row, col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print("[" + i + "]" + "[" + j + "]: ");
                res.setElement(i, j, matrixInput.nextDouble());
            }
        }
        matrixInput.close();
        return res;
    }
    public static void displayMatrix(Matrix m) {
        for (int i = 0; i < m.getRowNum(); i++) {
            for (int j = 0; j < m.getColNum(); j++) {
                System.out.print(m.getElement(i, j) + " ");
            }
            System.out.println("");
        }
    }

    /**
     * returns the amount of element in a matrix
     */
    public static int countElement(Matrix m) {
        int count = 0;
        for (int i = 0; i < m.getRowNum(); i++) {
            for (int j = 0; j < m.getColNum(); j++) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Adding two matrices of which size is not yet confirmed,
     * will return a matrix of size m1.getRowNum() * m1.getColNum() if the size are different
     */
    public static Matrix add(Matrix m1, Matrix m2) {
        Matrix res = new Matrix(m1.getRowNum(), m1.getColNum());
        if (isMatrixSizeEqual(m1, m2)) {
            for (int i = 0; i < m1.getRowNum(); i++) {
                for (int j = 0; j < m1.getColNum(); j++) {
                    res.setElement(i, j, (m1.getMem()[i][j] + m2.getMem()[i][j]));
                }
            }
        }
        return res;
    }

    /**
     * Adding two matrices of which size is not yet confirmed,
     * will return a matrix of size m1.getRowNum() * m1.getColNum() if the size are different.
     */
    public static Matrix subtract(Matrix m1, Matrix m2) {
        Matrix res = new Matrix(m1.getRowNum(), m1.getColNum());
        if (isMatrixSizeEqual(m1, m2)) {
            for (int i = 0; i < m1.getRowNum(); i++) {
                for (int j = 0; j < m1.getColNum(); j++) {
                    res.setElement(i, j, (m1.getMem()[i][j] - m2.getMem()[i][j]));
                }
            }
        }
        return res;
    }

    public Matrix multiply(Matrix m1, Matrix m2) {
        double temp;
        Matrix res = new Matrix(m1.getRowNum(), m2.getColNum());
        for (int i = 0; i < m1.getRowNum(); i++) {
            for (int j = 0; j < m2.getColNum(); j++) {
                temp = 0;
                for (int k = 0; k < m1.getColNum(); k++) {
                    temp += m1.getElement(i, k) * m2.getElement(k, i);
                }
                res.setElement(i, j, temp);
            }
        }
        return res;
    }
    public Matrix transpose(Matrix m) {
        Matrix res = new Matrix(m.getRowNum(), m.getColNum());
        for (int i = 0; i < m.getRowNum(); i++) {
            for (int j = 0; j < getColNum(); j++) {
                res.setElement(j, i, m.getElement(i, j));
            }
        }
        return res;
    }

    /**
     * return an echelon or a reduced echelon form of the passed matrix depending on the value of the reduced parameter
     * */
    public static void toEchelon(Matrix m, boolean reduced) {
        int pivot = 0, lastRow;
        double divider, multiplier;
        double[] temp;
        for (int i = 0; i < m.getRowNum(); i++) {
            if (m.getElement(i, pivot) == 0) {
                for (int k = i + 1; k < m.getRowNum(); k++) {
                    if (m.getElement(k, pivot) != 0) {
                        temp = m.getMem()[k];
                        m.setRow(k, m.getMem()[i]);
                        m.setRow(i, temp);
                        break;
                    }
                }
            }
            divider = m.getElement(i, pivot);
            for (int k = 0; k < m.getColNum(); k++) {
                m.setElement(i, k, (m.getElement(i, k) / divider));
            }
            if (reduced) {
                lastRow = 0;
            } else {
                lastRow = i + 1;
            }
            for (int k = lastRow; k < m.getRowNum(); k++) {
                multiplier = m.getElement(k, pivot);
                if (k != i) {
                    for (int l = 0; l < m.getColNum(); l++) {
                        m.setElement(k, l, (m.getElement(k, l) - multiplier * m.getElement(i, l)));
                    }
                }
            }
            pivot += 1;
        }
    }

    /**
     * Returns a determinant of a matrix m, if the amount of element is zero it will return 0, if the element is 1 then
     * it will return that element. Otherwise, it will return the determinant using cofactor recursion.
     */
    public static double getCofactorDeterminant(Matrix m) {
        int curRow, curCol, pivot;
        double d;
        Matrix c;
        if (countElement(m) == 0) {
            return 0;
        } else if (countElement(m) == 1) {
            return m.getElement(0, 0);
        } else if (countElement(m) == 4) {
            d = m.getElement(0, 0) * m.getElement(1, 1) - m.getElement(0, 1) * m.getElement(1, 0);
            return d;
        } else {
            pivot = 0;
            d = 0;
            for (int j = 0; j < m.getColNum(); j++) {
                curRow = 0;
                c = new Matrix((m.getRowNum() - 1), (m.getColNum() - 1));
                for (int k = 0; k < m.getRowNum(); k++) {
                    curCol = 0;
                    for (int l = 0; l < m.getColNum(); l++) {
                        if (l != j && k != pivot) {
                            c.setElement(curRow, curCol, (m.getElement(k, l)));
                            curCol++;
                        }
                    }
                    curRow++;
                }
                if (Math.floorDiv(j, 2) == 1) {
                    d = d - (m.getElement(pivot, j) * getCofactorDeterminant(c));
                } else {
                    d = d + (m.getElement(pivot, j) * getCofactorDeterminant(c));
                }
            }
            return d;
        }
    }

    /**
     * Turns the matrix passed into an upper triangle form. Assumed the matrix passed is not empty and the current form
     * of the matrix is not an upper triangle. Returns the amount of swaps that occur in the conversion
     */
    public static int toUpperTriangle(Matrix m) {
        int pivot = 0, swaps = 0;
        double multiplier;
        double[] temp;

        for (int i = 0; i < m.getRowNum(); i++) {
            if (m.getElement(i, pivot) == 0) {
                for (int j = i + 1; j < m.getRowNum(); j++) {
                    if (m.getElement(j, pivot) != 0) {
                        temp = m.getMem()[j];
                        m.setRow(j, m.getMem()[i]);
                        m.setRow(i, temp);
                        swaps += 1;
                        break;
                    }
                }
            }
            for (int j = i + 1; j < m.getRowNum(); j++) {
                multiplier = m.getElement(j, pivot) / m.getElement(i, pivot);
                for (int k = 0; k < m.getColNum(); k++) {
                    m.setElement(j, k, m.getElement(j, k) - multiplier * m.getElement(i, k));
                }
            }
            pivot += 1;
        }
        return swaps;
    }

    /**
     * Returns the determinant of a matrix using the reduction algorithm. Returns 0 if the element is zero. If there is
     * only one element in the matrix it will return that only element. Otherwise, it will return the sum multiplication
     * of the elements in the matrix's diagonal
     */
    public static double getDeterminantReduction(Matrix m) {
        int n;
        double d;
        if (countElement(m) == 0) {
            return 0;
        } else if (countElement(m) == 1) {
            return m.getElement(0, 0);
        } else {
            n = toUpperTriangle(m);
            d = m.getElement(0, 0);
            for (int i = 1; i < m.getRowNum(); i++) {
                d *= m.getElement(i, i);
            }
            d *= Math.pow(-1, n);
        }
        return d;
    }
}
