package currency

object CurrentState {
  private var lastRates: Map[String, Double] = _
  def setRates (rates : Map[String, Double]) : Unit = lastRates = rates
  def getRate (currencyName : String) : Double = lastRates(currencyName)
}
