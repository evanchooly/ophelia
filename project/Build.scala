import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName = "ophelia"
  val appVersion = "0.5"

  val appDependencies = Seq(
    "com.antwerkz.sofia" % "sofia-play" % "0.12",
    "org.mongodb" % "mongo-java-driver" % "2.9.1" withSources(),
    "be.objectify" %% "deadbolt-2" % "1.1.2",
    "org.jongo" % "jongo" % "0.2" withSources(),
    "org.twitter4j" % "twitter4j-core" % "2.2.5" withSources(),
    "com.google.code.morphia" % "morphia" % "0.99" withSources(),
    "com.google.code.morphia" % "morphia-logging-slf4j" % "0.99" withSources()

    /*
    "com.google.code" % "morphia" % "0.91" withSources()
    */
  )

  /*
    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("Objectify Play Snapshot Repository", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns)
    )
  */

  val main2 = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
    resolvers += Resolver.url("Objectify Play Snapshot Repository", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns),
    resolvers += "Morphia repository" at "http://morphia.googlecode.com/svn/mavenrepo/",
    resolvers += Resolver.file("Local Repository", file(Path.userHome + "/.m2/repository"))(Resolver.mavenStylePatterns),
    resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
    templatesImport += "controllers.QueryResults",
    templatesImport += "utils.Sofia"
  )
}