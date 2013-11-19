name := "play-scala-backbone-todo"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "com.typesafe.play" %% "play-slick" % "0.5.0.8",
  "org.seleniumhq.selenium" % "selenium-firefox-driver" % "2.34.0"
)

play.Project.playScalaSettings

requireJsShim += "shim.js"

requireJs += "main.js"