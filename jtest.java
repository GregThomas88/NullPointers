package com.company;


import java.util.Scanner;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner reader = new Scanner(System.in);
        System.out.println("Give a game: ");
        String game = reader.next();
        Document d=Jsoup.connect("http://store.steampowered.com/search/?term=" + game + "&category1=998").get();
        //Document d=Jsoup.connect("http://store.steampowered.com/app/" + url + "/").cookie("mature_content", "1").cookie("\
birthtime","0").get();
        //
        String[] prices = d.select("div.col.search_price.responsive_secondrow").first().text().split("\\s");
        System.out.println(prices[prices.length-1]);
        //        System.out.println(d);
    }
}

