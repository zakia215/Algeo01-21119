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

    // convert borderedMatrix index to outputMatrix
    public static int OriginalToScaled(double delta, int n) {
        return (int)(delta*n + 1.5);
    }


    public static Matrix getScaledMatrix(Matrix borderedMatrix, int n) {
        Matrix X = Bicubic.getBicubicX();
        double[] koef = null;
        
        Matrix scaledM = new Matrix((n*(borderedMatrix.getRowNum()-4)), (n*(borderedMatrix.getColNum()-4)));
        int tempI = -3;
        int tempJ = -3;

        if (n%2 == 0) {
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
                    double value = Bicubic.predictBicubicValue(x, y, koef);
                    scaledM.setElement(i, j, value);

                    tempI = originalRowIndex_i;
                    tempJ = originalColumnIndex_j;
                }
            }
        }
        return scaledM;

    }

    /* ***  IMAGE PROCESSING *** */
    public static void convertMatrix(Matrix m, String filePath, String imageType) {
        BufferedImage image = new BufferedImage(m.getColNum(), m.getRowNum(), BufferedImage.TYPE_INT_ARGB);
        File outputFile = new File(filePath);
        try {
            for (int i = 0; i < m.getRowNum(); i++) {
                for (int j = 0; j < m.getColNum(); j++) {
                    int value = (int) m.getElement(i, j);
                    image.setRGB(i, j, value);
                }
            }
            ImageIO.write(image, imageType, outputFile);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public static Matrix getImageMatrix(String pathFile) {
        int width = 0, height = 0;
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

        for(int i = 0; i < height ; i++) {
            for(int j = 0; j < width ; j++) {
                imageMatrix.setElement(i, j, image.getRGB(j, i));
            }
        }

        return imageMatrix;
    }
}
