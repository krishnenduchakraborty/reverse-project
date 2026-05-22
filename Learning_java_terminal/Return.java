class Return1 {
  // public int num =20;
    public int show(int num, int num1) {
      //this.num=num;
      int value = num + num1;
      System.out.println("Number return: " + value);

      return value;
    }
}
public class Return {
    public static void main(String[] args) {
        Return1 rt = new Return1();
        rt.show(50, 50);
        int catching = rt.show(50,50);
        rt.show(catching);
        //int num1 = rt.num+80;
        //int result = num1+50;
        //int result = rt.num=20;
        //int num2 = num1+2;
        //rt.show(result);

        //int fixedValue = rt.show(result);

        //int valueAdding = fixedValue+20;

        //rt.show(valueAdding);

    }
}
