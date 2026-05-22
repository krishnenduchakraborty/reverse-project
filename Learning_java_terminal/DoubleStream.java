public class DoubleStream {
    public static void main(String[] args) {
        for(int values : java.util.stream.IntStream.rangeClosed(20, 50).count()) {
           System.out.println(values);
        }
    }
}
