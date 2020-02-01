
name := "SearchAnomaliesSensors"

version := "0.1"

scalaVersion := "2.11.12"


val sparkVersion = "2.4.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,
  "org.scalatest" % "scalatest_2.11" % "3.0.4" % Test
)