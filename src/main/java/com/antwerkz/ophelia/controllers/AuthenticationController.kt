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

import java.io.IOException
import java.io.InputStream
import java.util.Properties

import org.bson.types.ObjectId
import twitter4j.TwitterException

public class AuthenticationController {

    {
        try {
            val props = Properties()
            val stream = javaClass<AuthenticationController>().getResourceAsStream("/oauth.conf")
            if (stream != null) {
                try {
                    props.load(stream)
                } finally {
                    stream.close()
                }
            }
            twitterKey = props.getProperty("twitter.key")
            twitterSecret = props.getProperty("twitter.secret")
        } catch (e: IOException) {
            throw RuntimeException(e.getMessage(), e)
        }

    }

    class object {
        private val CONTEXT_NAME = "-context"

        var twitterKey: String

        var twitterSecret: String

        public fun oauth() {
            val twitterContext = getTwitterContext()
            if (twitterContext == null || twitterContext.screenName == null) {
                val context = TwitterContext()
                //Cache.set(session().get(Application.SESSION_KEY) + CONTEXT_NAME, context);
                //                RequestToken requestToken = context.twitter.getOAuthRequestToken(request().uri() + "/callback");
                //                redirect(requestToken.getAuthenticationURL());
            }
        }

        public fun login() {
            //        Application.index();
        }

        public fun callback(oauth_token: String, oauth_verifier: String) {
            try {
                getTwitterContext()!!.authenticate(oauth_token, oauth_verifier)
                //            Application.index();
            } catch (e: TwitterException) {
                System.out.println("e = " + e)
                throw RuntimeException(e.getMessage(), e)
            }

        }

        public fun initUsers(name: String) {
            /*
    if (!User.find().initialized()) {
      new User(name, true).save();
    }
*/
        }

        public fun addUser(twitter: String) {
            /*
    if (!twitter.isEmpty()) {
      new User(twitter, false).save();
    }
*/
        }

        public fun deleteUser(id: ObjectId) {
            /*
    User user = User.find().byId(id);
    if (user != null) {
      user.delete();
    }
*/
        }

        public fun getTwitterContext(): TwitterContext? {
            return null //(TwitterContext) Cache.get(session().get(Application.SESSION_KEY) + CONTEXT_NAME);
        }
    }
}