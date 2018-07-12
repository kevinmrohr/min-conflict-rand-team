package util

object PairGenerator {

  /**
    * Take the first A player from the list and pair with all the lower players. Pass the remaining A players into
    * this function recursively until the A player list is exhausted. The B player list also gets passed in, but remains
    * unmodified as it will be paired fully with each A player.
    *
    * @param playersA - Set of one type of player that cannot be paired together (Upper players, for instance)
    * @param playersB - Another set of players that cannot be paired together (Lower players, for instance)
    * @param pairs    - Uniq pairs of A players and B players
    * @return
    */
  def generateUniqABPairs(playersA: Set[String], playersB: Set[String], pairs: Set[Set[String]] = Set[Set[String]]()): Set[Set[String]] = {
    if (playersA.isEmpty) pairs
    else {
      val newPairs = pairs ++ playersB.map(p => Set(p, playersA.head))
      generateUniqABPairs(playersA.drop(1), playersB, newPairs)
    }
  }

  /**
    * Take the first player from the player list and pair them with the remaining players, then pass the remaining players
    * to this function recursively until the list is exhausted
    *
    * @param players
    * @param pairs
    * @return
    */
  def generateUniqPairs(players: Set[String], pairs: Set[Set[String]] = Set[Set[String]]()): Set[Set[String]] = {
    if (players.size <= 1) pairs
    else {
      val remainingPlayers = players.drop(1)
      val newPairs = pairs ++ remainingPlayers.map(p => Set(p, players.head))
      generateUniqPairs(remainingPlayers, newPairs)
    }
  }
}
