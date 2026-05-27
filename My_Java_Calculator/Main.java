import java.util.Scanner;
//Written by Krishnendu Chakraborty
public class Main {
    public static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        Addition addi = new Addition();
        Subtraction sub = new Subtraction();
        Division div = new Division();
        Multiplication mul = new Multiplication();
        boolean running = true;

        while (running) {

          try {

            System.out.println("==|Welcome to calculator|==");

            System.out.println("1, Addition,\n2, Subtraction,\n3, Division,\n4, Multiplication,\n5, Exit");
            System.out.print("Choose option: ");
            String option = sc.nextLine();

           int statusCode = switch (option) {
<<<<<<< HEAD
                case "1" ->  {
                    System.out.println("\n---This is addition section---\n");
=======
                case "1" -> {                  System.out.println("---This is addition section---");
>>>>>>> ca896a6 (Update, code formatting!)

                    System.out.print("Enter first number: ");
                    double a = Double.parseDouble(sc.nextLine());

                    System.out.print("Enter second number: ");
                    double b = Double.parseDouble(sc.nextLine());

                    addi.show(a, b);
                    yield 1;
                }
                case "2" -> {
                    System.out.println("\n---This is Subtraction section---\n");

                    System.out.print("Enter first number: ");
                    double subs = Double.parseDouble(sc.nextLine());

                    System.out.print("Enter second number: ");
                    Double subs1 = Double.parseDouble(sc.nextLine());

                    sub.show(subs, subs1);
                    yield 1;
            }
                case "3" -> {
                    //Division.main(args);
                    System.out.println("\n---This is division section---\n");

                    System.out.print("Enter first number: ");
                    double num = Double.parseDouble(sc.nextLine());

                    System.out.print("Enter second number: ");
                    double num2 = Double.parseDouble(sc.nextLine());

                    div.show(num, num2);
                    yield 1;
                }
                case "4" -> {
                    System.out.println("\n---This is Multiplication section---\n");

                    System.out.print("Enter first number: ");
                    double dou = Double.parseDouble(sc.nextLine());

                    System.out.print("Enter second number: ");
                    double dou1 = Double.parseDouble(sc.nextLine());

                    mul.show(dou, dou1);
                    yield 1;
                }
                case "5" -> {
                    System.out.println("Thank you for using calculator");
                    yield -1;
                }
                default -> {
                    System.out.println("Invalid choice");
                    yield 0;
                }
            };
           System.out.println(statusCode);
           if (statusCode == -1) {
               running = false;
           }
          } catch(NumberFormatException e) {
                System.err.println("Please enter value only!");
          } 
        }
    }
}
