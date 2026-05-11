import java.util.Scanner;

public class Subtraction {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        try {
        System.out.println("====This is subtraction section====");

        System.out.print("Enter first number: ");
        int num = Integer.parseInt(sc.nextLine());

        System.out.print("Enter second number: ");
        int num2 = Integer.parseInt(sc.nextLine());

        int result = num - num2;
        System.out.println("Subtraction result: " + result);
        }catch(NumberFormatException e) {
            System.err.println("Please enter value only");
        }
    }
}
