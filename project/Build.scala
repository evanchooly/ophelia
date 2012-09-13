import sbt._
import PlayProject._

object ApplicationBuild extends Build {

  val appName = "ophelia"
  val appVersion = "0.5"

  val appDependencies = Seq(
    "com.antwerkz.sofia" % "sofia-play" % "0.11",
    "org.mongodb" % "mongo-java-driver" % "2.7.3" withSources(),
    "org.jongo" % "jongo" % "0.2" withSources()
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    // Add your own project settings here
    ebeanEnabled := false
  )

}
