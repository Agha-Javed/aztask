name := """aztask"""


version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  cache,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.mybatis" % "mybatis" % "3.3.0",
  "com.google.inject" % "guice-parent" % "4.0",
  "org.jsoup" % "jsoup" % "1.8.3"
)