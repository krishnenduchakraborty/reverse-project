public class ChildElephant extends Elephant {
  private float childAge;

  ChildElephant(String name, float motherAge, float age) {
    super(name, motherAge);
    this.childAge=age;
  }

  public void childAgeProcessor() {     
    float age = this.childAge;
    if(age>=1 && age <= 10) {
      System.out.println("Child elephant age: " + age + " years!");
    }
    else {
      childAgeMatcher();
    }
  }

  public float getChildAge() {
    return this.childAge;
  }

  public void childAgeMatcher() {
    if(getChildAge() > 10 || getChildAge() <=0) {
    System.out.println("Not a valid child age! " + this.childAge);
  }
  }
}
