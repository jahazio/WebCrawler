import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.apache.tika.language.LanguageIdentifier;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        //extracts html from the website
        String seed = "http://stackoverflow.com";
        //String html = Jsoup.connect(seed).get().html();

        //save the html as a document?
        Document document = Jsoup.connect(seed).get();
        //testing out what the document.body().text() looks like
        System.out.println(document.body().text());

        //copy and pasated code from the apache tiki language detection example but used the doc body text
        LanguageIdentifier identifier = new LanguageIdentifier(document.body().text());
        String language = identifier.getLanguage();
        System.out.println("Language of the given content is : " + language);
    }
}