package steamget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class SkillResponse implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(SkillResponse.class);

    public static String categoryN;
    public static String categoryN2;
    public static String lastFunc = "";
    public static int i = 0;
    public static String glob_game_name;

    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any initialization logic goes here
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("HelloWorldIntent".equals(intentName))
          return ask_user("Hello!");

        else if ("CategorySearchIntent".equals(intentName))
          return search_category(intent);
                
        else if ("TopFiveFiveIntent".equals(intentName))
            return TopTen5DollarGames();
        
        else if ("TopTenTenIntent".equals(intentName))
            return TopTen10DollarGames();

        else if ("SteamSearchIntent".equals(intentName))
          return steam_search(intent);

        else if ("NarrowSearchIntent".equals(intentName))
          return narrow_search(intent);

        else if ("OpenSearch".equals(intentName) ) 
          return ask_user("Alright, what is the name of the game");

        else if ("OpenCategorySearch".equals(intentName) ) 
          return ask_user("Alright, what category do you want to look at");
    
        else if ("AssistanceIntent".equals(intentName))
          return ask_user("You can search for a game price, or you can find the top ten dollar game, the top five dollar game, or you can search by category.");

        else if ("AMAZON.NextIntent".equals(intentName))
            return next();

        else if ("AMAZON.PreviousIntent".equals(intentName))
          return previous();

        else if ("AMAZON.HelpIntent".equals(intentName))
          return ask_user("Ask me how much a game costs");

        else if ("AMAZON.StopIntent".equals(intentName))
            return respond_with("good bye!");

        else if ("AMAZON.CancelIntent".equals(intentName))
            return ask_user("okay");

        else
            throw new SpeechletException("Invalid Intent");
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any cleanup logic goes here
    }

    private SpeechletResponse respond_with(String outputText){
      PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
      outputSpeech.setText(outputText);
      return SpeechletResponse.newTellResponse(outputSpeech);
    }

    private SpeechletResponse ask_user(String outputText){
      PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
      outputSpeech.setText(outputText);
      Reprompt reprompt  = new Reprompt();
      return SpeechletResponse.newAskResponse(outputSpeech,reprompt);
    }

    private SpeechletResponse steam_search(Intent intent) {

      i = 0;
      lastFunc = "priceSearch";


      String game_name = intent.getSlot("GameName").getValue();
      glob_game_name = game_name;

      String real_game_name = SteamAPI.getName(game_name, i);

      String price = SteamAPI.search(game_name, i);



      if( price.equals("GameNotFound") ) {
        String text = String.format("Game not found");
        return ask_user(text);
      }
      else {
        String text = String.format("On Steam, %s costs %s. What would you like to do now", real_game_name,price);
        return ask_user(text);
      }

      
    }

    private SpeechletResponse search_category(Intent intent) {
      String category_name = intent.getSlot("CategoryName").getValue();
      categoryN = category_name;
      lastFunc = "category";
      i = 0;
      String game = SteamAPI.search_category(category_name, i);
      if( game.equals("CategoryNotFound") ) {
        String text = String.format("I'm sorry, I could not find %s category. What would you like to do now", category_name);
          return ask_user(text);
      }
      else {
        String text = String.format("The top %s game is %s. What would you like to do now",category_name,game);
        return ask_user(text);
      }
      
    }

    private SpeechletResponse narrow_search(Intent intent) {

      i = 0;
      lastFunc = "NarrowSearch";

      String category_name = intent.getSlot("category").getValue();

      categoryN2 = category_name;

      String game = SteamAPI.narrow_search(categoryN, category_name, i);

      if( game.equals("CategoryNotFound") ) {
        String text = String.format("I'm sorry, I could not find a game for %s and %s categories. What would you like to do now", categoryN, category_name);
        return ask_user(text);
      }
      else {
        String text = String.format("The top %s and %s game is %s. What would you like to do now",categoryN, category_name,game);
        return ask_user(text);
      }
      
    }

    private SpeechletResponse next() {

      i += 1;

      if( lastFunc == "category" ) {
        String game = SteamAPI.search_category(categoryN, i);
        String text = String.format("The next game is %s", game);
        return ask_user(text);
      }
      else if( lastFunc == "Top5Dollar") {
        String game = SteamAPI.TopTen5DollarGames(i);
        String text = String.format("The top 5 dollar game is %s. What would you like to do now",game);
        return ask_user(text);
      }
      else if ( lastFunc == "Top10Dollar") {
        String game = SteamAPI.TopTen10DollarGames(i);
        String text = String.format("The top 10 dollar game is %s. What would you like to do now",game);
        return ask_user(text);
      }
      else if ( lastFunc == "priceSearch") {
        String real_game_name = SteamAPI.getName(glob_game_name, i);
        String price = SteamAPI.search(glob_game_name, i);
        String text = String.format("On Steam, %s costs %s. What would you like to do now", real_game_name,price);
        return ask_user(text);
      }
      else if( lastFunc == "narrow_search") {
        String game = SteamAPI.narrow_search(categoryN, categoryN2, i);
        String text = String.format("The top %s and %s game is %s. What would you like to do now",categoryN, categoryN2,game);
        return ask_user(text);
      }
      else {
        return ask_user("Sorry, you have not specified a search yet");
      }
      



    }

    private SpeechletResponse previous() {
      
      if( i == 0 ) {
        return ask_user("Cannot find previous game");
      }

      i -= 1;

      
      if( lastFunc == "category" ) {
        String game = SteamAPI.search_category(categoryN, i);
        String text = String.format("The next game is %s", game);
        return ask_user(text);
      }
      else if( lastFunc == "Top5Dollar") {
        String game = SteamAPI.TopTen5DollarGames(i);
        String text = String.format("The top 5 dollar game is %s. What would you like to do now",game);
        return ask_user(text);
      }
      else if ( lastFunc == "Top10Dollar") {
        String game = SteamAPI.TopTen10DollarGames(i);
        String text = String.format("The top 10 dollar game is %s. What would you like to do now",game);
        return ask_user(text);
      }
      else if ( lastFunc == "priceSearch") {
        String real_game_name = SteamAPI.getName(glob_game_name, i);
        String price = SteamAPI.search(glob_game_name, i);
        String text = String.format("On Steam, %s costs %s. What would you like to do now", real_game_name,price);
        return ask_user(text);
      }
      else if( lastFunc == "narrow_search") {
        String game = SteamAPI.narrow_search(categoryN, categoryN2, i);
        String text = String.format("The top %s and %s game is %s. What would you like to do now",categoryN, categoryN2,game);
        return ask_user(text);
      }
      else {
        return ask_user("Sorry, you have not specified a search yet");
      }



    }
      
    
    
    private SpeechletResponse TopTen5DollarGames() {
      i = 0;
      lastFunc = "Top5Dollar";
      String game = SteamAPI.TopTen5DollarGames(i);
      String text = String.format("The top 5 dollar game is %s. What would you like to do now",game);
      return ask_user(text);
    }
    
    private SpeechletResponse TopTen10DollarGames() {
      i = 0;
      lastFunc = "Top10Dollar";
      String game = SteamAPI.TopTen10DollarGames(i);
      String text = String.format("The top 10 dollar game is %s. What would you like to do now",game);
      return ask_user(text);
    }
    
    
    /**
     * Creates and returns a {@code SpeechletResponse} with a welcome message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWelcomeResponse() {
        String speechText = "Welcome to the steam store, what would you like to do? If you are unsure what to say, ask for help.";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Welcome!");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    /**
     * Creates a {@code SpeechletResponse} for the hello intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getGeneralResponse() {
        String speechText = "Okay";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("General Response:");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    /**
     * Creates a {@code SpeechletResponse} for the help intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getHelpResponse() {
        String speechText = "Ask me how much a game costs";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Help Response:");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }
}
