/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import DAO.WcJsonDAO;
import DAO.WcSerializationDAO;
import DAO.WcDAOFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.LinkException;
import models.WebsiteException;
import java.util.Scanner;
import models.Iterative;
import models.Website;
import webcrawler.WebCrawler;
import DAO.WcDAO;

/**
 *
 * @author bernardo
 */

public class Main {
    

    public static void main(String[] args) throws IOException, WebsiteException, LinkException {
        WcDAO dao = WcDAOFactory.createWcDAO("onejson", "./");
        
        System.out.println("=====================================================");
        System.out.println("\t\t     WELCOME");
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
                    wc.automatic("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
                    
                    if (!wc.getLinksVisited().isEmpty() || maxNum >= 1) {
                        System.out.println(wc.toString());
                    }
                    
                    for (Website p : wc.webSitesVisited()) {
                        dao.saveWC(p);
                    }
                } else if (choose.equalsIgnoreCase("n") || choose.equalsIgnoreCase("no")) {
                    System.out.print("Insira url : ");
                    String url = myObj.next();
                    System.out.print("Número máximo de páginas procuradas: ");
                    int maxNum = myObj.nextInt();
                    WebCrawler wc = new WebCrawler(maxNum);
                    wc.automatic(url);
                    if (!wc.getLinksVisited().isEmpty() || maxNum >= 1) {
                        System.out.println(wc.toString());
                    }
                    
                    for (Website p : wc.webSitesVisited()) {
                        dao.saveWC(p);
                    }
                }
                
            } else if(option == 2){
                System.out.print("Pretende usar página default: \"https://moodle.ips.pt/1920/course/index.php?categoryid=7\" [YES/NO] -> ");
                String choose = myObj.next();
                if (choose.equalsIgnoreCase("y") || choose.equalsIgnoreCase("yes")) {
                    
                    Iterative wc = new Iterative();
                    wc.executeIterative("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
                    if (!wc.getLinksVisited().isEmpty()) {
                        System.out.println(wc.toString());
                    }
                    
                    for (Website p : wc.webSitesVisited()) {
                        dao.saveWC(p);
                    }
                } else if (choose.equalsIgnoreCase("n") || choose.equalsIgnoreCase("no")) {
                    System.out.print("Insira url : ");
                    String url = myObj.next();
                    
                    Iterative wc = new Iterative();
                    
                    for (Website p : wc.webSitesVisited()) {
                        dao.saveWC(p);
                    }
                }
            }else if(option != 1 || option != 2){
                System.out.println("Escolha apenas 1 ou 2");
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

