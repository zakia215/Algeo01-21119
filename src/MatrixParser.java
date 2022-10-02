import java.io.*;
import java.util.*;

public class MatrixParser {
    private String filePath;
    private String lines;
    private AugmentedMatrix parsedMatrix;

    public MatrixParser(String filePath, boolean bicubic, boolean regression) {
        this.filePath = filePath;
        readLines(bicubic, regression);
        parseMatrix(bicubic, regression);
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }

    public void setParsedMatrix(AugmentedMatrix parsedMatrix) {
        this.parsedMatrix = parsedMatrix;
    }

    public String getFilePath() { 
        return filePath; 
    }

    public String getLines() {
        return lines;
    }

    public AugmentedMatrix getParsedMatrix() {
        return parsedMatrix;
    }

    /**
     * readLines() passes no parameter and return the number of lines that are in the txt file. Assumed the filePath 
     * field is not empty. Also sets the lines field with all the lines in the text file seperated by a new line.
     */
    public int readLines(boolean bicubic, boolean regression) {
        int i = 0;
        try {
            File inputFile = new File(this.getFilePath());
            Scanner lineReader = new Scanner(inputFile);
            setLines("");
            while (lineReader.hasNextLine()) {
                setLines(getLines() + lineReader.nextLine() + "\n");
                i += 1;
            }
            lineReader.close();
        } catch (FileNotFoundException exception) {
            System.out.println("An error occurred.");
            exception.printStackTrace();
        }
        if (bicubic) {
            i -= 1;
        } else if (regression) {
            i -= ((getCol(getLines(), false, true)) - 1);
        }
        return i;
    }

    /**
     * Returns the number of columns that are in the matrix that is read in the txt file. Assumed that we already set
     * the line field.
     */
    public int getCol(String t, boolean bicubic, boolean regression ) {
        Scanner doubleCounter = new Scanner(t);
        if (regression) {
            int i = 0;
            String firstLine = doubleCounter.nextLine();
            Scanner colCounter = new Scanner(firstLine);
            while(colCounter.hasNextDouble()) {
                colCounter.nextDouble();
                i += 1;
            }
            colCounter.close();
            return i;
        }
        int i = 0, row = readLines(bicubic, false);
        while (doubleCounter.hasNextDouble()) {
            doubleCounter.nextDouble();
            i += 1;
        }
        doubleCounter.close();
        
        if (bicubic) {
            i -= 2;
        }
        i /= row;
        return i;
    }

    /**
     * Returns the matrix in the lines field and turns it into a matrix object.
     */
    public void parseMatrix(boolean bicubic, boolean regression) {
        int row = readLines(bicubic, regression), col = getCol(getLines(), bicubic, regression);
        AugmentedMatrix m = new AugmentedMatrix(row, col);
        Scanner doubleReader = new Scanner(getLines());
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (doubleReader.hasNextDouble()) {
                    m.setElement(i, j, doubleReader.nextDouble());
                }
            }
        }
        doubleReader.close();
        setParsedMatrix(m);
    }

    public AugmentedMatrix getInterpolationMatrix() {
        int row = this.readLines(false, true), col = this.readLines(false, true) + 1;
        double toInsert;
        AugmentedMatrix inter = new AugmentedMatrix(row, col);

        Scanner doubleReader = new Scanner(getLines());
        for (int i = 0; i < row; i++) {
            if (doubleReader.hasNextDouble()) {
                toInsert = doubleReader.nextDouble();
                for (int j = 0; j < col - 1; j++) {
                    inter.setElement(i, j, Math.pow(toInsert, j));
                }
                toInsert = doubleReader.nextDouble();
                inter.setElement(i, (col-1), toInsert);
            }
        }
        doubleReader.close();
        this.parsedMatrix = inter;

        return inter;
    }

    public AugmentedMatrix getRegressionMatrix() {
        int row = getCol(getLines(), false, true), col = row + 1, n = readLines(false, true);
        double curSum, a, b;
        AugmentedMatrix rm = new AugmentedMatrix(row, col);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                curSum = 0;
                for (int k = 0; k < n; k++) {
                    if (i == 0) {
                        a = 1;
                    } else {
                        a = getParsedMatrix().getElement(k, (i - 1));
                    }
                    if (j == 0) {
                        b = 1;
                    } else {
                        b = getParsedMatrix().getElement(k, (j - 1));
                    }
                    curSum += (a * b);
                }
                rm.setElement(i, j, curSum);
            }
        }

        return rm;
    }

    public Matrix getBicubicY(boolean file) {
        Matrix bm = new Matrix(16, 1);
        if (file) {
            int curIdx = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    bm.setElement(curIdx, 0, getParsedMatrix().getElement(j, i));
                    curIdx += 1;
                }
            }
        } else {
            Scanner bicubicScanner = new Scanner(System.in);

            for (int i = 0; i < 16; i++) {
                bm.setElement(i, 0, bicubicScanner.nextDouble());
            }
            bicubicScanner.close();
        }

        return bm;
    }

    public double[] getPointToInterpolate() {
        double[] result = new double[2];
        Scanner valueScanner = new Scanner(getLines());

        for (int i = 0; i < 16; i++) {
            valueScanner.nextDouble();
        }

        for (int i = 0; i < 2; i++) {
            result[i] = valueScanner.nextDouble();
        }
        valueScanner.close();
        return result;
    }

    public double[] getRegressionPoint() {
        double[] result = new double[getParsedMatrix().getColNum() - 1];
        Scanner valueScanner = new Scanner(getLines());

        for (int i = 0; i < getParsedMatrix().getRowNum(); i++) {
            valueScanner.nextLine();
        }
        for (int i = 0; i < (getParsedMatrix().getColNum() - 1); i++) {
            result[i] = valueScanner.nextDouble();
        }

        return result;
    }

    public double getInterpolationPoint() {
        double result;
        Scanner valueScanner = new Scanner(getLines());

        for (int i = 0; i < getParsedMatrix().getRowNum(); i++) {
            valueScanner.nextLine();
        }
        result = valueScanner.nextDouble();

        return result;
    }

}