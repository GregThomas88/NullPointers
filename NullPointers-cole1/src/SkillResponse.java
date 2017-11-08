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
          return respond_with("Hello!");

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

        else if ("AssistanceIntent".equals(intentName))
          return respond_with("You can search for a game price, or you can find the top ten dollar game, the top five dollar game, or you can search by category.");

        else if ("AMAZON.HelpIntent".equals(intentName))
          return respond_with("Ask me how much a game costs");

        else if ("AMAZON.StopIntent".equals(intentName))
            return respond_with("bye!");

        else if ("AMAZON.CancelIntent".equals(intentName))
            return respond_with("bye.");

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

    private SpeechletResponse steam_search(Intent intent) {
      String game_name = intent.getSlot("GameName").getValue();
      String price = SteamAPI.search(game_name);
      String text = String.format("On Steam, %s costs %s.",game_name,price);
      return respond_with(text);
    }

    private SpeechletResponse search_category(Intent intent) {
      String category_name = intent.getSlot("CategoryName").getValue();
      categoryN = category_name;
      String game = SteamAPI.search_category(category_name);
      if( game.equals("CategoryNotFound") ) {
        String text = String.format("I'm sorry, I could not find a %s category", category_name);
          return respond_with(text);
      }
      else {
        String text = String.format("The top %s game is %s.",category_name,game);
        return respond_with(text);
      }
      
    }

    private SpeechletResponse narrow_search(Intent intent) {
      String category_name = intent.getSlot("category").getValue();
      String game = SteamAPI.narrow_search(categoryN, category_name);

      String text = String.format("The top %s and %s game is %s.",categoryN, category_name,game);
      return respond_with(text);
    }
    
    private SpeechletResponse TopTen5DollarGames() {
        String game = SteamAPI.TopTen5DollarGames();
        String text = String.format("The top 5 dollar game is %s.",game);
        return respond_with(text);
    }
    
    private SpeechletResponse TopTen10DollarGames() {
        String game = SteamAPI.TopTen10DollarGames();
        String text = String.format("The top 10 dollar game is %s.",game);
        return respond_with(text);
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
