package steamget;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public final class RequestHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds = new HashSet<String>();
    static {
        supportedApplicationIds.add("amzn1.ask.skill.47261913-89ad-4e31-a2a7-943c7990eff8");
    }
    public RequestHandler() {
        super(new SkillResponse(), supportedApplicationIds);
    }
}
