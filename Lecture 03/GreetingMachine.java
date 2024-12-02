import java.util.*;

public class GreetingMachine { // Class name changed to avoid keyword conflict

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a number (1, 2, or 3): ");
        int button = sc.nextInt();

        switch (button) {
            case 1:
                System.out.println("Hello");
                break;
            case 2:
                System.out.println("Namaste");
                break;
            case 3:
                System.out.println("Bonjour");
                break;
            default:
                System.out.println("Invalid 1  input. Please enter 1, 2, or 3.");
        }
    }
}