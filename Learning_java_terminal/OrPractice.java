import java.util.Scanner;

public class OrPractice {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        while(true) {
            try {
              System.out.println("This is grade calculator");
              System.out.print("Enter your grade: ");
              int grade = Integer.parseInt(sc.nextLine());
              if(grade > 100 || grade < 1) {
                  System.out.println("Invalid grade, check your mark sheet!.");
              }
              else if(grade >= 71) {
                  System.out.println("You got A!");
              }
              else if(grade >= 40) {
                  System.out.println("You got B!");
              }
              else if(grade >= 20) {
                  System.out.println("You got C!");
              }
              else if(grade >= 1) {
                  System.out.println("You are failed, keep studying!");
              }
            } catch(NumberFormatException e) {
                System.err.println("Please enter values only!");
            }
        }
    }
}
