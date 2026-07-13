import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);  
    boolean isRunning = true;

    while(isRunning) {
    System.out.println("===Mother Elephant Info===");
    System.out.print("Enter mother name: ");
    String name = scanner.nextLine();

    float age = 0.0f;
    System.out.print("Enter mother age: ");
    try{
      age = Float.parseFloat(scanner.nextLine());
    }
    catch (NumberFormatException e) {
      System.err.println("Please enter valid age!");
      continue;
    }

    System.out.println("===Child Info===");
    System.out.print("Enter child age: ");
    float childAge = 0.0f;
    try{
      childAge = Float.parseFloat(scanner.nextLine());
    }
    catch (NumberFormatException e) {
      System.err.println("Please enter valid age!");
      continue;
    }

    ChildElephant child = new ChildElephant(name, age, childAge);

    System.out.println("===Processing Info===");
    child.motherAgeProcess();
    child.nameProcess();
    child.childAgeProcessor();
    System.out.println("Entered name: " + child.getName());

    System.out.println("Do you want to continue?(yes/no)");
    System.out.print("Enter option: ");
    String choice = scanner.nextLine();
    if(choice.equalsIgnoreCase("no")) {
      isRunning=false;
    }
    }
  }
}
