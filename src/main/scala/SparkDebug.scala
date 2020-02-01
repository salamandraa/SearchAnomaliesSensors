import helper.Mode
import org.apache.spark.sql.SparkSession

trait SparkDebug {

  implicit val spark: SparkSession = SparkSession
    .builder().master("local[*]")
    .appName("SparkSession")
    .getOrCreate()

  implicit val mode: Mode = Mode.isDebug

  mode.start
}
