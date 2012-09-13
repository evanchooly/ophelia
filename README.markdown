Ophelia
=======

Ophelia is a web based mongo query viewer built on the [playframework](http://playframework.org).  When run on
a machine, you can connect to the local mongo instance and run queries over any collection you'd like.
Currently, it only works for local instances of mongo but that will change soon.  Hopefully.

How to run it
======

1. One option is to download the latest distribution from [here](https://github.com/evanchooly/ophelia/downloads).
   Extract the bundle wherever you'd like.  The distribution is self-contained and can be run with the "start" script
   in the root the zip file.  This assumes some flavor of UNIX to run the app.
1. Another is to simply clone the repository using the url above.  You should be familiar with how the play framework
   works before trying this approach.  You can find specific release tags [here](https://github.com/evanchooly/ophelia/tags).
   Play supports war distributions but I'm currently having issues with the production mode
   that entails.  The problems aren't insurmountable but I want to get a release or two or three out so I can get some
   feature/functionality feedback before tackling different deployment issues.  Running it via play is simple enough
   that I'm willing to let it go for now to focus on more interesting features for now

Ophelia requires Java 7 and is built on play! 2 [framework](http://www.playframework.org/documentation/2.0.3/Installing).
There are several interesting bits I have in mind but I'm ready for feedback.  If you find any bugs or have feature
requests, please [file](https://github.com/evanchooly/ophelia/issues) an issue.