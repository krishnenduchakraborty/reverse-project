public class Stream {
    public static void main(String[] args) {
        for (int l : java.util.stream.IntStream.rangeClosed(1, 2272782).toArray()) {
            System.out.println(l);
        }
    }
}
