package io

import org.apache.spark.sql.{Dataset, SparkSession}

case class KafkaIO(bootstrapServers: String, topic: String) {

  def readStream(implicit spark: SparkSession): Dataset[String] = {
    import spark.implicits._
    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", bootstrapServers)
      .option("subscribe", topic)
      .load()
    df.selectExpr("CAST(value AS STRING)").as[String]
  }

  def readBatch(implicit spark: SparkSession): Dataset[String] = {
    import spark.implicits._
    val df = spark.read
      .format("kafka")
      .option("kafka.bootstrap.servers", bootstrapServers)
      .option("subscribe", topic)
      .option("startingOffsets", "earliest")
      .load()
    df.selectExpr("CAST(value AS STRING)")
      .as[String]
  }

  def writeBatch(ds: Dataset[String]): Unit = {
    ds.write
      .format("kafka")
      .option("kafka.bootstrap.servers", bootstrapServers)
      .option("topic", topic)
      .save()
  }


}
