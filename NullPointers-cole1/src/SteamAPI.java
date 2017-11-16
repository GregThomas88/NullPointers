package steamget;

import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;

public class SteamAPI {
  private static String unsafe_search(String game_name, int i) throws IOException {

    String game_name_fixed = "";

    for(int j = 0; j < game_name.length(); j++ ) {
        if( game_name.charAt(j) == ' ' )
          game_name_fixed += "%20";
        else {
          game_name_fixed += game_name.charAt(j);
        } 
    }

    try {
      Document doc = Jsoup.connect("http://store.steampowered.com/search/?term=" + game_name_fixed + "&category1=998").get();
      String[] prices = doc.select("div.col.search_price.responsive_secondrow").eq(i).text().split("\\s");
      String price = prices[prices.length-1];
      if (price.indexOf('.')<0)
        price = "nothing, it is free";
      return price;
    }
    catch (IOException e) {
      return "GameNotFound";
    }

  }

  public static String search(String game, int i){
    try {
      return unsafe_search(game, i);
    } catch (IOException e) {
      return "not listed on steam";
    }
  }


    public static String search_category(String category, int i){

        if( category.equals("action")) {
            return action_search( "Action", i );
        }
        else if( category.equals("adventure")) {
            return action_search( "Adventure", i );
        }
        else if( category.equals("indie")) {
            return action_search( "Indie", i );
        }
        else if( category.equals("casual")) {
            return action_search( "Casual", i );
        }
        else if( category.equals("massively multiplayer")) {
            return action_search( "Massively Multiplayer", i );
        }
        else if( category.equals("racing")) {
            return action_search( "Racing", i );
        }
        else if( category.equals("r.p.g.")) {
            return action_search( "RPG", i );
        }
        else if( category.equals("simulation")) {
            return action_search( "Simulation", i );
        }
        else if( category.equals("sports")) {
            return action_search( "Sports", i );
        }
        else if( category.equals("strategy")) {
            return action_search( "Strategy", i );
        }
        else if( category.equals("sexy")) {
            return action_search( "Sexy", i );
        }
        else {
            return action_search( category, i ); //"category not found two"; //
        }

    }


    private static String action_search( String category, int i ) {
      try {

        Document doc = Jsoup.connect("http://store.steampowered.com/tag/en/" + category + "/#p=0&tab=PopularNewReleases").get();
        //name.add(doc.select("div.tab_item_name").first().text()); //doc.select("div.col.TopSellersTable.TopSellersRows.tab_item_content.tab_item_name").first().text();  //

        String name = doc.select("div.tab_item_name").eq(i).text();
        

        if( doc.location().equals("http://store.steampowered.com/") ) {
          return "CategoryNotFound";
        }
        else { 

          return name;
        }
      }
      catch (IOException e) {
        return "action error";
      }
        
    }

