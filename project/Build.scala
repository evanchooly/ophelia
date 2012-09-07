import sbt._
import PlayProject._

object ApplicationBuild extends Build {

  val appName = "ophelia"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    "com.antwerkz.sofia" % "sofia-play" % "0.11",
//    "com.fasterxml.jackson.core" % "jackson-databind" % "2.0.5",
//    "com.google.code.gson" % "gson" % "1.7.1",
    "org.mongodb" % "mongo-java-driver" % "2.7.3"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    // Add your own project settings here
  )

}
