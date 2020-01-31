import data.LightSensorData
import io.KafkaIO

object ReadDataKafka extends App with Spark {

  import spark.implicits._

  val kafkaInput = KafkaIO(Config.sourceBrokers, Config.sourceTopic)

  kafkaInput.readBatch.show(false)

}
