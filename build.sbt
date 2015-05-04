name := "factorymaker-performance"

organization := "me.frmr.lift"

scalaVersion := "2.11.4"

libraryDependencies += "net.liftweb" %% "lift-util" % "3.0-M5-1"

libraryDependencies += "net.liftweb" %% "lift-webkit" % "3.0-M5-1"

mainClass in Compile := Some("me.frmr.lift.PerformanceTester")
