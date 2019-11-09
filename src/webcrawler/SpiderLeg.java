/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection;

/**
 *
 * @author berna
 */
public class SpiderLeg {
        private List<String> linkTitle = new LinkedList<String>();
        private List<String> links = new LinkedList<String>(); // Just a list of URLs
	private Document document; // This is our web page, or in other words, our document
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	public boolean openUrlAndShowTitleAndLinks(String url) throws IOException { // Give it a URL and it makes an HTTP request for a web page
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document doc = connection.get();
			this.document = doc;
                        String title = doc.title();
                        print("\n PAGE TITLE: %s \n", title);

			if (connection.response().statusCode() == 200) {// 200 is the HTTP OK status code
															// indicating that everything is great.
				print("\t**Visiting** Received web page at " + url + "\n");
			}
			if (!connection.response().contentType().contains("text/html")) {
				print("\t**Failure** Retrieved something other than HTML");
				return false;
			}
			Elements linksOnPage = doc.select("a[href]");
                        print("\t Found (" + linksOnPage.size() + ") links");
//			System.out.println("These are the links found \n \n \n" + linksOnPage);
			for (Element link : linksOnPage) {
                            if(link.text().isEmpty()){
                                this.linkTitle.add("404 - Not Found");
                            }else this.linkTitle.add(link.text());                                
                            
                            this.links.add(link.attr("abs:href"));
			}
                        
                        for (int i=0; i < links.size(); i++) {
                            print("\t \t " + "[" + linkTitle.get(i).toUpperCase() + "]" + " " + links.get(i));
			}
			return true;
		}catch (IOException ex) {
			print("HTTP request error" + ex);
			return false;
		}
	}
        
        private static void print(String msg, Object... args) {
            System.out.println(String.format(msg, args));
        }

	public List<String> getLinks() {
		return this.links;
	}
    
}
