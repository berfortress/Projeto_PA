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
import webcrawler.HyperlinksException;
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

    public static void main(String[] args) throws IOException, PageTitleException, HyperlinksException {
        WebCrawler wc = new WebCrawler(1);
        
        //opendUrlAndShowTitleAndLinks("https://jsoup.org/");
        //wc.search("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
        //wc.search("https://v1.mrpiracy.top/");
        try {
            wc.search("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
            //wc.search("https://v1.mrpiracy.top/");


        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        for(PageTitle p : linkTitle){
//            try {
//                wc.addPageTitle(p);
//            } catch (InvalidVertexException ex) {
//                throw new PageTitleException("Website with name does not exist");
//            }
//        }
        
//        System.out.println(wc.getAllPageTitle());
//        System.out.println(wc.toString());
//        for(Hyperlinks h : wc.getLinks())
//            System.out.println(h.getLinkName()+"\n");
    }
}

