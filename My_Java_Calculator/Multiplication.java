import java.util.Scanner;

public class Multiplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
        System.out.println("====Multiplication section====");

        System.out.print("Enter first number: ");
        int num = Integer.parseInt(sc.nextLine());

        System.out.print("Enter second number: ");
        int num2 = Integer.parseInt(sc.nextLine());

        int result = num * num2;
        System.out.println("Multiplication result:" + result);
        } catch(NumberFormatException e) {
            System.err.println("Please enter value only");
        }
    }
}
