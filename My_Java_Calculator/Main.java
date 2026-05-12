import java.util.Scanner;
//Written by Krishnendu Chakraborty
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Addition addi = new Addition();
        Subtraction sub = new Subtraction();
        Division div = new Division();
        Multiplication mul = new Multiplication();

        while (true) {

          try {

            System.out.println("==|Welcome to calculator|==");

            System.out.println("1, Addition,\n2, Subtraction,\n3, Division,\n4, Multiplication,\n5, Exit");
            System.out.print("Choose option: ");
            int option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 1:
                    System.out.println("---This is addition section---");

                    System.out.print("Enter first number: ");
                    double a = Double.parseDouble(sc.nextLine());

                    System.out.print("Enter second number: ");
                    double b = Double.parseDouble(sc.nextLine());

                    addi.show(a, b);
                    break;
                case 2:
                    System.out.println("---This is Subtraction section---");

                    System.out.print("Enter first number: ");
                    double subs = Double.parseDouble(sc.nextLine());

                    System.out.print("Enter second number: ");
                    Double subs1 = Double.parseDouble(sc.nextLine());

                    sub.show(subs, subs1);
                    break;
                case 3:
                    //Division.main(args);
                    System.out.println("\n---This is division section---\n");

                    System.out.print("Enter first number: ");
                    double num = Double.parseDouble(sc.nextLine());

                    System.out.print("Enter second number: ");
                    double num2 = Double.parseDouble(sc.nextLine());

                    div.show(num, num2);
                    break;
                case 4:
                    System.out.println("---This is Multiplication section---");

                    System.out.print("Enter first number: ");
                    double dou = Double.parseDouble(sc.nextLine());

                    System.out.print("Enter second number: ");
                    double dou1 = Double.parseDouble(sc.nextLine());

                    mul.show(dou, dou1);
                    break;
                case 5:
                    System.out.println("Thank you for using calculator!");
                    return;
                default:
                    System.err.println("Please choose valid option!");
                    break;
            }
          } catch(NumberFormatException e) {
                System.err.println("Please enter value only!");
            } 
        }
    }
}
