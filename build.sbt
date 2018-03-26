
name := "lagom-assignment2"

version := "0.1"

scalaVersion := "2.12.5"


organization in ThisBuild := "com.knoldus"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"
lazy val user = lagomExternalScaladslProject("user-lagom", "com.knoldus" %% "user-lagom-impl" % "1.0-SNAPSHOT")
val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

lazy val `app-lagom` = (project in file("."))
  .aggregate(`app-lagom-api`, `app-lagom-impl`)

lazy val `app-lagom-api` = (project in file("app-lagom-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `app-lagom-impl` = (project in file("app-lagom-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      "com.knoldus" %% "user-lagom-api" % "1.0-SNAPSHOT",
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`app-lagom-api`)