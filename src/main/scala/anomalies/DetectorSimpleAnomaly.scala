package anomalies

trait DetectorSimpleAnomaly[T] extends Serializable {
  def isAnomaly(t: T): Boolean

  def toAnomaly(t: T): String
}
