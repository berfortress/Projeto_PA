/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import DAO.wcDAO;
import DAO.wcJsonDAO;
import webcrawler.WebCrawler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import webcrawler.HyperlinksException;
import webcrawler.PageTitleException;
import java.util.Scanner;
import webcrawler.PageTitle;

/**
 *
 * @author bernardo
 */

public class Main {
    

    public static void main(String[] args) throws IOException, PageTitleException, HyperlinksException {
        wcDAO jsonDAO = new wcJsonDAO("C:\\Users\\fabio\\OneDrive\\Documentos\\GitHub\\Projeto_PA");
        
        System.out.println("=====================================================");
        System.out.println("\t\t     BEM VINDO!");
        System.out.println("=====================================================");
        System.out.println("WEB CRAWLING \n");
        System.out.println(" 1- MODO AUTOMÁTICO");
        System.out.println(" 2- MODO ITERATIVO");
        System.out.print("Insira o número do modo que pretende utilizar: ");
        Scanner myObj = new Scanner(System.in);
        try {
            int option = myObj.nextInt();
            if(option == 1){
                System.out.println("\n \n *************** MODO AUTOMÁTICO ********************");
                System.out.print("Pretende usar página default: \"https://moodle.ips.pt/1920/course/index.php?categoryid=7\" [YES/NO] -> ");
                String choose = myObj.next();
                if (choose.equalsIgnoreCase("y") || choose.equalsIgnoreCase("yes")) {
                    System.out.print("Número máximo de páginas procuradas: ");
                    int maxNum = myObj.nextInt();
                    WebCrawler wc = new WebCrawler(maxNum);
                    wc.search("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
                    if (!wc.getLinksVisited().isEmpty() || maxNum >= 1) {
                        System.out.println(wc.toString());
                    }
                    
                    for (PageTitle p : wc.getPagesVisited()) {
                        jsonDAO.saveWC(p);
                    }
                } else if (choose.equalsIgnoreCase("n") || choose.equalsIgnoreCase("no")) {
                    System.out.print("Insira url : ");
                    String url = myObj.next();
                    System.out.print("Número máximo de páginas procuradas: ");
                    int maxNum = myObj.nextInt();
                    WebCrawler wc = new WebCrawler(maxNum);
                    wc.search(url);
                    if (!wc.getLinksVisited().isEmpty() || maxNum >= 1) {
                        System.out.println(wc.toString());
                    }
                    
                    for (PageTitle p : wc.getPagesVisited()) {
                        jsonDAO.saveWC(p);
                    }
                }
                
            } else if(option == 2){
                System.out.println("\tPedimos desculpa mas estamos em manutenção :(");
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

