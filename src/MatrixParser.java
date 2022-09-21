import java.io.*;
import java.util.*;

public class MatrixParser {
    private String filePath;
    private String lines;
    private Matrix parsedMatrix;

    public MatrixParser(String filePath) {
        this.filePath = filePath;
        readLines();
        parseMatrix();
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }

    public void setParsedMatrix(Matrix parsedMatrix) {
        this.parsedMatrix = parsedMatrix;
    }

    public String getFilePath() { return filePath; }

    public String getLines() {
        return lines;
    }

    public Matrix getParsedMatrix() {
        return parsedMatrix;
    }

    /**
     * readLines() passes no parameter and return the number of lines that are in the txt file. Assumed the filePath 
     * field is not empty. Also sets the lines field with all the lines in the text file seperated by a new line.
     */
    public int readLines() {
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
        return i;
    }

    /**
     * Returns the number of columns that are in the matrix that is read in the txt file. Assumed that we already set
     * the line field.
     */
    public int getCol(String t) {
        int i = 0;
        Scanner doubleCounter = new Scanner(t);
        while (doubleCounter.hasNextDouble()) {
            doubleCounter.nextDouble();
            i += 1;
        }
        doubleCounter.close();
        i /= this.readLines();
        return i;
    }

    /**
     * Returns the matrix in the lines field and turns it into a matrix object.
     */
    public void parseMatrix() {
        int row = this.readLines(), col = getCol(getLines());
        Matrix m = new Matrix(row, col);
        Scanner doubleReader = new Scanner(getLines());
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (doubleReader.hasNextDouble()) {
                    m.setElement(i, j, doubleReader.nextDouble());
                }
            }
        }
        setParsedMatrix(m);
    }
}