ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "scala-sbt-maven-example"
  )

// Add library dependencies from the Maven repository
// The '%%' tells SBT to automatically use the correct version for our project's Scala version.
libraryDependencies ++= Seq(
  "com.lihaoyi" %% "requests" % "0.8.0",
  "com.github.kevinsawicki" % "http-request" % "6.0",
  "com.lihaoyi" %% "upickle" % "3.1.3",
  "log4j" % "log4j" % "1.2.17"
)
