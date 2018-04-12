name := "CurrencyConverter"

version := "0.1"

scalaVersion := "2.12.5"

val akkaHttpVersion = "10.1.0"

libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.0"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.11"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.11"
libraryDependencies += "io.spray" %%  "spray-json" % "1.3.3"
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.5"
libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.3.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5"