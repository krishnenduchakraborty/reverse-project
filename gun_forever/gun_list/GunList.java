package gun_forever.gun_list;

//import java.util.Scanner;
//import gun_forever.company.GunRuleCom;

public abstract class GunList {
  //boolean isRunning = true;
  public abstract void gunlist();

  public abstract void infoGatheringAlgorithm();

  public void ekart() {
    System.out.println("Product will be delivared through ekart!"); 
  }

  public void mkart() {
    System.out.println("Product will be delivared through mkart!");
  }
}
