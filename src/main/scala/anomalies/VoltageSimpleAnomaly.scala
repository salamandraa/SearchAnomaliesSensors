package anomalies

import data.LightSensorData

object VoltageSimpleAnomaly extends DetectorSimpleAnomaly[LightSensorData] {
  override def isAnomaly(t: LightSensorData): Boolean = {
    t.voltage < 4.5 || t.voltage > 5.5
  }
}


