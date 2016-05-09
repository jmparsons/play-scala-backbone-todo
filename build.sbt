name := """play-scala-backbone-todo"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  ws,
  evolutions,
  specs2 % Test,
  "org.webjars.bower" % "jquery" % "2.2.3",
  "org.webjars.bower" % "requirejs" % "2.2.0",
  "org.webjars.bower" % "backbone" % "1.3.3",
  "org.webjars.bower" % "underscore" % "1.8.3",
  "org.webjars.bower" % "dustjs-linkedin" % "2.7.2",
  "org.webjars.bower" % "dustjs-helpers" % "1.7.3",
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "com.h2database" % "h2" % "1.4.191",
  "org.seleniumhq.selenium" % "selenium-firefox-driver" % "2.53.0"
)

JsEngineKeys.engineType := JsEngineKeys.EngineType.Node

routesGenerator := InjectedRoutesGenerator

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

DustJsKeys.helpers in Assets := true

DustJsKeys.infoNotice in Assets := true

includeFilter in (Assets, LessKeys.less) := "main.less"

excludeFilter in (Assets, LessKeys.less) := "_*.less"

pipelineStages := Seq(rjs, digest)
