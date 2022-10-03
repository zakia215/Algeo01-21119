import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.lang.Math;

public class ImageScaling {

    /* ***  BORDERED MATRIX CONSTRUCTOR *** */
    public static double getNewElement(Matrix m, int i, int j) {
        return m.getElement(i + 2, j + 2);
    }

    public static void setNewElement(Matrix m, int i, int j, double value) {
        m.setElement(i + 2, j + 2, value);
    }

    public static Matrix getBorderedMatrix(Matrix m) {
        Matrix borderM = new Matrix(m.getRowNum() + 4, m.getColNum()+4);
        for (int i = -2; i < borderM.getRowNum()-2; i++) {
            for (int j = -2; j < borderM.getColNum()-2; j++) {
                if (i < 0 ) {
                    if (j < 0) {
                        setNewElement(borderM, i, j, m.getElement(0, 0));
                    } else if (j < m.getColNum()) {
                        setNewElement(borderM, i, j, m.getElement(0, j));
                    } else {
                        setNewElement(borderM, i, j, m.getElement(0, m.getColNum()-1));
                    }
                } else if (i < m.getRowNum()) {
                    if (j < 0) {
                        setNewElement(borderM, i, j, m.getElement(i, 0));
                    } else if (j < m.getColNum()) {
                        setNewElement(borderM, i, j, m.getElement(i, j));
                    } else {
                        setNewElement(borderM, i, j, m.getElement(i, m.getColNum()-1));
                    }
                } else {
                    if (j < 0) {
                        setNewElement(borderM, i, j, m.getElement(m.getRowNum()-1, 0));
                    } else if (j < m.getColNum()) {
                        setNewElement(borderM, i, j, m.getElement(m.getRowNum()-1, j));
                    } else {
                        setNewElement(borderM, i, j, m.getElement(m.getRowNum()-1, m.getColNum()-1));
                    }
                }
            }
        }
        return borderM;
    }

    /*** 
     * Returns ready-to-interpolate matrix with (x,y) is coordinate of borderedMatrix and refer to (0,0) in bicubic interpolating*/
    public static Matrix getInterpolaterMatrix(Matrix borderedMatrix,int x, int y) {
        Matrix interpolater = new Matrix(16, 1);
        int row = 0;
        for (int i = 0 ; i < 4;  i++) {
            for (int j = 0; j < 4; j++) {
                interpolater.setElement(row, 0, getNewElement(borderedMatrix, (x + i - 1), (y + j -1)));
                row += 1;
            }
        }
        return interpolater;
    }

    public static Matrix getScaledMatrix(Matrix borderedMatrix, int n) {
        Matrix X = Bicubic.getBicubicX();
        double[] koef = null;
        
        Matrix scaledM = new Matrix((n*(borderedMatrix.getRowNum()-4)), (n*(borderedMatrix.getColNum()-4)));
        int tempI = -3;
        int tempJ = -3;

        for (int i = 0; i < scaledM.getRowNum(); i++) {
            for (int j = 0; j < scaledM.getColNum(); j++) {
                int originalRowIndex_i = (int) Math.floor((double)(i-2) / n);
                int originalColumnIndex_j = (int) Math.floor((double)(j-2) / n);
                int rowDiff = (i-2) % n;
                int colDiff = (j-2) % n;
                if (rowDiff < 0) {
                    rowDiff += n;
                }
                if (colDiff < 0) {
                    colDiff += n;
                }
                double x = rowDiff * (1.0/(double)n) + 1.0/((double)n * 2);
                double y = colDiff * (1.0/(double)n) + 1.0/((double)n * 2);
                if (originalRowIndex_i != tempI || originalColumnIndex_j != tempJ) {
                    koef = Bicubic.getCoefficient(X, getInterpolaterMatrix(borderedMatrix, originalRowIndex_i, originalColumnIndex_j));
                }
                double value = Bicubic.predictBicubicValue(y, x, koef);
                scaledM.setElement(i, j, value);

                tempI = originalRowIndex_i;
                tempJ = originalColumnIndex_j;
            }
        }
        return scaledM;

    }

    /* ***  IMAGE PROCESSING *** */
    public static void convertMatrix(Matrix A, Matrix R, Matrix G, Matrix B, String filePath, int imageType, String tipeGambar) {
        BufferedImage image = new BufferedImage(A.getColNum(), A.getRowNum(), imageType);
        File outputFile = new File(filePath);
        try {
            for (int i = 0; i < A.getRowNum(); i++) {
                for (int j = 0; j < A.getColNum(); j++) {
                    int alpha = ((int) A.getElement(i, j)) << 24; // 255 = 000000011111111 - > 111111100000000
                    int red = ((int) R.getElement(i, j)) << 16; // 255                         000000011111111
                    int green = ((int) G.getElement(i, j)) << 8; // 255 =                      00000000000000011111111
                    int blue = ((int) B.getElement(i, j));
                    image.setRGB(i, j, (alpha | red | green | blue ));
                }
            }
            ImageIO.write(image, tipeGambar, outputFile);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public static Matrix getImageMatrix(String pathFile, char color) {
        int width = 0, height = 0, intColor;
        BufferedImage image = null;
        File inputFile = null;
        try {   
            inputFile = new File (pathFile);
            image = ImageIO.read(inputFile);
            width = image.getWidth();
            height = image.getHeight();

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

        Matrix imageMatrix = new Matrix(height, width); 

        if (color == 'a') {
            intColor = 24;
        } else if (color == 'r') {
            intColor = 16;
        } else if (color == 'g') {
            intColor = 8;
        } else if (color == 'b') {
            intColor = 0;
        } else {
            intColor = 24;
        }

        for(int i = 0; i < height ; i++) {
            for(int j = 0; j < width ; j++) {
                int value = (image.getRGB (i, j) >> intColor) & 0xff;
                imageMatrix.setElement(i, j, (double) value);
            }
        }

        return imageMatrix;
    }

    public static int getImageType(String pathFile) {
        BufferedImage image = null;
        File inputFile = null;
        try {   
            inputFile = new File (pathFile);
            image = ImageIO.read(inputFile);

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return image.getType();
    }
}
