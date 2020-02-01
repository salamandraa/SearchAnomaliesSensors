package helper

import org.apache.spark.sql.SparkSession

sealed abstract case class Mode(isDebug: Boolean) {
  def start(implicit spark: SparkSession): Unit
}


object Mode {

  lazy val isDebug: Mode = new Mode(isDebug = true) {

    override def start(implicit spark: SparkSession): Unit = {
      spark.sparkContext.setLogLevel("ERROR")
    }
  }


  lazy val isProd: Mode = new Mode(isDebug = false) {
    override def start(implicit spark: SparkSession): Unit = {}
  }


}