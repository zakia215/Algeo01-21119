import java.util.*;
import java.lang.Math;
// import java.io.*;

public class Matrix {
    private double[][] mem;
    private int rowNum;
    private int colNum;
    private double determinant;
    private Matrix inverse;


    /* ***  CONSTRUCTOR *** */
    public Matrix(int row, int col) {
        this.colNum = col;
        this.rowNum = row;
        mem = new double[row][col];
    }

    /* ***  GETTER *** */
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

    public double getDeterminant() {
        return this.determinant;
    }

    public Matrix getInverse() {
        return this.inverse;
    }

    /* ***  SETTER *** */
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

    public void setDeterminant(double determinant) {
        this.determinant = determinant;
    }

    public void setInverse(Matrix inverse) {
        this.inverse = inverse;
    }

    /* ***  MATRIX VALIDATION *** */
    public static boolean isMatrixSizeEqual(Matrix m1, Matrix m2) {
        return m1.getRowNum() == m2.getRowNum() && m1.getColNum() == m2.getRowNum();
    }

    public static boolean isMatrixMultipliable(Matrix m1, Matrix m2) {
        return m1.getColNum() == m2.getRowNum();
    }

    public static boolean isSquare(Matrix m) {
        return m.getColNum() == m.getRowNum();
    }

