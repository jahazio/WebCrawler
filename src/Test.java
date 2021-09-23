import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        //extracts html from the website
        String seed = "http://stackoverflow.com";
        String html = Jsoup.connect(seed).get().html();
        //System.out.println(html);
        //write html to csv file





    }
}