import org.apache.spark.sql.SparkSession

trait Spark {

  implicit val spark: SparkSession = SparkSession
    .builder().master("local[*]")
    .appName("SparkSession")
    .getOrCreate()
}
