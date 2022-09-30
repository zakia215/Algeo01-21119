import java.util.*;

public class Main {

    public static void main(String[] args) {
        // System.out.println("======== SELAMAT DATANG DI MATRIX.IO ========");
        // Menu.mainMenu();
        // Matrix A = Matrix.readMatrix(true);
        // Matrix.toEchelon(A, true);
        // Matrix A = ImageScaling.getImageMatrix();
        // Matrix.displayMatrix(A);
        // ImageScaling.convertMatrix(A);
        // System.out.println(A.getElement(0, 0));
        Matrix X = Bicubic.getBicubicX();
        Matrix Y = Matrix.readMatrix(true);
        double[] koef = Bicubic.getCoefficient(X, Y);
        double a = Bicubic.predictBicubicValue(0.25, 0.75, koef);
        double b = Bicubic.predictBicubicValue(0.75, 0.75, koef);
        System.out.println(a + " " + b);
    }
   
}
