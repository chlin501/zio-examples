scalaVersion := "2.13.2"

libraryDependencies ++= Seq (
  "io.github.neurodyne" %% "zio-arrow" % "0.2.1",
  "dev.zio" %% "zio" % "1.0.0-RC21-2",
  "dev.zio" %% "zio-streams" % "1.0.0-RC18-2",
  "org.slf4j" % "slf4j-api" % "1.7.30",
  "org.slf4j" % "slf4j-simple" % "1.7.30",
  "org.scalatest" %% "scalatest" % "3.1.1" % "test" 
)
