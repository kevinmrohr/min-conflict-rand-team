package util

object TeamGenerator {
  def generateUniqCoedTrips(men: Set[String], women: Set[String]): Set[Set[String]] = {

    val menPairs = PairGenerator.generateUniqPairs(men)
    val womenPairs = PairGenerator.generateUniqPairs(women)

    val twoMenTrips:Set[Set[String]] = menPairs.flatMap(manPair => women.map(w => manPair + w))
    val twoWomenTrips:Set[Set[String]] = womenPairs.flatMap(womanPair => men.map(m => womanPair + m))

    twoMenTrips ++ twoWomenTrips
  }

  def generateUniqUpperLowerCoedTrips(upperMen:Set[String], upperWomen:Set[String], lowerMen:Set[String], lowerWomen:Set[String]): Set[Set[String]] = {
    val menPairs = PairGenerator.generateUniqABPairs(upperMen, lowerMen)
    val womenPairs = PairGenerator.generateUniqABPairs(upperWomen, lowerWomen)

    val twoMenTrips:Set[Set[String]] = menPairs.flatMap(manPair => upperWomen.map(w => manPair + w))
    val twoWomenTrips:Set[Set[String]] = womenPairs.flatMap(womanPair => upperMen.map(m => womanPair + m))

    twoMenTrips ++ twoWomenTrips
  }

  def generateUniqUpperLowerCoedQuads(upperMen: Set[String], lowerMen: Set[String], upperWomen: Set[String], lowerWomen: Set[String]): Set[Set[String]] = {
    val mensPairs: Set[Set[String]] = PairGenerator.generateUniqABPairs(upperMen, lowerMen)
    val womensPairs: Set[Set[String]] = PairGenerator.generateUniqABPairs(upperWomen, lowerWomen)
    generateUniqQuadsFromPairs(mensPairs, womensPairs)
  }


  def generateUniqCoedQuads(men: Set[String], women: Set[String]): Set[Set[String]] = {
    val mensPairs = PairGenerator.generateUniqPairs(men)
    val womensPairs = PairGenerator.generateUniqPairs(women)
    generateUniqQuadsFromPairs(mensPairs, womensPairs)
  }

  def generateUniqQuadsFromPairs(menPairs: Set[Set[String]], womenPairs: Set[Set[String]],
                                 quads: Set[Set[String]] = Set[Set[String]]()): Set[Set[String]] = {

    if (menPairs.isEmpty) quads
    else {

      val manPair = menPairs.head

      val newQuads = quads ++ womenPairs.map(w => Set(manPair, w).flatten)

      generateUniqQuadsFromPairs(menPairs - manPair, womenPairs, newQuads)
    }
  }
}
