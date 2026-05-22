import java.util.Scanner;

class Home {
    public Scanner sc = new Scanner(System.in);
    int age = 24;
    String name = "Krishnendu";
    double height = 5.11D;
    float amount = 5000.20F;
    boolean isIndian = false;
    int adding = 50;
    void show() {
        while(true) {
        System.out.print("What is your, state name: ");
        String name1 = sc.nextLine().toUpperCase();

        if(name1.equalsIgnoreCase("kolkata")) {
            isIndian = true;
        }
        System.out.print("Add some amount,\n To get bonus: ");
        int amount1 = Integer.parseInt(sc.nextLine().toLowerCase());
        if(amount1==adding) {
            int data = amount1+adding + 50;
            System.out.println("You got bonus worth of 50: " + data);
        }
        else {
            System.out.println("better luck next time");
        }
        System.out.println("My age is: " + age + "\n" + "My name is: " + name + "\n" + "My height is: " + height + "\n" + "My current balance is: " + amount + "\n" + "Is indian: " + isIndian);
        }
    }
}
public class Game {   
    //public Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
      // public static Scanner sc = new Scanner(System.in);
        Home home = new Home();
        home.show();
    }
}
