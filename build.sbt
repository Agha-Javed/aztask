name := """aztask"""


version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.apache.commons" % "commons-pool2" % "2.3",
  "org.mybatis" % "mybatis" % "3.3.0"
)
