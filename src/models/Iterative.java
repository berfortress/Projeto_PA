/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import adtgraph.Vertex;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import webcrawler.WebCrawler;

/**
 *
 * @author fabio
 */
public class Iterative extends WebCrawler {

    private final WebLink webLink; //Receiver e Originator
    private final static Scanner sc = new Scanner(System.in);
    private final CareTaker care;

    public Iterative() {
        this.webLink = new WebLink(null, null);
        this.care = new CareTaker();
    }

    private void writeMenu() {
        System.out.println("\n \n *************** MODO ITERATIVO ********************");

        System.out.println("----------------------------------------------------------------------");
        int count = 1;
        System.out.println("Para aceder às páginas a que tem acesso insira - " + count);

        System.out.println("----------------------------------------------------------------------");
        System.out.println("Para sair insira - s");

        System.out.println("----------------------------------------------------------------------");
        System.out.println("Para fazer undo insira - u");

        System.out.println("----------------------------------------------------------------------");
        System.out.print("Insira o número da página à qual quer aceder : ");
    }

    public void executeIterative(String url) throws IOException, WebsiteException, LinkException {
        openUrlAndShowTitleAndLinks(url);

        List<Website> websites = webSitesVisited();
        List<Link> links = getLinksVisited();
        Website web = websites.get(0);

        int count = 1;
        while (websites.size() <= 8) {
            openUrlAndShowTitleAndLinks(links.get(count).getLink());
            bubbleSort(links);
            if(count < websites.size() && count < links.size())
                addEdge(web, websites.get(count), links.get(count));
            count++;
        }

        writeMenu();

        String op = "";
        op = sc.next();

        while (!op.equals("s")) {
            switch (op) {
                case "1":
                    List<Link> linkList = new ArrayList<>();
                    List<Website> websList = new ArrayList<>();
                    System.out.println("----------------------------------------------------------------------");
                    System.out.println("[PÁGINA ATUAL] - " + web);
                    System.out.println("----------------------------------------------------------------------");

                    System.out.println("--------Páginas a que pode aceder--------");
                    String str = "";
                    int count1 = 1;
                    List<Website> listOfWebs = new ArrayList<>();
                    Vertex<Website> webVertex = getVertex(web);
                    for (Vertex<Website> w : getAdjacents(webVertex)) {
                        str += count1 + " - " + w.element().getURL() + "\n";
                        listOfWebs.add(w.element());
                        count1++;
                        linkList.add(w.element().getLink());
                        websList.add(w.element());
                    }

                    webLink.setLinks(linkList);
                    webLink.setWebsite(web);
                    webLink.setWebsites(websList);
                    care.saveState(webLink);

                    System.out.println(str);
                    System.out.println("----------------------------------------------------------------------");
                    System.out.println("\n" + listOfWebs + "\n");
                    System.out.print("Insira o número da página à qual deseja aceder : ");
                    String v = "";
                    v = sc.next();

                    int val = Integer.parseInt(v);
                    if (val - 1 <= websites.size()) {
                        web = listOfWebs.get(val - 1);
                        List<Link> linksList = openUrlAndShowTitleAndLinks(web.getURL());
                        System.out.println("\n" + linksList + "\n");

                        int count2 = 0;
                        while (linksList.size() - 1 > count2 && websites.size() > count2) {
                            openUrlAndShowTitleAndLinks(linksList.get(count2).getLink());
                            bubbleSort(linksList);
                            if (getVertex(websites.get(count2)) != null && getEdge(linksList.get(count2)) == null) {
                                addEdge(web, websites.get(count2), linksList.get(count2));
                            }
                            count2++;
                            count++;
                        }
                    } else {
                        System.out.print("Não foi possivel obter a página a pesquisar com esse número\n");
                    }

                    break;

                case "u":
                    if (care.objMementos.isEmpty()) {
                        System.out.println("------------------------------------------------------------------------");
                        System.out.println("Não é possivel efetuar mais o comando undo, pois está na página inicial!");
                        System.out.println("------------------------------------------------------------------------");
                    } else {
                        undo();
                        web = webLink.getWebsite();
                        Vertex<Website> webVertex1 = getVertex(web);
                        List<String> dupWebs = new ArrayList<>();

                        String str1 = "[PÁGINA ATUAL] " + web.toString() + "\n--------Páginas a que pode aceder--------\n";
                        List<Website> webs = getAdjacentsElem(web);
                        bubbleSortWeb(webs);
                        for (Website w : webs) {
                            if(!dupWebs.contains(w.getURL())){
                                str1 += w.toString() + "\n";
                                dupWebs.add(w.getURL());
                            }
                        }
                        System.out.println(str1);
                    }
                    break;
            }
            //writeMenu();
            System.out.print("Nova Opção -> ");
            op = sc.next();

        }
    }

    private void undo() {
        care.restoreState(webLink);
    }
}
