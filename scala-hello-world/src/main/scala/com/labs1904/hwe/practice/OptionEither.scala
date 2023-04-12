package com.labs1904.hwe.practice

case class Item(description: String, price: Option[Int])

case class WeatherStation(name: String, temperature: Option[Int])

object OptionEither {
  /*
    Returns age of a dog when given a human age.
    Returns None if the input is None.
  */
  def dogAge(humanAge: Option[Int]): Option[Int] = {
    humanAge match {
      case Some(age) => Option(age * 7)
      case None => None
    }
  }

  /*
    Returns the total cost af any item.
    If that item has a price, then the price + 7% of the price should be returned.
  */
  def totalCost(item: Item): Option[Double] = {
    if( item.price.isDefined ) {
      return Option(item.price.get * 1.07)
    }
    None
  }

  /*
    Given a list of weather temperatures, calculates the average temperature across all weather stations.
    Some weather stations don't report temperature
    Returns None if the list is empty or no weather stations contain any temperature reading.
   */
  def averageTemperature(temperatures: List[WeatherStation]): Option[Int] = {
    val tempList: List[Int] = temperatures.flatMap((w: WeatherStation) => w.temperature)
    if (tempList.isEmpty) {
      None
    } else {
      Option(tempList.sum / tempList.size)
    }
  }
}
