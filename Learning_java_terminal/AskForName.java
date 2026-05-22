import java.util.Scanner;

public class AskForName {
    public static void main(String args[]) {
       Scanner sc = new Scanner(System.in);

       System.out.print("My name is krishnendu!,\nWhat is your name?: ");

       String name = sc.nextLine();
       System.out.println("Welcome to java, " + name);

    }
}
