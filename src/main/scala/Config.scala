object Config {


  object SourceKafka {
    val sourceTopic: String = "sensors.data.raw"
    val sourceBrokers: String = "localhost:9092"
  }

  object OutputKafka {
    val anomalyTopic: String = "sensors.anomaly"
    val anomalyBrokers: String = "localhost:9092"
  }

}
