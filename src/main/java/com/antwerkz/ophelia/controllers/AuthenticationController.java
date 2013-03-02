/**
 * Copyright (C) 2012-2013 Justin Lee <jlee@antwerkz.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.antwerkz.ophelia.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.antwerkz.ophelia.models.User;
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