    public static String narrow_search( String mainCategory, String category, int i ) {
      if( category.equals("free to play") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=113&", i ); 
      }
      else if( category.equals("indie") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=492&", i ); 
      }
      else if( category.equals("action") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=19&", i ); 
      }
      else if( category.equals("adventure") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=21&", i ); 
      }
      else if( category.equals("r.p.g.") || category.equals("role playing game")) {
        return narrow_search2( mainCategory, "tag%5B%5D=122&", i ); 
      }
      else if( category.equals("casual") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=597&", i ); 
      }
      else if( category.equals("single player") || category.equals("singleplayer")) {
        return narrow_search2( mainCategory, "tag%5B%5D=4182&", i ); 
      }
      else if( category.equals("strategy") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=9&", i ); 
      }
      else if( category.equals("violent") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=4667&", i ); 
      }
      else if( category.equals("gore") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=4345&", i ); 
      }
      else if( category.equals("multiplayer") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=3859&", i ); 
      }
      else if( category.equals("massively multiplayer") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=128&", i ); 
      }
      else if( category.equals("simulation") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=599&", i ); 
      }
      else if( category.equals("shooter") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=1774&", i ); 
      }
      else if( category.equals("great soundtrack") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=1756&", i ); 
      }
      else if( category.equals("coop") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=1685&", i ); 
      }
      else if( category.equals("f.p.s.") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=1663&", i ); 
      }
      else if( category.equals("open world") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=1695&", i ); 
      }
      else if( category.equals("atmospheric") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=4166&", i ); 
      }
      else if( category.equals("2D") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=3871&", i ); 
      }
      else if( category.equals("difficult") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=4026&", i); 
      }
      else if( category.equals("first person") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=3839&", i ); 
      }
      else if( category.equals("scifi") || category.equals("science fiction")) {
        return narrow_search2( mainCategory, "tag%5B%5D=3942&", i ); 
      }
      else if( category.equals("platformer") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=1625&", i ); 
      }
      else if( category.equals("survival") ) {
        return narrow_search2( mainCategory, "tag%5B%5D=1662&", i ); 
      }
      else if( category.equals("steam achievements") ) {
        return narrow_search2( mainCategory, "category%5B%5D=22&", i ); 
      }
      else if( category.equals("steam cloud") ) {
        return narrow_search2( mainCategory, "category%5B%5D=23&", i ); 
      }
      else if( category.equals("steam workshop") ) {
        return narrow_search2( mainCategory, "category%5B%5D=30&", i ); 
      }
      else if( category.equals("steam trading cards") ) {
        return narrow_search2( mainCategory, "category%5B%5D=29&", i ); 
      }
      else if( category.equals("full controller support") ) {
        return narrow_search2( mainCategory, "category%5B%5D=28&", i ); 
      }
      else if( category.equals("windows") ) {
        return narrow_search2( mainCategory, "os%5B%5D=win&", i ); 
      }
      else if( category.equals("mac") || category.equals("mac os") || category.equals("mac os x")) {
        return narrow_search2( mainCategory, "os%5B%5D=mac&", i ); 
      }
      else if( category.equals("linux") || category.equals("steamos") || category.equals("steam os")) {
        return narrow_search2( mainCategory, "os%5B%5D=mac&", i ); 
      }
      else {
        return "CategoryNotFound";
      }
    }

    private static String narrow_search2( String mainCategory, String category, int i ) {
      try {
        Document doc = Jsoup.connect("http://store.steampowered.com/tag/en/" + mainCategory + "/#" + category + "p=0&tab=PopularNewReleases").get();
        String name = doc.select("div.tab_item_name").eq(i).text();

        return name;
      }
      catch (IOException e) {
        return "narrow error";
      }
    }

    public static String TopTen5DollarGames ( int i ) {
      try {
        Document doc = Jsoup.connect("http://store.steampowered.com/search/?filter=ut2").get();
          String name = doc.select("span.title").eq(i).text();
          return name;
      }
      catch (IOException e) {
        return "top 5 dollar games error";
      }
        
    }
    
    public static String TopTen10DollarGames ( int i ) {
      try {
        Document doc = Jsoup.connect("http://store.steampowered.com/search/?filter=ut1").get();
        String name = doc.select("span.title").eq(i).text();
        return name;
      }
      catch (IOException e) {
        return "top 10 dollar games error";
      }
        
    }

    public static String getName(String game_name, int i) {
      try {

        String game_name_fixed = "";

        for(int j = 0; j < game_name.length(); j++ ) {
            if( game_name.charAt(j) == ' ' )
              game_name_fixed += "%20";
            else {
              game_name_fixed += game_name.charAt(j);
            } 
        }
        Document doc = Jsoup.connect("http://store.steampowered.com/search/?term=" + game_name_fixed + "&category1=998").get();
        String name = doc.select("span.title").eq(i).text();
        return name;
      }
      catch (IOException e) {
        return "game name error";
      }
    }


}