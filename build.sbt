scalaVersion := "2.12.3"

crossScalaVersions := Seq("2.11.11", "2.12.3")

name := "play-json-refined"

version := "0.0.2"

organization := "beyondthelines"

resolvers += Resolver.bintrayRepo("beyondthelines", "maven")

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json"  % "2.6.6",
  "eu.timepit"        %% "refined"    % "0.8.4",
  "org.scalatest"     %% "scalatest"  % "3.0.4"  % "test",
  "org.scalacheck"    %% "scalacheck" % "1.13.4" % "test"
)

licenses := ("MIT", url("http://opensource.org/licenses/MIT")) :: Nil

bintrayOrganization := Some("beyondthelines")

bintrayPackageLabels := Seq("scala", "json", "play", "refined")
