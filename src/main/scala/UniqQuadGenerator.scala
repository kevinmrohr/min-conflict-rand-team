class UniqQuadGenerator {
  def generateRounds(roundCount: Int, men: Set[String], women: Set[String]): Set[Round] = {
    val uniqQuads = generateUniqQuads(men, women)
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

  def generateUniqQuads(men: Set[String], women: Set[String]): Set[Set[String]] = {

    val mensPairs = generateUniqPairs(men)
    val womensPairs = generateUniqPairs(women)

    generateUniqQuadsFromPairs(mensPairs, womensPairs)
  }

  def generateUniqQuadsFromPairs(menPairs: Set[Set[String]], womenPairs: Set[Set[String]],
                                 quads: Set[Set[String]] = Set[Set[String]]())
  : Set[Set[String]] = {

    if (menPairs.isEmpty) quads
    else {

      val manPair = menPairs.head

      val newQuads = quads ++ womenPairs.map(w => Set(manPair, w).flatten)

      generateUniqQuadsFromPairs(menPairs - manPair, womenPairs, newQuads)
    }
  }

  def generateUniqPairs(players: Set[String], pairs: Set[Set[String]] = Set[Set[String]]()): Set[Set[String]] = {
    if (players.size <= 1) pairs
    else {
      val first = players.head

      val remainingPlayers = players - first

      val newPairs = pairs ++ remainingPlayers.map(p => Set(p, first))
      generateUniqPairs(remainingPlayers, newPairs)
    }
  }
}
