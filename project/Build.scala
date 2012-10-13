import sbt._
import PlayProject._

object ApplicationBuild extends Build {

  val appName = "ophelia"
  val appVersion = "0.5"

  val appDependencies = Seq(
//    "com.antwerkz.sofia" % "sofia-play" % "0.11",
    "org.mongodb" % "mongo-java-driver" % "2.9.1" withSources(),
    "org.jongo" % "jongo" % "0.2" withSources()/*,
    "com.google.code" % "morphia" % "0.91" withSources()*/
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(defaultJavaSettings: _*).settings(
//     resolvers += "Local Repository" at file(Path.userHome + "/.m2/repository")(Resolver.mavenStylePatterns)
//     resolvers += Resolver.file("Local Repository", file(Path.userHome + "/.m2/repository"))(Resolver.mavenStylePatterns)
    templatesImport += "controllers.QueryResults",
    templatesImport += "utils.Sofia"
  )
}