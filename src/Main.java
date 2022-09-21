public class Main {
    public static void main(String[] args) {
        String filePath = "D:\\ITB\\Semester 3\\Aljabar Liniear dan Geometri\\Algeo01-21119\\test\\input_0b.txt";
        MatrixParser testInput = new MatrixParser(filePath);
        Matrix testInverse = testInput.getParsedMatrix();

        Matrix.toEchelon(testInverse, false);
        Matrix.displayMatrix(testInverse);
    }
}