    public static boolean isIdentity(Matrix m) {
        if (isSquare(m)) {
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
     * zero) */
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
     * or not reduced echelon. */
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

    

    /* ********** BASIC FUNCTION OF MATRIX ********** */
    /**
     * Return a matrix object after input is being read in the terminal. */
    public static AugmentedMatrix readMatrix(Scanner globalScanner) {
        int row, col;

        // read number of row and col
        System.out.print("\nNumber of rows: ");
        row = globalScanner.nextInt();
        System.out.print("Number of columns: ");
        col = globalScanner.nextInt();

        // matrix initialization and read element
        AugmentedMatrix res = new AugmentedMatrix(row, col);
        System.out.println("Enter each element of Matrix below :");
        System.out.println("(Note : use whitespace to separate each element in a row and enter for each row)");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                res.setElement(i, j, globalScanner.nextDouble());
            }
        }
        globalScanner.nextLine();
        return res;
    }

    public static AugmentedMatrix readSPL(Scanner globalScanner) {
        int row, col;

        // read number of row and col
        System.out.print("Masukkan m: ");
        row = globalScanner.nextInt();
        globalScanner.nextLine();
        System.out.print("Masukkan n: ");
        col = globalScanner.nextInt();

        // matrix initialization and read element
        AugmentedMatrix res;
        Matrix a = new Matrix(row, col - 1), b = new Matrix(row, 1);
        System.out.println("Masukkan Matriks A:");
        System.out.println("Gunakan whitespace untuk memisahkan setiap elemen dan gunakan enter untuk memisahkan setiap baris.");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col - 1; j++) {
                a.setElement(i, j, globalScanner.nextDouble());
            }
        }
        for (int i = 0; i < row; i++) {
            b.setElement(i, 0, globalScanner.nextDouble());
        }
        globalScanner.nextLine();
        res = augment(a, b);
        return res;
    }

    /**
     * print element of matrix which each column and row separated by whitespace */
    public static String displayMatrix(Matrix m) {
        String outputString = "";
        int lengthMax = 0;
        for (int i = 0; i < m.getRowNum(); i++) {
            for (int j = 0; j < m.getColNum(); j++) {
                if ((double) ((int) m.getElement(i, j)) == m.getElement(i, j)) {
                    int lengthElement = (((int) m.getElement(i, j))+"").length();
                    if (lengthElement > lengthMax) {
                        lengthMax = lengthElement;
                    }
                } else {
                    double value = Math.round(m.getElement(i, j) * 100000) / 100000.0 ;
                    int lengthElement = (value+"").length();
                    if (lengthElement > lengthMax) {
                        lengthMax = lengthElement;
                    }
                }
            }
        }

        for (int i = 0; i < m.getRowNum(); i++) {
            for (int j = 0; j < m.getColNum(); j++) {
                if ((double) ((int) m.getElement(i, j)) == m.getElement(i, j)) {
                    int lengthElement = (((int) m.getElement(i, j))+"").length();
                    System.out.print((" ".repeat((lengthMax-lengthElement + 1))) + (int) m.getElement(i, j));
                    outputString = outputString.concat((" ".repeat((lengthMax-lengthElement + 1))) + (int) m.getElement(i, j));
                } else {
                    double value = Math.round(m.getElement(i, j) * 100000) / 100000.0 ; 
                    int lengthElement = ((value)+"").length();
                    System.out.print((" ".repeat((lengthMax-lengthElement + 1))) + value);
                    int lengthElement = ((m.getElement(i, j))+"").length();
                    System.out.print((" ".repeat((lengthMax-lengthElement + 1))) + m.getElement(i, j));
                    outputString = outputString.concat((" ".repeat((lengthMax-lengthElement + 1))) + m.getElement(i, j));
                }
            }
            System.out.println();
            outputString = outputString.concat("\n");
        }
        return outputString;
    }

    /**
     * returns the amount of element in a matrix */
    public int countElement() {
        int count = 0;
        for (int i = 0; i < getRowNum(); i++) {
            for (int j = 0; j < getColNum(); j++) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Adding two matrices of which size is not yet confirmed,
     * will return a matrix of size m1.getRowNum() * m1.getColNum() if the size are different */
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
     * Substract two matrices of which size is not yet confirmed,
     * will return a matrix of size m1.getRowNum() * m1.getColNum() if the size are different. */
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

    /**
     * Multiply two matrices of which size is not yet confirmed,
     * will return a matrix of size m1.getRowNum() * m2.getColNum() if m1.getColNum() != m2.getRowNum(). */
    public static Matrix multiply(Matrix m1, Matrix m2) {
        Matrix res = new Matrix(m1.getRowNum(), m2.getColNum());
        if (m1.getColNum() == m2.getRowNum()){
            for (int j = 0; j < m2.getColNum(); j++) {
                for (int i = 0; i < m1.getRowNum(); i++) {
                    double temp = 0;
                    for (int k = 0; k < m1.getColNum(); k++) {
                        temp += m1.getElement(i, k) * m2.getElement(k, j);
                        res.setElement(i, j, temp);
                    }
                }
            }
        } else {
            System.out.println("Operasi perkalian tidak dapat dilakukan");
        }
        return res;
    }

    /**
     * Returns a matrix after being multiplied by a constant.   */
    public static Matrix multiplyByConstant(Matrix m, double c) {
        Matrix a = new Matrix(m.getRowNum(), m.getColNum());
        for (int i = 0; i < m.getRowNum(); i++) {
            for (int j = 0; j < m.getColNum(); j++) {
                a.setElement(i, j, (c * m.getElement(i, j)));
            }
        }
        return a;
    }

    /**
     * Returns a matrix after the matrix passed in the argument has been transposed. */
    public static Matrix transpose(Matrix m) {
        Matrix res = new Matrix(m.getRowNum(), m.getColNum());
        for (int i = 0; i < m.getRowNum(); i++) {
            for (int j = 0; j < m.getColNum(); j++) {
                res.setElement(j, i, m.getElement(i, j));
            }
        }
        return res;
    }

    /**
     * Returns a copy of passed matrix. */
    public static Matrix copyMatrix(Matrix m) {
        Matrix copy = new Matrix(m.getRowNum(), m.getColNum());
        for (int i = 0; i < m.getRowNum(); i++) {
            for (int j = 0;j < m.getColNum(); j++) {
                copy.setElement(i, j, m.getElement(i, j));
            }
        }
        return copy;
    }

    /* ********** ADVANCE FUNCTION OF MATRIX ********** */
    /**
     * return an echelon or a reduced echelon form of the passed matrix depending on the value of the reduced parameter */
    public static void toEchelon(Matrix m, boolean reduced) {
        int pivot = 0, lastRow;
        double divider, multiplier;
        double[] temp;
        boolean swapped;
        for (int i = 0; i < m.getRowNum(); i++) {
            if (pivot > m.getColNum() - 1) {
                break;
            }
            if (m.getElement(i, pivot) == 0 && i != m.getRowNum() - 1) {
                swapped = false;
                for (int k = i + 1; k < m.getRowNum(); k++) {
                    if (m.getElement(k, pivot) != 0) {
                        temp = m.getMem()[k];
                        m.setRow(k, m.getMem()[i]);
                        m.setRow(i, temp);
                        swapped = true;
                        break;
                    }
                }
                if (!swapped) {
                    pivot += 1;
                }
            }
            if (pivot >= m.getColNum() - 1) {
                break;
            }
            divider = m.getElement(i, pivot);
            if (divider != 0) {
                for (int k = 0; k < m.getColNum(); k++) {
                    if (m.getElement(i, k) != 0) {
                        m.setElement(i, k, (m.getElement(i, k) / divider));
                    }
                }
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
     * it will return that element. Otherwise, it will return the determinant using cofactor recursion. */
    public static double getCofactorDeterminant(Matrix m, boolean output) {
        int curRow, curCol, pivot;
        double d;
        Matrix c;
        if (!isSquare(m)) {
            m.setDeterminant(Double.NaN);
            if (output) {
                System.out.println(">>> Matriks yang anda masukkan bukan merupakan matriks persegi : Matriks tidak memiliki determinan !");
            }
            return Double.NaN;
        }
        if (m.countElement() == 0) {
            return 0;
        } else if (m.countElement() == 1) {
            return m.getElement(0, 0);
        } else if (m.countElement() == 4) {
            d = (m.getElement(0, 0) * m.getElement(1, 1)) - (m.getElement(0, 1) * m.getElement(1, 0));
            return d;
        } else {
            pivot = 0;
            d = 0;
            for (int j = 0; j < m.getColNum(); j++) {
                curRow = 0;
                c = new Matrix((m.getRowNum() - 1), (m.getColNum() - 1));
                for (int k = 1; k < m.getRowNum(); k++) {
                    curCol = 0;
                    for (int l = 0; l < m.getColNum(); l++) {
                        if (l != j) {
                            c.setElement(curRow, curCol, (m.getElement(k, l)));
                            curCol += 1;
                        }
                    }
                    curRow += 1;
                }
                if (Math.floorMod(j, 2) == 1) {
                    d = d - (m.getElement(pivot, j) * getCofactorDeterminant(c, false));
                } else {
                    d = d + (m.getElement(pivot, j) * getCofactorDeterminant(c, false));
                }
            }
            m.setDeterminant(d);
            if (output) {
                System.out.println("Determinan Matriks: " + d);
            }
            return d;
        }
    }

    /**
     * Turns the matrix passed into an upper triangle form. Assumed the matrix passed is not empty and the current form
     * of the matrix is not an upper triangle. Returns the amount of swaps that occur in the conversion */
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
     * of the elements in the matrix's diagonal */
    public static double getDeterminantReduction(Matrix m, boolean output) {
        int n;
        double d;
        if (!isSquare(m)) {
            m.setDeterminant(Double.NaN);
            if (output) {
                System.out.println(">>> Matriks yang anda masukkan bukan merupakan matriks persegi : Matriks tidak memiliki determinan !");
            }
            return Double.NaN;
        }
        if (m.countElement() == 0) { 
            return 0;
        } else if (m.countElement() == 1) {
            return m.getElement(0, 0);
        } else {
            Matrix y = copyMatrix(m);
            n = toUpperTriangle(y);
            d = y.getElement(0, 0);
            for (int i = 1; i < y.getRowNum(); i++) {
                if (y.getElement(i, i) != 0) {
                    d *= y.getElement(i, i);
                } else {
                    d = 0;
                }
            }
            d *= Math.pow(-1, n);
        }
        m.setDeterminant(d);
        if (output) {
            System.out.println("Determinan Matriks: " + d);
        }
        return d;
    }

    /**
     * Returns the augmented matrix of m1 and m2 with m2 on the right of m1. Assumed that both matrices has the same
     * number of rows. */
    public static AugmentedMatrix augment(Matrix m1, Matrix m2) {
        AugmentedMatrix augmented = new AugmentedMatrix(m1.getRowNum(), (m1.getColNum() + m2.getColNum()));
        for (int i = 0; i < m1.getRowNum(); i++) {
            for (int j = 0; j < m1.getColNum(); j++) {
                augmented.setElement(i, j, m1.getElement(i, j));
            }
        }
        for (int i = 0; i < m2.getRowNum(); i++) {
            for (int j = 0; j < m2.getColNum(); j++) {
                augmented.setElement(i, (j + m1.getColNum()), m2.getElement(i, j));
            }
        }
        return augmented;
    }

    /***
     * Returns the A or B matrix with B is the last column of m and A is the rest */
    public static Matrix disaugment(AugmentedMatrix m, boolean AnotB){
        Matrix A = new Matrix(m.getRowNum(), m.getColNum()-1);
        Matrix B = new Matrix(m.getRowNum(), 1);
        for (int i = 0; i < m.getRowNum(); i++){
            for (int j = 0; j < m.getColNum(); j++){
                if (j < m.getColNum()-1){
                    A.setElement(i, j, m.getElement(i, j));
                } else {
                    B.setElement(i, 0, m.getElement(i, j));
                }
            }
        }
        if (AnotB){
            return A;
        } else {
            return B;
        }
    }
    /**
     * Returns a cofactor matrix of the matrix that is passed. Assumed that matrix m is symmetric. */
    public static Matrix cofactor(Matrix m) {
        Matrix cofactor = new Matrix(m.getRowNum(), m.getColNum());
        Matrix subMatrix = new Matrix((m.getRowNum() - 1), (m.getColNum() - 1));
        int curRow, curCol;
        double subMatrixDet;
        for (int i = 0; i < m.getRowNum(); i++) {
            for (int j = 0; j < m.getColNum(); j++) {
                curRow = 0;
                for (int k = 0; k < m.getRowNum(); k++) {
                    if (k != i) {
                        curCol = 0;
                        for (int l = 0; l < m.getColNum(); l++) {
                            if (l != j) {
                                subMatrix.setElement(curRow, curCol, m.getElement(k, l));
                                curCol += 1;
                            }
                        }
                        curRow += 1;
                    }
                }
                subMatrixDet = getCofactorDeterminant(subMatrix, false);
                if (Math.floorMod((i + j), 2) == 1) {
                    subMatrixDet *= -1;
                }
                cofactor.setElement(i, j, subMatrixDet);
            }
        }
        return cofactor;
    }

    /**
     * Returns the inverse of m using adjoin cofactor method. If determinant is zero will return the passed matrix itself. */
    public static Matrix inverseAdjoin(Matrix m) {
        Matrix inverted;
        double detInverted = getDeterminantReduction(m, false);
        if (detInverted == 0 && !isSquare(m)) {
            System.out.println("Matriks tidak memiliki Invers : mengembalikan matriks kembali ");
            return m;
        } else {
            detInverted = 1 / detInverted;
            inverted = cofactor(m);
            inverted = transpose(inverted);
            inverted = multiplyByConstant(inverted, detInverted);
            return inverted;
        }
    }
    
    /***
     * Returns the inverse of m using Gauss-Jordan elimination method. If determinant is zero will return the passed matrix itself.*/
    public static Matrix inverseGaussJordan(Matrix m) {
        if (getDeterminantReduction(m, false) == 0 || !isSquare(m)){
            System.out.println("Matriks tidak memiliki Invers : mengembalikan matriks kembali !");
            return m;
        } else {
            // make Identity matrix
            Matrix Identity = new Matrix(m.getRowNum(), m.getColNum());
            for (int i = 0; i < Identity.getRowNum(); i++) {
                for (int j = 0; j < Identity.getColNum(); j++) {
                    if (i == j){
                        Identity.setElement(i, j, 1);
                    } else {
                        Identity.setElement(i, j, 0);
                    }
                }
            }

            // merge m and identity with identity on the right side and turn the matrix to reduced echelon
            AugmentedMatrix temp = augment(m, Identity);
            toEchelon(temp, true);

            // copy the left side to the inverted matrix
            Matrix Inversed = new Matrix(m.getRowNum(), m.getColNum());
            for (int i = 0; i < m.getRowNum(); i++) {
                for (int j = 0; j < m.getColNum(); j++) {
                    Inversed.setElement(i, j, temp.getElement(i, j+m.getColNum()));
                }
            }
            return Inversed;
        }
    }

    /***
     * Return matrix of solution Ax = B using Inverse method. Assume number of column A = number of row B*/
    public static Matrix setResultInvers(Matrix A, Matrix B, boolean displayOutput) {
        Matrix res = new Matrix(A.getRowNum(), 1);
        
        if (B.getColNum() > 1) {
            if (displayOutput) {
                System.out.println("Matrix B tidak memenuhi (Ax = B) !");
            }
        } else {
            // check is matrix singular ?
            if (getDeterminantReduction(A, false) == 0 || !isSquare(A)){
                if (displayOutput) {
                    System.out.println("Matriks A adalah matriks singular: tidak dapat menggunakan metode balikan!");
                }
            } else {
                res = multiply(inverseGaussJordan(A), B);
                if (displayOutput) {
                    for (int i = 0; i < A.getRowNum(); i++) {
                        System.out.println("X" + (i + 1) + " = " + res.getElement(i, 0));
                    }
                }
            }
        }
        return res;
    }

    /***
     * Return matrix of solution Ax = B using cramer method */
    public static Matrix setResultCramer(Matrix A, Matrix B) {
        Matrix res = new Matrix(A.getColNum(), 1);
        double detA = getDeterminantReduction(A, false);

        if (B.getColNum() > 1) {
            System.out.println("Matrix B tidak memenuhi (Ax = B) !");
        } else {
            if (detA == 0 || A.getRowNum() != B.getRowNum()) {
                System.out.println("Persamaan tidak dapat diselesaikan dengan metode cramer!");
            } else {
                for (int j = 0; j < A.getColNum(); j++) {
                    Matrix temp = copyMatrix(A);
                    for (int i = 0; i < A.getRowNum(); i++) {
                        temp.setElement(i, j, B.getElement(i, 0));
                    }
                    res.setElement(j, 0, getDeterminantReduction(temp, false)/detA);
                }
                for (int i = 0; i < A.getColNum(); i++) {
                    System.out.println("X" + (i + 1) + " = " + res.getElement(i, 0));
                }
            }
        }
        return res;
    }
}
