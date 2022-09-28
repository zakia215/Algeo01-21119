import java.util.Scanner;
import java.util.regex.MatchResult;

public class Bicubic {

    /***
     * Returns X matrix with function f(x,y) = (x^i)(y^j) and x,y [-1, 0, 1, 2]
     * @return
     */
    public static Matrix getBicubicX() {
        int curX = -1, curY = -1, curRow = 0, curCol = 0;
        Matrix bm = new Matrix(16, 16);
        double a, b, toInsert;

        while (curY <= 2) {
            curX = -1;
            while (curX <= 2) {
                curCol = 0;
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 4; i++) {
                        a = Math.pow(curX, i);
                        b = Math.pow(curY, j);
                        if (a != 0 && b != 0) {
                            toInsert = a * b;
                        } else {
                            toInsert = 0;
                        }
                        bm.setElement(curRow, curCol, toInsert);
                        curCol += 1;
                    }
                }
                curX += 1;
                curRow += 1;
            }
            curY += 1;
        }

        return bm;
    }

    public static double[] getCoefficient(Matrix X, Matrix Y) {
        double[] aCoefficient = new double[16];
        Matrix aInMatrix = Matrix.setResultInvers(X, Y);

        for (int i = 0; i < 16; i++) {
            aCoefficient[i] = aInMatrix.getElement(i, 0);
        }
        
        return aCoefficient;
    }
    
    public static double predictBicubicValue(double x, double y, double[] aCoefficient) {
        double result = 0, a, b;
        int i = 0, j = 0, k = 0;

        while (j <= 3) {
            i = 0;
            while (i <= 3) {
                a = Math.pow(x, i);
                b = Math.pow(y, j);
                if (a != 0 && b != 0 && aCoefficient[k] != 0) {
                    result += aCoefficient[k] * Math.pow(x, k) * Math.pow(y, k);
                    k += 1;
                }
                i += 1;
            }
            j += 1;
        }

        return result;
    }

    public static void runBicubic(boolean fromFile, String filePath) {
        double[] coefficients;
        Matrix xm = getBicubicX(), ym;
        double predictedValue;
        int i = -1, j, curRow = 0;

        if (fromFile) {
            MatrixParser bYMatrix = new MatrixParser(filePath, true);
            double[] toPredict = bYMatrix.getPointToInterpolate();
            ym = bYMatrix.getBicubicY(true);
            coefficients = getCoefficient(xm, ym);
            predictedValue = predictBicubicValue(toPredict[0], toPredict[1], coefficients);
        } else {
            double[] toPredict = new double[2];
            Scanner bicubicValueScanner = new Scanner(System.in);
            ym = new Matrix(16, 1);
            System.out.println("Masukkan nilai sesuai koordinat yang dinyatakan: ");

            while (i <= 2) {
                j = -1;
                while (j <= 2) {
                    System.out.print("f(" + j + ", " + i + "): ");
                    ym.setElement(curRow, 0, bicubicValueScanner.nextDouble());
                    j += 1;
                    curRow += 1;
                }
                i += 1;
            }

            System.out.println("Masukkan titik yang ingin diprediksi nilainya: ");
            System.out.print("x: ");
            toPredict[0] = bicubicValueScanner.nextDouble();
            System.out.print("y: ");
            toPredict[1] = bicubicValueScanner.nextDouble();

            bicubicValueScanner.close();
            coefficients = getCoefficient(xm, ym);
            predictedValue = predictBicubicValue(toPredict[0], toPredict[1], coefficients);
        }

        System.out.println("Nilai yang diprediksi: " + predictedValue);
    }

}
