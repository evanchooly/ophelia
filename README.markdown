Ophelia
=======

Ophelia is a web based mongo query viewer built on the [playframework](http://playframework.org).  When run on
a machine, you can connect to the local mongo instance and run queries over any collection you'd like.
Currently, it only works for local instances of mongo but that will change soon.  Hopefully.

How to run it
======

1. One option is to download the latest distribution from [here](https://github.com/evanchooly/ophelia/downloads).  Extract the bundle wherever you'd like.
1. Another is to simply clone the repository using the url above.  You can find specific release tags [here](https://github.com/evanchooly/ophelia/tags).
1. Make sure the play framework 1.2.x is [installed](http://www.playframework.org/documentation/1.2.4/install) and that
   you are running java 7.  Play supports war distributions but I'm currently having issues with the production mode
   that entails.  The problems aren't insurmountable but I want to get a release or two or three out so I can get some
   feature/functionality feedback before tackling different deployment issues.  Running it via play is simple enough
   that I'm willing to let it go for now to focus on more interesting features for now
1. You can then run the app from the root of the extracted bundle using with `play run` in the foreground or
   `play start` to background the app.  If you opt for the second option, you can stop it with `play stop` in the root
    directory.

There are several interesting bits I have in mind but I'm ready for feedback.  If you find any bugs or have feature
requests, please [file](https://github.com/evanchooly/ophelia/issues) an issue.
