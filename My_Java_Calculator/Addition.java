public class Addition {
    public void show(double a, double b) {
        if(a > 0 && b > 0) {
        double result = a + b; 
        System.out.println("Addition result is: " + result);  
        }
        else {
            System.err.println("Entered value should be, greater then zero!");
        }
    }
}
