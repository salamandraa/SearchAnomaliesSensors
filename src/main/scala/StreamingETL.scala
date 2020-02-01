import data.LightSensorData
import io.KafkaIO
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.streaming.OutputMode

object StreamingETL extends App with SparkDebug {

  val kafkaInput = KafkaIO(Config.SourceKafka.sourceBrokers, Config.SourceKafka.sourceTopic)
  val kafkaOutputIncidents = KafkaIO(Config.OutputKafka.anomalyBrokers, Config.OutputKafka.anomalyTopic)

  def transform(read: Dataset[LightSensorData]) = {

  }

  import spark.implicits._

  val read = kafkaInput.readStream.as[LightSensorData]
  val transformData = read

  transformData.
    writeStream.
    outputMode(OutputMode.Append()).
    foreachBatch { (ds, _) =>
      kafkaOutputIncidents.writeBatch(ds.toJSON)
      if (mode.isDebug) ds.show(false)
    }.start().awaitTermination()

}
