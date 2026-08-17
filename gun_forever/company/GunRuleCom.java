package gun_forever.company;

import java.util.Scanner;
import gun_forever.services.PaymentServices;
//import gun_forever.services.DelivaryServices;
import gun_forever.gun_list.GunList;

public class GunRuleCom extends GunList implements PaymentServices {
  private boolean isRunning = true;
  private String gunName;
  private int payableAmount;
  Scanner scanner = new Scanner(System.in);

  @Override
  public void gunlist() {
    while(isRunning) {
    System.out.println("===WELCOME TO GUN RULE COMPANY PORTAL===");
    System.out.println("[List of available guns!]");
    System.out.println("[1]AK47 AssultRifle, 5 thousand rupes!");
    System.out.println("[2]M4A1 Automatic, 12 thousand rupes!");
    System.out.println("[3]Kar98 Sniper, 20 thousand rupes!");
    System.out.println("[4]Exit!\n");
    System.out.print("Choose Gun Or Exit: ");

    int choice = readIntInput();

    //if(choice == 4) isRunning=false;
    if(choice <= 0) {
      System.out.println("\nChoice can't be <= 0!");
      continue;
    }
    else if(choice == 1) {
      this.gunName="AK47 AssultRifle";
      System.out.println("\nThank you for choosing " + gunName);
      System.out.println("Redirecting to payment gateway!");
      this.payableAmount=5000;
      paymetMethod();
    }
    else if(choice == 2) {
      this.gunName="M4A1 Automatic";
      System.out.println("\nThank you for choosing " + gunName);
      System.out.println("Redirecting to payment gateway!");
      this.payableAmount=12000;
      paymetMethod();
    }
    else if(choice == 3) {
      this.gunName="Kar98 Sniper";
      System.out.println("\nThank you for choosing " + gunName);
      System.out.println("Redirecting to payment gateway!");
      this.payableAmount=20000;
      paymetMethod();
    }
    else if(choice == 4) {
      isRunning=false;
    }
    else {
      System.out.println("\nPlease choose between given options!\n");
      continue;
    }
    }
  }

  public void paymetMethod() {
    while(isRunning) {
    System.out.println("\nChoose Payment Method!");
    System.out.println("[1]SBI PAYMENT");
    System.out.println("[2]AXIS PAYMENT");
    System.out.println("[3]PAYPAL PAYMENT");
    System.out.println("[4]EXIT PAYMENT GATEWAY\n");
    System.out.print("Choose Method Or Exit: ");
    int option = 0;
    try{
      option = Integer.parseInt(scanner.nextLine());
    }
    catch(NumberFormatException e) {
      System.out.println("\nPlease enter value only!\n");
      continue;
    }
    switch(option) {
      case 1 :
        sbiPayment();
        return;
      case 2 :
        axisPayment();
        return;
      case 3 :
        paypalPayment();
        return;
      case 4 :
        System.out.println("Exiting payment getway!");
        return;
      default :
      System.out.println("\nInvalid choice!\n");
      continue;
    }
    }
  }

  private void processPayment(String bankName) {
    int attempts = 3;
    while(isRunning) {
      System.out.println(bankName + " Payment gateway!");
      System.out.println(payableAmount + " To be payed for " + gunName);
      System.out.print("Enter Payable: ");
      int amount = readIntInput();
      if(amount==payableAmount) {
        System.out.println("Payment verified!");
        System.out.println("Redirecting to delevery portal!");
        infoGatheringAlgorithm();
        break;
      }
      else {
        attempts--;
        System.out.println("Attempts left " + attempts);
      }
    }
  }

  private int readIntInput() {
    try {
      return Integer.parseInt(scanner.nextLine());
    }
    catch (NumberFormatException e) {
      System.err.println("Please enter value only!");
      return -1;
    }
  }

  @Override
  public void sbiPayment() {
    processPayment("SBI");
  }

  @Override
  public void paypalPayment() {
    processPayment("PAYPAL");
  }

  @Override
  public void axisPayment() {
    processPayment("AXIS");
  }

  @Override
  public void infoGatheringAlgorithm() {
    while(isRunning) {
    System.out.println("===NAME & ADRESS RECORDER===");
    System.out.println("===Fill Info===");
    System.out.print("Enter your name: ");
    String name = scanner.nextLine();
    System.out.print("Enter your adress: ");
    String address = scanner.nextLine();

    System.out.println("===All Info Recorded===");
    System.out.println(name + ", Please choose delivery provider.");
    System.out.println("[1] Ekart delivery");
    System.out.println("[2] Mkart delivery");
    System.out.println("[0] Exit!");
    System.out.print("Choose Provider or Exit: ");

    int options;
    try {
      options = Integer.parseInt(scanner.nextLine());
    } catch(NumberFormatException e) {
      System.err.println("Please enter integer only!");
      continue;
    }

      switch(options) {
        case 1 :
          ekart();
          return;
        case 2 :
          mkart();
          return;
        case 0 :
          System.out.println("Exiting Delevery Portal!");
          return;
        default :
          System.out.println("Please enter valid option!");
          continue;
      }
    }
  }
}
