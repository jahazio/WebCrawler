import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Test {
    public static void main(String[] args) {
        try{
            //gets the HTML?
            Document document = Jsoup.connect("https://www.globo.com/").get();
            Element taglang = document.select("html").first();
            System.out.println(taglang.attr("lang"));

        } catch (Exception e) {
            System.err.println("bruh" + e.getMessage());
        }

    }
}