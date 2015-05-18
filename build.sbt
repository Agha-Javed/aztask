name := """aztask"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "org.hibernate" % "hibernate-entitymanager" % "4.3.8.Final",
  "mysql" % "mysql-connector-java" % "5.1.18",
  "redis.clients" % "jedis" % "2.7.2",
  "org.apache.commons" % "commons-pool2" % "2.3"
)
