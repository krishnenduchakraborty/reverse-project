public class Elephant {
  private String elephantName;
  private float motherAge;

  Elephant(String name, float motherAge) {
    if(name==null || name.trim().isEmpty()) {
      this.elephantName="Unknown";
    }
    else {
      this.elephantName=name;
    }
    this.motherAge=motherAge;
  }

  public void motherAgeProcess() {
    float age = this.motherAge;
    if(age >=120 || age <=0) {
      ageNotValid();
    }
    else {
      System.out.println("Age is: " + this.motherAge);
    }
  }

  public void ageNotValid() {
    System.out.println("Age is not valid! " + this.motherAge);
  }

  public void nameProcess() {
    if(this.elephantName.equalsIgnoreCase("Rekha")){
      System.out.println("Name is matched: " + this.elephantName);
    }
    else {
      validationCheck();
    }
  }

  public void validationCheck() {
    int length = this.elephantName.length();
    if(length!=5) {
      System.out.println("Name length is not 5!");
      return;
    }
  }

  public String getName() {
    return this.elephantName;
  }
}
