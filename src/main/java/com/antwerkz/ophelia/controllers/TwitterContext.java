package com.antwerkz.ophelia.controllers;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.Serializable;

public class TwitterContext implements Serializable {
    public String screenName;
    final Twitter twitter;

    public TwitterContext() {
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(AuthenticationController.twitterKey, AuthenticationController.twitterSecret);
    }

    public void authenticate(String oauth_token, String oauth_verifier) throws TwitterException {
        AccessToken token = twitter.getOAuthAccessToken(new RequestToken(oauth_token, oauth_verifier));
        screenName = token.getScreenName();
    }
}
