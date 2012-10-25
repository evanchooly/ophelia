package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import models.User;
import org.bson.types.ObjectId;
import twitter4j.TwitterException;

public class AuthenticationController {
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
        TwitterContext twitterContext = getTwitterContext();
        if (twitterContext == null || twitterContext.screenName == null) {
            TwitterContext context = new TwitterContext();
            //Cache.set(session().get(Application.SESSION_KEY) + CONTEXT_NAME, context);
//                RequestToken requestToken = context.twitter.getOAuthRequestToken(request().uri() + "/callback");
//                redirect(requestToken.getAuthenticationURL());
        }
    }

    public static void login() {
//        Application.index();
    }

    public static void callback(String oauth_token, String oauth_verifier) {
        try {
            getTwitterContext().authenticate(oauth_token, oauth_verifier);
//            Application.index();
        } catch (TwitterException e) {
            System.out.println("e = " + e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void initUsers(String name) {
        if (!User.find().initialized()) {
            new User(name, true).save();
        }
    }

    public static void addUser(String twitter) {
        if (!twitter.isEmpty()) {
            new User(twitter, false).save();
        }
    }

    public static void deleteUser(ObjectId id) {
        User user = User.find().byId(id);
        if (user != null) {
            user.delete();
        }
    }

    public static TwitterContext getTwitterContext() {
        return null; //(TwitterContext) Cache.get(session().get(Application.SESSION_KEY) + CONTEXT_NAME);
    }
}