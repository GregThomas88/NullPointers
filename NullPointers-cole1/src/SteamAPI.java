package steamget;

import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;

public class SteamAPI {
  private static String unsafe_search(String game) throws IOException {
    Document doc = Jsoup.connect("http://store.steampowered.com/search/?term=" + game + "&category1=998").get();
    String[] prices = doc.select("div.col.search_price.responsive_secondrow").first().text().split("\\s");
    String price = prices[prices.length-1];
    if (price.indexOf('.')<0)
      price = "$0.00";
    return price;
    }

  public static String search(String game){
    try {
      return unsafe_search(game);
    } catch (IOException e) {
      return "not listed on steam";
    }
  }


  public static String search_category(String category){
    try {
      if( category == "action" ) {
        return action_search( void );
      }
      else {
        return "category not found";
      }
      } catch (IOException e) {
        return "category error";
      }
    }
  }

  private static String action_search( void ) throws IOException {
    Document doc = Jsoup.connect("http://store.steampowered.com/tag/en/Action/#p=0&tab=TopSellers").get();
    String[] name = doc.select("div.col.tab_item_name").text();
    return name.first();
  }



}
