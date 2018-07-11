package util

import model.Round

object RoundGenerator {
  def generateCoedQuadsUpperLowerRounds(roundCount: Int, upperMen: Set[String], upperWomen: Set[String], lowerMen: Set[String], lowerWomen: Set[String]): Set[Round] = {
    val uniqQuads:Set[Set[String]] = QuadGenerator.generateUniqUpperLowerCoedQuads(upperMen, lowerMen, upperWomen, lowerWomen)
    generateRoundsFromQuads(roundCount, uniqQuads, upperMen ++ upperWomen ++ lowerMen ++ lowerWomen)
  }
  def generateCoedQuadsRounds(roundCount: Int, men: Set[String], women: Set[String]): Set[Round] = {
    val uniqQuads = QuadGenerator.generateUniqCoedQuads(men, women)
    generateRoundsFromQuads(roundCount, uniqQuads, men ++ women)
  }


  def generateRoundsFromQuads(roundCount: Int, uniqQuads: Set[Set[String]], allPlayers: Set[String], rounds: Set[Round] = Set(), playersAlreadyInRound: Set[String] = Set()): Set[Round] = {
    if (roundCount == 0) rounds
    else {
      val newRound = generateRound(uniqQuads, allPlayers)
      generateRoundsFromQuads(roundCount - 1, uniqQuads.diff(newRound.quads), allPlayers, rounds + newRound)
    }
  }


  def generateRound(uniqQuads: Set[Set[String]], playerList: Set[String], quads: Set[Set[String]] = Set()): Round = {

    if (playerList.isEmpty) new Round(quads)
    else {
      val quad = findQuadForPlayers(uniqQuads, playerList)
      generateRound(uniqQuads - quad, playerList -- quad, quads + quad)
    }
  }

  def findQuadForPlayers(uniqQuads: Set[Set[String]], playerList: Set[String]): Set[String] = {
    uniqQuads.find(q => q.subsetOf(playerList)).get
  }


}
