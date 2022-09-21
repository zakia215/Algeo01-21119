public class Main {
    public static void main(String[] args) {
        String filePath = "D:\\ITB\\Semester 3\\Aljabar Liniear dan Geometri\\Algeo01-21119\\test\\input_1a.txt";
        MatrixParser testInput = new MatrixParser(filePath);
        AugmentedMatrix testResult = testInput.getParsedMatrix();
        testResult.setResultGauss(false);
        for (int i = 0; i < testResult.getColNum() - 1; i++) {
            System.out.println(testResult.getResult()[i]);
        }
    }
}
