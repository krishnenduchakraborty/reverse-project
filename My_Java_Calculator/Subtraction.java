public class Subtraction {
    public void show(double s, double p) {
        if(s >= -10 && p >= -10) {           
            double result = s - p;
            System.out.println("Subtraction result: " + result);
        }
        else {          
            System.err.println("Subtraction limit is, set to -10 and above only!");
        }
    }
}
