public class Division {
    public void show(double input1, double input2) {
        if(input1 == 0 && input2 == 0) {
            System.err.println("Cannot divide with zero!.");
        } else if(input2 == 0) {
            System.err.println("input two should contain some value!.");
        } else {
            double result = input1 / input2;
            System.out.println("\nDivision result: " + result + "\n");
        }
    }
}
