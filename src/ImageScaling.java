import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageScaling {
    // public static Matrix doubleMatrix(Matrix m) {
    //     Matrix scaledM = new Matrix((2*m.getRowNum()), (2*m.getColNum()));


    // }

    public static void convertMatrix(Matrix m, String filePath) {
        BufferedImage image = new BufferedImage(m.getColNum(), m.getRowNum(), BufferedImage.TYPE_INT_ARGB);
        File outputFile = new File(filePath);
        try {
            for (int i = 0; i < m.getRowNum(); i++) {
                for (int j = 0; j < m.getColNum(); j++) {
                    int value = (int) m.getElement(i, j);
                    image.setRGB(i, j, value);
                }
            }
            ImageIO.write(image, "png", outputFile);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public static Matrix getImageMatrix() {
        int width = 0, height = 0;
        BufferedImage image = null;
        try {   
            File inputFile = new File ("C:/Users/mrsya/Pictures/PngItem_644980 (1).png");
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
