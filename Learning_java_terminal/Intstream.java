public class Intstream {
    public static void main(String[] args) {
        for(int i : java.util.stream.IntStream.rangeClosed(1, 200).toArray()) {
            System.out.println(i);
        }
    }
}
