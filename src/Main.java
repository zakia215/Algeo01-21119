public class Main {
    public static void main(String[] args) {
        String filePath = "D:\\ITB\\Semester 3\\Aljabar Liniear dan Geometri\\Algeo01-21119\\test\\input_0a.txt";
        TextParser testInput = new TextParser(filePath);
        Matrix testInverse;
        testInput.readLines();
        testInput.parseMatrix();
        testInverse = Matrix.inverseAdjoin(testInput.getParsedMatrix());
        Matrix.displayMatrix(testInverse);
    }
}
