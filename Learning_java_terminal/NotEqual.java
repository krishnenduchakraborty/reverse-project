import java.util.Scanner;

public class NotEqual {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        String num = "1";
        boolean authenticated = false;

      for(int n = 0; n <= 2; n++) {
        System.out.print("Enter your passcode: ");
        String num1 = sc.nextLine();

        if (num1.equals(num)) {
            System.out.println("Welcome!");
            authenticated = true;
            break;
        }
        else if (n < 2) {
            System.err.println("Wrong passcode, try again!");
        }
      }
      if (!authenticated) {
          System.err.println("System locked, Max attempts reached!");
      }
    }
}
