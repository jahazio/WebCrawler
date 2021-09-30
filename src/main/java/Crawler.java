import org.apache.tika.language.LanguageIdentifier;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Crawler {
    //hashset stores unique values
    private HashSet<String> links;
    //count keeps track of the number of URLS crawled
    private int count =0;
    //max is the maximum number of crawls we want to do
    private int max =500;
    //what language do we want the webpages to be in
    //en = english
    //pt = portuguese
    //ru = russian
    private String desiredLanguage = "pt";
    //printwriter for updating csv
    PrintWriter out3;
    {
        try {
            out3 = new PrintWriter("report.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //contains the links that need to be crawled
    Queue<String> toCrawl = new LinkedList<>();

    //each Crawler gets its own hashset
    public Crawler() {
        links = new HashSet<String>();
    }

    public void getLinks(String URL) {
        int outlinks = 0;
        //check if the url has already been crawled
        if (!links.contains(URL)) {
            try {
                //If not add it to the index
                if (links.add(URL)) {
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
                    System.out.println(count);
                    //save the html of the webpage to repository folder
                    PrintWriter out = new PrintWriter("./repository/" + count + ".html");
                    out.println(document);
                    out.close();
                    //save the plain text of the webpage to the text folder
                    PrintWriter out2 = new PrintWriter("./text/" + count + ".txt");
                    out2.println(document.text());
                    out2.close();
                    //count the number of outlinks
                    for (Element x : linksOnPage) {
                        //check that the extracted URL is for a webpage (starts with https)
                        if (x.attr("abs:href").startsWith("https")){
                            outlinks++;
                        }
                    }
                    //save to csv
                    out3.println(URL + " number of outlinks: " + outlinks);
                    //only crawl up to the number count
                    if (count>=max){
                        out3.close();
                        System.exit(0);
                    }
                }
                //randomize the order of outlinks
                Collections.shuffle(linksOnPage);
                //depending on how many outlinks there are, add the first 10 to toCrawl or add all
                if (linksOnPage.size()>10 && toCrawl.size()<max){
                    //for loop for adding the first 10 random outlinks to the toCrawl arraylist
                    for (int i = 0; i<10; i++){
                        if (linksOnPage.get(i).attr("abs:href").startsWith("https")){
                            toCrawl.add(linksOnPage.get(i).attr("abs:href"));
                        }
                    }
                }
                //else webpage has 10 or less outlinks
                else if (linksOnPage.size()<=10 && toCrawl.size()<max){
                    for (int i = 0; i<linksOnPage.size(); i++){
                        if (linksOnPage.get(i).attr("abs:href").startsWith("https")){
                            toCrawl.add(linksOnPage.get(i).attr("abs:href"));
                        }
                    }
                }
                //put in a loop to prevent program from terminating too early/make it continue until count reaches max
                while (count<max){
                    getLinks(toCrawl.remove());
                }
            } catch (Exception e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }//end of !links.contains(URL)
    }
    public static void main(String[] args) {
        //enter a URL as the seed to start the crawl
        new Crawler().getLinks("https://agora.folha.uol.com.br/");
        //https://www.npr.org/
        //https://agora.folha.uol.com.br/
        //https://www.mk.ru/
    }
}