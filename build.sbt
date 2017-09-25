organization := "com.zenon"
name := "pricetime"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.12.2"

val Http4sVersion = "0.15.11a"

libraryDependencies ++= Seq(
  "org.http4s"     %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s"     %% "http4s-circe"        % Http4sVersion,
  "org.http4s"     %% "http4s-dsl"          % Http4sVersion,
  "org.http4s"     %% "http4s-scala-xml"    % Http4sVersion,
  "org.http4s"     %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s"     %% "http4s-blaze-client" % Http4sVersion,
  "org.http4s"     %% "http4s-server-metrics"   % Http4sVersion,
  "ch.qos.logback" %  "logback-classic"     % "1.2.1",
  "org.json4s"     %% "json4s-native"       % "3.5.3",
  "org.scalactic"  %% "scalactic"           % "3.0.1",
  "org.scalatest"  %% "scalatest"           % "3.0.1" % "test",
  "org.mock-server" % "mockserver-netty" % "3.9.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % Test
)
