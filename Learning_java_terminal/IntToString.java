class INTtostring {
    int numbers = 12378984;
    void show() {
        String str = Integer.toString(numbers);
        System.out.println("This is converted string,\nFrom integer: " + str);
        System.out.println("Length of the string: " + str.length());
    }
}
public class IntToString {
    public static void main(String[] args) {
        INTtostring INT = new INTtostring();
        INT.show();
    }
}
