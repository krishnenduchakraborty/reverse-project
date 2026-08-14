package gun_forever.company;

import java.util.Scanner;
import gun_forever.services.PaymentServices;
//import gun_forever.services.DelivaryServices;
import gun_forever.gun_list.GunList;

public class GunRuleCom extends GunList implements PaymentServices {
  private int amount = 0;
  private int attempts = 3;
  public boolean isRunning = true;
  private String gunName;
  private int payableAmount;  
  private int provider;
  private String name;
  private String adress;
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

    int choice = 0;
    try {
      choice = Integer.parseInt(scanner.nextLine());
    }
    catch (NumberFormatException e) {
      System.out.println("\nPlease enter value only!");
      continue;
    }

    if(choice <= 0) {
      System.out.println("\nOption can't be <= 0!");
      continue;
    }
    else if(choice == 1) {
      this.gunName="AK47 AssultRifle";
      System.out.println("\nThank you for choosing " + gunName);
      System.out.println("Redirecting to the payment gateway!");
      this.payableAmount=5000;
      paymetMethod();
    }
    else if(choice == 2) {
      this.gunName="M4A1 Automatic";
      System.out.println("\nThank you for choosing " + gunName);
      System.out.println("Redirecting to the payment gateway!");
      this.payableAmount=12000;
      paymetMethod();
    }
    else if(choice == 3) {
      this.gunName="Kar98 Sniper";
      System.out.println("\nThank you for choosing " + gunName);
      System.out.println("Redirecting to the payment gateway!");
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
      break;
      case 2 :
        axisPayment();
      break;
      case 3 :
        paypalPayment();
      break;
      case 4 :
        System.out.println("Exiting payment getway!");
        return;
      default :
      System.out.println("\nInvalid choice!\n");
      continue;
    }
    }
  }

  @Override
  public void sbiPayment() {
    while(isRunning) {
    System.out.println("SBI PAYMENT GATEWAY");
    System.out.println(payableAmount + " Amount to be payed for " + gunName);
    System.out.print("Enter Payable: ");

    try {
      amount = Integer.parseInt(scanner.nextLine());
    }
    catch(NumberFormatException e) {
      System.out.println("Please enter value only!");
      continue;
    }

    if(amount==payableAmount) {
      System.out.println("Payment verified!");
      System.out.println("Redirecting to delivary gateway;");
      infoGatheringAlgorithm();
      gunlist();
    }
    else {
      attempts--;
    }

    if(attempts==0) {isRunning=false;}

    }
    scanner.close();
  }

  @Override
  public void paypalPayment() {
    while(isRunning) {
    System.out.println("PAYPAL PAYMENT GATEWAY");
    System.out.println(payableAmount + " Amount to be payed for " + gunName);
    System.out.print("Enter Payable: ");

    try{
      amount = Integer.parseInt(scanner.nextLine());
    }
    catch(NumberFormatException e) {
      System.out.println("Please enter value only!");
      continue;
    }

    if(amount==payableAmount) {
      System.out.println("Payment verified!");
      System.out.println("Redirecting to the delivary gateway!");
    }
    else {
      attempts--;
    }

    if(attempts == 0) {isRunning=false;}

    }
    scanner.close();
  }

  @Override
  public void axisPayment() {
    while (isRunning) {
    System.out.println("AXIS PAYMENT GATEWAY");
    System.out.println(payableAmount + " Amount to be payed for " + gunName);
    System.out.print("Enter Payable: ");

    try{
      amount = Integer.parseInt(scanner.nextLine());
    }
    catch(NumberFormatException e) {
      System.err.println("Please enter value only!");
      continue;
    }

    if(amount==payableAmount) {
      System.out.println("Payment verified!");
      System.out.println("Redirecting to the delivary gateway!");
      //ekart();
    }
    else {
      attempts--;
    }

    if(attempts==0) {isRunning=false;}
    }
    scanner.close();
  }

  @Override
  public void infoGatheringAlgorithm() {  
    while(isRunning) {
    System.out.println("===NAME & ADRESS RECORDER===");
    System.out.println("===Fill Info===");
    System.out.print("Enter your name: ");
    name = scanner.nextLine();
    System.out.println("Enter your adress: ");
    adress = scanner.nextLine();

    System.out.println("===All Info Recorded===");
    System.out.println(name + ", Please choose delivary provider");
    System.out.println("[1] Ekart delivary");
    System.out.println("[2] Mkart delivary");
    System.out.println("[0] Exit!");
    System.out.print("Choose Provider or Exit: ");

    try {
      provider = Integer.parseInt(scanner.nextLine());
    } catch(NumberFormatException e) {
      System.err.println("Please enter integer only!");
      continue;
    }

      switch(provider) {
        case 1 :
          ekart();
          break;
        case 2 :
          mkart();
          break;
        case 0 :
          isRunning=false;
          break;
        default :
          System.out.println("Please enter valid option!");
          continue;
      }
    }
  }
}
