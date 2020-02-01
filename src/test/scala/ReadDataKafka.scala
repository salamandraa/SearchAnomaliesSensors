import data.LightSensorData
import io.KafkaIO

object ReadDataKafka extends App with SparkDebug {

  import spark.implicits._

  val kafkaInput = KafkaIO(Config.SourceKafka.sourceBrokers, Config.SourceKafka.sourceTopic)

  if (mode.isDebug) {
    kafkaInput.readBatch.printSchema()
  }

  kafkaInput.readBatch.as[LightSensorData].show(false)


}