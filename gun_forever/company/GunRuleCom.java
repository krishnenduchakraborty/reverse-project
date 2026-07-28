package gun_forever.company;

import java.util.Scanner;
import gun_forever.services.PaymentServices;
//import gun_forever.services.DelivaryServices;
import gun_forever.gun_list.GunList;

public class GunRuleCom extends GunList implements PaymentServices {
  public boolean isRunning = true;
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
        return;
      default :
      System.out.println("\nInvalid choice!\n");
      break;
    }
    }
  }

  @Override
  public void sbiPayment() {
    System.out.println("SBI PAYMENT GATEWAY");
    System.out.println(payableAmount + " Amount to be payed for " + gunName);
    System.out.print("Enter Payable: ");
    int paymentAmount = 0;
    try {
      paymentAmount = Integer.parseInt(scanner.nextLine());
    }
    catch(NumberFormatException e) {
      System.out.println("Please enter value only!");
      //continue;
    }

    if(paymentAmount==payableAmount) {
      System.out.println("Redirecting to delivary gateway;");
    }
  }

  @Override
  public void paypalPayment() {
    System.out.println("PAYPA PAYMENT GATEWAY");
    System.out.println(payableAmount + " Amount to be payed for " + gunName);
    System.out.print("Enter Payable: ");
    int amount = 0;
    try{
      amount = Integer.parseInt(scanner.nextLine());
    }
    catch(NumberFormatException e) {
      System.out.println("Please enter value only!");
      //continue;
    }
  }

  @Override
  public void axisPayment() {
    int attempts = 3;
    while (isRunning) {
    System.out.println("AXIS PAYMENT GATEWAY");
    System.out.println(payableAmount + " Amount to be payed for " + gunName);
    System.out.print("Enter Payable: ");
    int amount = 0;
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
      attempts-=1;
    }

    if(attempts==0) {isRunning=false;}
    }
  }
}
