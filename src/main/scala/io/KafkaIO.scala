package io

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.sql.functions.{from_json, col}

case class KafkaIO(bootstrapServers: String, topic: String, limitCountToInferSchema: Int = 1000) {

  def readStream(implicit spark: SparkSession): DataFrame = {
    import spark.implicits._
    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", bootstrapServers)
      .option("subscribe", topic)
      .load()
    val ds = df.selectExpr("CAST(value AS STRING)").as[String]

    val schema = readBatch.limit(limitCountToInferSchema).schema

    ds.select(from_json(col("value"), schema).as("data")).select("data.*")
  }

  def readBatch(implicit spark: SparkSession): DataFrame = {
    import spark.implicits._
    val df = spark.read
      .format("kafka")
      .option("kafka.bootstrap.servers", bootstrapServers)
      .option("subscribe", topic)
      .option("startingOffsets", "earliest")
      .load()
    val ds = df.selectExpr("CAST(value AS STRING)")
      .as[String]

    spark.read.json(ds)
  }

  def writeBatch(ds: Dataset[String]): Unit = {
    ds.write
      .format("kafka")
      .option("kafka.bootstrap.servers", bootstrapServers)
      .option("topic", topic)
      .save()
  }


}
