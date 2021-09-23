import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashSet;
/*TODO:
check the page for language before downloading
save the html to repository folder
add the URL and number of outlinks to report.csv
*/

public class Crawler {

    private HashSet<String> links;
    private int count =0;

    public Crawler() {
        links = new HashSet<String>();
    }

    public void getLinks(String URL) {
        String html;
        //checks if you have already crawled the URLs but doesn't check for duplicate content
        if (!links.contains(URL)) {
            try {
                //If not add it to the index
                if (links.add(URL)) {
                    //print the URL
                    System.out.println(URL);
                    html = Jsoup.connect(URL).get().html();
                    //print the html code
                    System.out.println(html);
                    //only crawl up to the number count
                    count +=1;
                    if (count>=2){
                        System.exit(0);
                    }
                }
                //gets the HTML
                Document document = Jsoup.connect(URL).get();
                //extract links to other URLs from the HTML
                Elements linksOnPage = document.select("a[href]");
                //For each extracted URL go back to start of try
                for (Element page : linksOnPage) {
                    getLinks(page.attr("abs:href"));
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        //enter a URL as the seed to start the crawl
        new Crawler().getLinks("https://en.wikipedia.org/");
    }

}