/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import webcrawler.Hyperlinks;
import webcrawler.PageTitle;
import webcrawler.WebCrawler;
import webcrawler.SpiderLeg;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author berna
 */


/**
 *
 * @author berna
 */
public class Main {

    public static void main(String[] args) throws IOException {
        WebCrawler wc = new WebCrawler(2);
        //opendUrlAndShowTitleAndLinks("https://jsoup.org/");
//        wc.search("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
        wc.search("https://v1.mrpiracy.top/");
        System.out.println("FABI ESTUDA");
    }
}

