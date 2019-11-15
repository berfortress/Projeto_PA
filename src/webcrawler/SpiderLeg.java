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
        private List<PageTitle> linkTitle = new LinkedList<PageTitle>();
        private List<Hyperlinks> links = new LinkedList<Hyperlinks>(); // Just a list of URLs
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
        private List<Hyperlinks> notFound = new LinkedList<Hyperlinks>();;
        
        
        public static void openUrlAndShowTitleAndLinks(String urlAddress, List<PageTitle> linkTitle, List<Hyperlinks> link, List<Hyperlinks> notFound) throws IOException {
         try{
            Document doc = Jsoup.connect(urlAddress).get();
            String title = doc.title();
            print("PAGE TITLE: %s \n", title);

            Elements links = doc.select("a[href]");
            print("\nLinks: (%d)", links.size());
            for (Element l : links) {
                //abs:href is important, so it transforms relative paths, e.g., href="../home.html"
                //into the full address, e.g., "www.example.com/home.html".
                print("[%s]: &lt;%s&gt;  ", l.text(), l.attr("abs:href"));
                if(l.text().isEmpty()){
                    linkTitle.add(new PageTitle("404 - Not Found"));
                    notFound.add(new Hyperlinks(l.attr("abs:href")));
                }else {
                    linkTitle.add(new PageTitle(l.text()));
                }                                

                link.add(new Hyperlinks(l.attr("abs:href")));

                for (int i=0; i < link.size(); i++) {
                    print("\t \t " + "[" + linkTitle.get(i).getPageTitleName().toUpperCase() + "]" + " " + link.get(i).getLinkName());
                }
            }
        }catch (IOException ex) {
            System.out.println("HTTP request error" + ex);
        }
    }
     
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
        
//	public boolean openUrlAndShowTitleAndLinks(String url) throws IOException { // Give it a URL and it makes an HTTP request for a web page
//		try {
//			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
//			Document doc = connection.get();
//                        String title = doc.title();
//                        print("\n PAGE TITLE: %s \n", title);
//
//			if (connection.response().statusCode() == 200) {// 200 is the HTTP OK status code
//															// indicating that everything is great.
//				print("\t**Visiting** Received web page at " + url + "\n");
//			}
//			if (!connection.response().contentType().contains("text/html")) {
//				print("\t**Failure** Retrieved something other than HTML");
//				return false;
//			}
//			Elements linksOnPage = doc.select("a[href]");
//                        print("\t Found (" + linksOnPage.size() + ") links");
////			System.out.println("These are the links found \n \n \n" + linksOnPage);
//			for (Element link : linksOnPage) {
//                            if(link.text().isEmpty()){
//                                this.linkTitle.add(new PageTitle("404 - Not Found"));
//                                this.notFound.add(new Hyperlinks(link.attr("abs:href")));
//                            }else {
//                                this.linkTitle.add(new PageTitle(link.text()));
//                            }                                
//                            
//                            this.links.add(new Hyperlinks(link.attr("abs:href")));
//			}
//                        
//                        for (int i=0; i < links.size(); i++) {
//                            print("\t \t " + "[" + linkTitle.get(i).getPageTitleName().toUpperCase() + "]" + " " + links.get(i).getLinkName());
//			}
//			return true;
//		}catch (IOException ex) {
//			print("HTTP request error" + ex);
//			return false;
//		}
//	}
//        
//        private static void print(String msg, Object... args) {
//            System.out.println(String.format(msg, args));
//        }

	public List<Hyperlinks> getLinks() {
		return this.links;
	}
        
        public List<Hyperlinks> getLinksNotFound(){
            return notFound;
        }
    
        public int getNumberOfLinksNotFound(){
            return notFound.size();
        }
        
        public List<PageTitle> getPageTitles(){
            return linkTitle;
        }
}
