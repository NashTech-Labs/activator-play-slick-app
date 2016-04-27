name := """reactive-web-app"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  cache,
  ws,
  "com.typesafe.play"   %%   "play-slick"              %   "1.1.1",
  "com.typesafe.play"   %%   "play-slick-evolutions"   %   "1.1.1",
  "com.h2database"    % 	   "h2"                    %   "1.4.187" ,
  "org.postgresql"   %      "postgresql" % "9.4-1206-jdbc4",
  "org.json4s"      %%     "json4s-native" %  "3.3.0",
  specs2 % Test

)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


routesGenerator := InjectedRoutesGenerator
