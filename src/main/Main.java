/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import webcrawler.WebCrawler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import webcrawler.HyperlinksException;
import webcrawler.PageTitleException;

/**
 *
 * @author bernardo
 */


/**
 *
 * @author berna
 */
public class Main {

    public static void main(String[] args) throws IOException, PageTitleException, HyperlinksException {
        WebCrawler wc = new WebCrawler(19);
        try {
            wc.search("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
            //wc.search("https://v1.mrpiracy.top/");
            //wc.search("https://www.youtube.com/?hl=pt-PT&gl=PT");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(wc.toString());    
    }
}

