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

        if( category.equals("action")) {
            return action_search( "Action" );
        }
        else if( category.equals("adventure")) {
            return action_search( "Adventure" );
        }
        else if( category.equals("indie")) {
            return action_search( "Indie" );
        }
        else if( category.equals("casual")) {
            return action_search( "Casual" );
        }
        else if( category.equals("massively multiplayer")) {
            return action_search( "Massively Multiplayer" );
        }
        else if( category.equals("racing")) {
            return action_search( "Racing" );
        }
        else if( category.equals("r.p.g.")) {
            return action_search( "RPG" );
        }
        else if( category.equals("simulation")) {
            return action_search( "Simulation" );
        }
        else if( category.equals("sports")) {
            return action_search( "Sports" );
        }
        else if( category.equals("strategy")) {
            return action_search( "Strategy" );
        }
        else if( category.equals("sexy")) {
            return action_search( "Sexy" );
        }
        else {
            return action_search( category ); //"category not found two"; //
        }

    }


    private static String action_search( String category ) {
    	try {
        	Document doc = Jsoup.connect("http://store.steampowered.com/tag/en/" + category + "/#p=0&tab=PopularNewReleases").get();
        	String name = doc.select("div.tab_item_name").first().text(); //doc.select("div.col.TopSellersTable.TopSellersRows.tab_item_content.tab_item_name").first().text();  // 
        	
 
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

    public static String narrow_search( String mainCategory, String category ) {
    	if( category.equals("free to play") ) {
    		return narrow_search2( mainCategory, "tag%5B%5D=113&" ); 
    	}
      else if( category.equals("indie") ) {
    		return narrow_search2( mainCategory, "tag%5B%5D=492&" ); 
    	}
      else if( category.equals("action") ) {
    		return narrow_search2( mainCategory, "tag%5B%5D=19&" ); 
    	}
      else if( category.equals("adventure") ) {
    		return narrow_search2( mainCategory, "tag%5B%5D=21&" ); 
    	}
      else if( category.equals("r.p.g.") || category.equals("role playing game")) {
    		return narrow_search2( mainCategory, "tag%5B%5D=122&" ); 
    	}
      else if( category.equals("casual") ) {
    		return narrow_search2( mainCategory, "tag%5B%5D=597&" ); 
    	}
      else if( category.equals("single player") || category.equals("singleplayer")) {
    		return narrow_search2( mainCategory, "tag%5B%5D=4182&" ); 
    	}
      else if( category.equals("strategy") ) {
    		return narrow_search2( mainCategory, "tag%5B%5D=9&" ); 
    	}
      else if( category.equals("violent") ) {
    		return narrow_search2( mainCategory, "tag%5B%5D=4667&" ); 
    	}
      else if( category.equals("gore") ) {
    		return narrow_search2( mainCategory, "tag%5B%5D=4345&" ); 
    	}
      else if( category.equals("multiplayer") ) {
    		return narrow_search2( mainCategory, "tag%5B%5D=3859&" ); 
    	}
      else if( category.equals("massively multiplayer") ) {
    		return narrow_search2( mainCategory, "tag%5B%5D=128&" ); 
    	}
      else if( category.equals("simulation") ) {
    		return narrow_search2( mainCategory, "tag%5B%5D=599&" ); 
    	}
      else if( category.equals("shooter") ) {
    		return narrow_search2( mainCategory, "tag%5B%5D=1774&" ); 
    	}
      else if( category.equals("great soundtrack") ) {
    		return narrow_search2( mainCategory, "tag%5B%5D=1756&" ); 
    	}
    	else {
    		return "CategoryNotFound";
    	}
    }

    private static String narrow_search2( String mainCategory, String category ) {
    	try {
    		Document doc = Jsoup.connect("http://store.steampowered.com/tag/en/" + mainCategory + "/#" + category + "p=0&tab=PopularNewReleases").get();
    		String name = doc.select("div.tab_item_name").first().text();

    		return name;
    	}
    	catch (IOException e) {
    		return "narrow error";
    	}
    }

    public static String TopTen5DollarGames (  ) {
    	try {
    		Document doc = Jsoup.connect("http://store.steampowered.com/search/?filter=ut2").get();
        	String name = doc.select("span.title").first().text();
        	return name;
    	}
    	catch (IOException e) {
    		return "top 5 dollar games error";
    	}
        
    }
    
    public static String TopTen10DollarGames (  ) {
    	try {
    		Document doc = Jsoup.connect("http://store.steampowered.com/search/?filter=ut1").get();
        	String name = doc.select("span.title").first().text();
        	return name;
    	}
    	catch (IOException e) {
    		return "top 10 dollar games error";
    	}
        
    }


}
