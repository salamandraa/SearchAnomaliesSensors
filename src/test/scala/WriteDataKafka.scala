import data.LightSensorData
import io.KafkaIO

object WriteDataKafka extends App with SparkDebug {

  import spark.implicits._

  val testData = List(
    LightSensorData(1, 5),
    LightSensorData(2, 6),
    LightSensorData(3, 5.1),
    LightSensorData(4, 4.4))

  val kafkaOutput = KafkaIO(Config.SourceKafka.sourceBrokers, Config.SourceKafka.sourceTopic)

  kafkaOutput.writeBatch(testData.toDS().toJSON)

}
