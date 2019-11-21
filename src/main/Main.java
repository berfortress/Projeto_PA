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
import java.util.Scanner;

/**
 *
 * @author bernardo
 */

public class Main {
    

    public static void main(String[] args) throws IOException, PageTitleException, HyperlinksException {
        System.out.println("WEB CRAWLING \n");
        System.out.println(" 1- MODO AUTOMÁTICO");
        System.out.println(" 2- MODO ITERATIVO");
        Scanner myObj = new Scanner(System.in);
        try {
            int option = myObj.nextInt();
            if(option == 1){
                System.out.println("\n \n *************** MODO AUTOMÁTICO ********************");
                System.out.println("Insira url : ");
                String url = myObj.next();
                System.out.println("Número máximo de páginas procuradas: ");
                int maxNum = myObj.nextInt();
                WebCrawler wc = new WebCrawler(maxNum);             
//                wc.search("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
                wc.search(url);
                if(!wc.getLinksVisited().isEmpty() || maxNum >=1 ){
                  System.out.println(wc.toString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

