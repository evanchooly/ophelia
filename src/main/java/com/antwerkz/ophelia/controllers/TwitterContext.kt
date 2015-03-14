/**
 * Copyright (C) 2012-2014 Justin Lee <jlee@antwerkz.com>
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
package com.antwerkz.ophelia.controllers

import java.io.Serializable

import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

public class TwitterContext : Serializable {
    public var screenName: String

    val twitter: Twitter

    {
        twitter = TwitterFactory().getInstance()
        twitter.setOAuthConsumer(AuthenticationController.twitterKey, AuthenticationController.twitterSecret)
    }

    throws(javaClass<TwitterException>())
    public fun authenticate(oauth_token: String, oauth_verifier: String) {
        val token = twitter.getOAuthAccessToken(RequestToken(oauth_token, oauth_verifier))
        screenName = token.getScreenName()
    }
}
