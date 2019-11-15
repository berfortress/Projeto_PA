/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import adtgraph.InvalidVertexException;
import webcrawler.Hyperlinks;
import webcrawler.PageTitle;
import webcrawler.WebCrawler;
import webcrawler.SpiderLeg;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webcrawler.PageTitleException;

/**
 *
 * @author berna
 */


/**
 *
 * @author berna
 */
public class Main {

    public static void main(String[] args) throws IOException, PageTitleException {
        WebCrawler wc = new WebCrawler(2);
        List<PageTitle> linkTitle = new LinkedList<PageTitle>();
        List<Hyperlinks> links = new LinkedList<Hyperlinks>(); // Just a list of URLs
        List<Hyperlinks> notFound = new LinkedList<Hyperlinks>();
        
        //opendUrlAndShowTitleAndLinks("https://jsoup.org/");
        //wc.search("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
        //wc.search("https://v1.mrpiracy.top/");
        try {
            wc.search("https://moodle.ips.pt/1920/course/index.php?categoryid=7", linkTitle, links, notFound);

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(PageTitle p : linkTitle){
            try {
                wc.addPageTitle(p);
            } catch (InvalidVertexException ex) {
                throw new PageTitleException("Website with name does not exist");
            }
        }
        
        System.out.println(wc.toString());
    }
        
//     private static void openUrlAndShowTitleAndLinks1(String urlAddress, List<PageTitle> linkTitle, List<Hyperlinks> link, List<Hyperlinks> notFound) throws IOException {
//         try{
//            Document doc = Jsoup.connect(urlAddress).get();
//            String title = doc.title();
//            print("PAGE TITLE: %s \n", title);
//
//            Elements links = doc.select("a[href]");
//            print("\nLinks: (%d)", links.size());
//            for (Element l : links) {
//                //abs:href is important, so it transforms relative paths, e.g., href="../home.html"
//                //into the full address, e.g., "www.example.com/home.html".
//                print("[%s]: &lt;%s&gt;  ", l.text(), l.attr("abs:href"));
//                if(l.text().isEmpty()){
//                    linkTitle.add(new PageTitle("404 - Not Found"));
//                    notFound.add(new Hyperlinks(l.attr("abs:href")));
//                }else {
//                    linkTitle.add(new PageTitle(l.text()));
//                }                                
//
//                link.add(new Hyperlinks(l.attr("abs:href")));
//
//                for (int i=0; i < link.size(); i++) {
//                    print("\t \t " + "[" + linkTitle.get(i).getPageTitleName().toUpperCase() + "]" + " " + link.get(i).getLinkName());
//                }
//            }
//        }catch (IOException ex) {
//            System.out.println("HTTP request error" + ex);
//        }
//    }
//     
//    private static void print(String msg, Object... args) {
//        System.out.println(String.format(msg, args));
//    }

}

