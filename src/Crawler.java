import org.apache.tika.language.LanguageIdentifier;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

/*TODO:
add the URL and number of outlinks to report.csv
*/

public class Crawler {
    //hashset stores unique values
    private HashSet<String> links;
    //count keeps track of the number of URLS crawled
    private int count =0;
    //max is the maximum number of crawls we want to do
    private int max =1;
    //what language do we want the webpages to be in
    private String desiredLanguage = "en";

    //each Crawler gets its own hashset
    public Crawler() {
        links = new HashSet<String>();
    }

    public void getLinks(String URL) {
        //check if the url has already been crawled
        if (!links.contains(URL)) {
            try {
                //If not add it to the index
                if (links.add(URL)) {
                    //print the URL
                    System.out.println(URL);
                }
                //gets the HTML?
                Document document = Jsoup.connect(URL).get();
                //extract links to other URLs from the HTML
                Elements linksOnPage = document.select("a[href]");
                //check what language the webpage is
                LanguageIdentifier identifier = new LanguageIdentifier(document.body().text());
                String language = identifier.getLanguage();
                //only save the html and text if webpage is in desired language
                if(language.equals(desiredLanguage)){
                    count +=1;
                    //save the html of the webpage to repository folder
                    PrintWriter out = new PrintWriter("./repository/" + count + ".html");
                    out.println(document);
                    out.close();
                    //save the plain text of the webpage to the text fodler
                    PrintWriter out2 = new PrintWriter("./text/" + count + ".txt");
                    out2.println(document.text());
                    out2.close();
                    //only crawl up to the number count
                    if (count>=max){
                        System.exit(0);
                    }
                }
                //For each extracted URL perform the getLinks method
                for (Element page : linksOnPage) {
                    getLinks(page.attr("abs:href"));
                }
            } catch (Exception e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        //enter a URL as the seed to start the crawl
        new Crawler().getLinks("https://en.wikipedia.org/wiki/California_State_Polytechnic_University,_Pomona");
    }

}