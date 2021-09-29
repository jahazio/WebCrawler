import java.io.IOException;
import java.io.PrintWriter;

public class write {
    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter("./repository/test.txt");
        writer.println("The first line");
        writer.println("The second line");
        writer.close();
    }
}
