/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
        
        public static void openUrlAndShowTitleAndLinks(String urlAddress, Set<PageTitle> pageTitle, List<Hyperlinks> link) throws IOException {
         try{
            Document doc = Jsoup.connect(urlAddress).get();
            String title = doc.title();
            print("PAGE TITLE: %s \n", title);

            Elements links = doc.select("a[href]");
            if(title.isEmpty()){
                pageTitle.add(new PageTitle("404 - Not Found"));
                    //notFound.add(new Hyperlinks(l.attr("abs:href")));
            }else {
                pageTitle.add(new PageTitle(title));
            }    
            for (Element l : links) {
                //abs:href is important, so it transforms relative paths, e.g., href="../home.html"
                //into the full address, e.g., "www.example.com/home.html".
//                print("[%s]: %s  ", l.text(), l.attr("abs:href"));      
                  if(l.text().isEmpty()){
                    link.add(new Hyperlinks("404 NOT FOUND", l.attr("abs:href")));
                  }else {
                    link.add(new Hyperlinks(l.text(),l.attr("abs:href")));
                  }   
            }

//            for (int i=0; i < link.size(); i++) {
//                    print("\t " + link.get(i).getLinkName());
//            }
        }catch (IOException ex) {
            System.out.println("HTTP request error" + ex);
        }
    }
     
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
}
