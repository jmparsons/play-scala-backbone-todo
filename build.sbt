name := """play-scala-backbone-todo"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  ws,
  "org.webjars" % "jquery" % "2.1.1",
  "org.webjars" % "requirejs" % "2.1.14-1",
  "org.webjars" % "backbonejs" % "1.1.2-2",
  "org.webjars" % "underscorejs" % "1.6.0-3",
  "org.webjars" % "dustjs-linkedin" % "2.4.0-1",
  "com.typesafe.play" %% "play-slick" % "0.8.0-M1",
  "org.seleniumhq.selenium" % "selenium-firefox-driver" % "2.34.0"
)

includeFilter in (Assets, LessKeys.less) := "main.less"

excludeFilter in (Assets, LessKeys.less) := "_*.less"

pipelineStages := Seq(rjs, digest, gzip)
