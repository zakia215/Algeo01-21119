import java.io.*;
import java.util.*;

public class TextParser {
    private String filePath;
    private String lines;
    private Matrix parsedMatrix;

    public TextParser(String filePath) { this.filePath = filePath; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

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

    public int countElement(String t) {
        int i = 0;
        Scanner doubleCounter = new Scanner(t);
        while (doubleCounter.hasNextDouble()) {
            doubleCounter.nextDouble();
            i += 1;
        }
        doubleCounter.close();
        return i;
    }

    public void parseMatrix() {
        int row = this.readLines(), col = countElement(getLines()) / row;
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