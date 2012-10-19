package controllers;

import be.objectify.deadbolt.actions.Restrict;
import models.User;
import org.bson.types.ObjectId;
import play.cache.Cache;
import play.mvc.Controller;
import play.mvc.Result;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AuthenticationController extends Controller {
    private static final String CONTEXT_NAME = "-context";
    static String twitterKey;
    static String twitterSecret;

    public AuthenticationController() {
        try {
            Properties props = new Properties();
            InputStream stream = AuthenticationController.class.getResourceAsStream("/oauth.conf");
            if (stream != null) {
                try {
                    props.load(stream);
                } finally {
                    stream.close();
                }
            }

            twitterKey = props.getProperty("twitter.key");
            twitterSecret = props.getProperty("twitter.secret");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void oauth() {
        try {
            TwitterContext twitterContext = getTwitterContext();
            if (twitterContext == null || twitterContext.screenName == null) {
                TwitterContext context = new TwitterContext();
                Cache.set(session().get(Application.SESSION_KEY) + CONTEXT_NAME, context);
                RequestToken requestToken = context.twitter.getOAuthRequestToken(request().uri() + "/callback");
                redirect(requestToken.getAuthenticationURL());
            }
        } catch (TwitterException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void login() {
        Application.index();
    }

    public static void callback(String oauth_token, String oauth_verifier) {
        try {
            getTwitterContext().authenticate(oauth_token, oauth_verifier);
            Application.index();
        } catch (TwitterException e) {
            System.out.println("e = " + e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Result initUsers(String name) {
        if (!User.find().initialized()) {
            new User(name, true).save();
        }
        return Application.index();
    }

    @Restrict(Application.ADMIN)
    public static Result addUser(String twitter) {
        if (!twitter.isEmpty()) {
            new User(twitter, false).save();
        }
        return Application.index();
    }

    @Restrict(Application.ADMIN)
    public static Result deleteUser(ObjectId id) {
        User user = User.find().byId(id);
        if (user != null) {
            user.delete();
        }
        return Application.index();
    }

    public static TwitterContext getTwitterContext() {
        return (TwitterContext) Cache.get(session().get(Application.SESSION_KEY) + CONTEXT_NAME);
    }
}