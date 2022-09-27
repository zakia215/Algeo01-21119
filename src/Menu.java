import java.util.Scanner;

public class Menu {
    public static void determinant() {
        System.out.println("----- Determinan ------");
        Scanner choice = new Scanner(System.in);

        // meminta masukan 'metode masukan'
        String masukanList = String.join(
            System.getProperty("line.separator"),
        "Silakan pilih metode masukan :",
        "1. File",
        "2. Terminal",
        "\n"
        );
        System.out.println(masukanList);
        int masukanChoice = choice.nextInt();

        // meminta masukan "metode penyelesaian"
        String methodList = String.join(
            System.getProperty("line.separator"),
            "Silakan pilih metode penyelesaian :",
            "1. Ekspansi Kofaktor",
            "2. Reduksi Baris",
            "\n"
        );
        choice.close();
        System.out.println(methodList);
        int methodChoice = choice.nextInt();

    }
    
    public static void mainMenu() {
        System.out.println("++++++ MAIN MENU +++++++");
        Scanner choice = new Scanner(System.in);
        String mainMenuList = String.join(
            System.getProperty("line.separator"),
        "Silakan pilih menu :",
        "1. Determinan",
        "2. Matriks Balikan",
        "3. Sistem Persamaan Linier",
        "4. Interpolasi Linier",
        "5. Interpolasi Bicubic",
        "6. Regresi Linier Berganda",
        "7. Keluar",
        "\n"
        );
        System.out.println(mainMenuList);
        int mainMenuChoice = choice.nextInt();
        switch (mainMenuChoice) {
            case 1:
                determinant();
                break;
            case 2:
                break;
            default:
                System.out.println("Masukan salah!");
        }

        choice.close();
    }
}
