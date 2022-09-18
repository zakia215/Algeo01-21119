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
     * Return a boolean that represent whether the matrix passed is an echelon, reduced echelon or neither. The function
     * will pass an argument of type Matrix and a boolean to indicate whether the desired validation is for a reduced
     * or not reduced echelon.
     */
    public static boolean isEchelon(Matrix m, boolean reduced) {
        int oneCol = 0;
        boolean hasSeenOne = false;
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
}
