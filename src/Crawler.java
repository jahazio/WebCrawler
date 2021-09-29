package crawler;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.opencsv.CSVWriter;


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

    public void getLinks(String URL) throws IOException {
        String html;
        //checks if you have already crawled the URLs but doesn't check for duplicate content
        if (!links.contains(URL)) {
            try {
                //If not add it to the index
                if (links.add(URL)) {
                    //print the URL
                    System.out.println(URL);
                    List<String[]> csvData = createCsvDataSimple();
//                    try (CSVWriter writer = new CSVWriter(new FileWriter("testing.csv"))) {
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
        //enter a URL as the seed to start the crawl
        new Crawler().getLinks("https://en.wikipedia.org/");
        List<String[]> csvData = createCsvDataSimple();

        // default all fields are enclosed in double quotes
        // default separator is a comma
        try (CSVWriter writer = new CSVWriter(new FileWriter("testing.csv"))) {
            writer.writeAll(csvData);
        }

    }

    private static List<String[]> createCsvDataSimple() {
       String[] header = {"URL", "Number of outlinks"};
       //for(int i=0; i<=size; i++){
        String[] record1 = {"1", "first name", "address 1", "11111"};
        String[] record2 = {"2", "second name", "address 2", "22222"};
        String[] record3 = {"3", "third name", "address 3", "33333"};


        List<String[]> list = new ArrayList<>();
        list.add(header);
        list.add(record1);
        list.add(record2);
        list.add(record3);

        return list;
    }
    

    public static void main(String[] args) throws IOException {
        //enter a URL as the seed to start the crawl
        new Crawler().getLinks("https://en.wikipedia.org/");
    }
    {


   
}
}
