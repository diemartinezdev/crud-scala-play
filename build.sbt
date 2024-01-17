name := """invoices"""
organization := ""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
  "org.postgresql" % "postgresql" % "42.3.1",
  "com.github.tminglei" %% "slick-pg" % "0.20.2",
  "com.github.tminglei" %% "slick-pg_play-json" % "0.20.2"
)