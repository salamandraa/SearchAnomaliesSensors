
name := "SearchAnomaliesSensors"

version := "0.1"

scalaVersion := "2.11.12"


val sparkVersion = "2.4.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-hive" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion % Provided,
  "org.scalatest" % "scalatest_2.11" % "3.0.4" % Test
)