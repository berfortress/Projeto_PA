/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author bernardo
 */
public class SpiderLeg {        
        
    public void openUrlAndShowTitleAndLinks(String urlAddress, List<PageTitle> pageTitle, List<Hyperlinks> link) throws IOException {
         try{
            Document doc = Jsoup.connect(urlAddress).get();
            String title = doc.title();
            //print("PAGE TITLE: %s \n", title);

            Elements links = doc.select("a[href]");
            if(title.isEmpty()){
                pageTitle.add(new PageTitle("404 - Not Found", urlAddress));
            }else {
                pageTitle.add(new PageTitle(title, urlAddress));
            }    
            for (Element l : links) {
                  if(l.text().isEmpty()){
                    link.add(new Hyperlinks("-----", l.attr("abs:href")));
                  }else {
                    link.add(new Hyperlinks(l.text(),l.attr("abs:href")));
                  }   
            }
            removeRepeatedLinks(link);
//            for(Hyperlinks l : link){
//                System.out.println(l.getName() + " " + l.getLink());
//            }
            
        }catch (IOException ex) {
            System.out.println("HTTP request error" + ex);
        }
    }
    
    public void openUrlAndShowTitle(String urlAddress, List<PageTitle> pageTitle) throws IOException {
         try{
            Document doc = Jsoup.connect(urlAddress).get();
            String title = doc.title();
            //print("PAGE TITLE: %s \n", title);

            Elements links = doc.select("a[href]");
            if(title.isEmpty()){
                pageTitle.add(new PageTitle("404 - Not Found", urlAddress));
            }else {
                pageTitle.add(new PageTitle(title, urlAddress));
            }    
        }catch (IOException ex) {
            System.out.println("HTTP request error" + ex);
        }
    }
    
    private void removeRepeatedLinks (List<Hyperlinks> links){
        for (int i = 0; i < links.size(); i++) {
        String s1 = links.get(i).getName();     
        /* remove trailing #link */
     
            for (int j = i+1; j < links.size(); j++) {
                String s2 = links.get(j).getName();
                    if (s1.compareTo(s2)==0) {
                        links.remove(j);
                        j--;
                }
            }
        }
    }
    
    private void remove(String link){
        int indexOf = link.indexOf("#");
        if(indexOf > -1){
            link = link.substring(0, indexOf);
        }
    }
     
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
}
