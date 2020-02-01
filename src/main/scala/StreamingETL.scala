import anomalies.{DetectorSimpleAnomaly, VoltageSimpleAnomaly}
import data.LightSensorData
import io.KafkaIO
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.streaming.OutputMode

object StreamingETL extends App with SparkDebug {

  val kafkaInput = KafkaIO(Config.SourceKafka.sourceBrokers, Config.SourceKafka.sourceTopic)
  val kafkaOutputIncidents = KafkaIO(Config.OutputKafka.anomalyBrokers, Config.OutputKafka.anomalyTopic)

  import spark.implicits._

  def transform(read: Dataset[LightSensorData]) = {
    val detectors: List[DetectorSimpleAnomaly[LightSensorData]] = List(VoltageSimpleAnomaly)

    // todo nothing do with duplication anomaly
    detectors.map(detector => read.filter(x => detector.isAnomaly(x)).map(x => detector.toAnomaly(x))).reduce(_.union(_))
  }


  val read = kafkaInput.readStream.as[LightSensorData]
  val transformData = transform(read)

  transformData.
    writeStream.
    outputMode(OutputMode.Append()).
    foreachBatch { (ds, _) =>
      kafkaOutputIncidents.writeBatch(ds.toJSON)
      if (mode.isDebug) ds.show(false)
    }.
    start().
    awaitTermination()

}
