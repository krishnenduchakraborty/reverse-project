public class Multiplication {
    public void show(double mul1, double mul2) {
        if(mul1 <= 0 || mul2 <= 0) {
            System.err.println("Input one and two,\nshould contain value!.");
        }else {
        double result = mul1 * mul2;
        System.out.println("Multiplication result: " + result);
        }
    }
}
