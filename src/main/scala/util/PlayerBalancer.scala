package util

import com.typesafe.scalalogging.Logger
import model.UpperLowerCoedQuadPlayers

object PlayerBalancer {
  val l = Logger("PlayerBalancer")

  def balanceUpperLowerCoedQuadsPlayers(players: UpperLowerCoedQuadPlayers): UpperLowerCoedQuadPlayers = {
    l.info(s"Before balancing, $players")

    if (players.playerCount % 4 != 0) {
      l.warn("playerCount is not divisible by 4! balancing will be imperfect!")
    }

    if (players.lowerMen.size % 2 == players.upperMen.size % 2) {
      val men: (Set[String], Set[String]) = balancePlayerSets(players.upperMen, "Upper Men", players.lowerMen, "Lower Men")
      val women: (Set[String], Set[String]) = balancePlayerSets(players.upperWomen, "Upper Women", players.lowerWomen, "Lower Women")
      val uppers: (Set[String], Set[String]) = balancePlayerSets(men._1, "Upper Men", women._1, "Upper Women")
      val lowers: (Set[String], Set[String]) = balancePlayerSets(men._2, "Lower Men", women._2, "Lower Women")
      val result = new UpperLowerCoedQuadPlayers(uppers._1, uppers._2, lowers._1, lowers._2)
      l.info(s"After balancing: $result")
      result
    } else {
      val uppers: (Set[String], Set[String]) = balancePlayerSets(players.upperMen, "Upper Men", players.upperWomen, "Upper Women")
      val lowers: (Set[String], Set[String]) = balancePlayerSets(players.lowerMen, "Lower Men", players.lowerWomen, "Lower Women")
      val men: (Set[String], Set[String]) = balancePlayerSets(uppers._1, "Upper Men", lowers._1, "Lower Men")
      val women: (Set[String], Set[String]) = balancePlayerSets(uppers._2, "Upper Women", lowers._2, "Lower Women")

      val result = new UpperLowerCoedQuadPlayers(men._1, women._1, men._2, women._2)
      l.info(s"After balancing: $result")
      result
    }

  }

  /**
    * Balances two lists of players. For instance, if we have 10 upper men and 20 lower men, we want to move 5 lower men into the upper category
    *
    * The descriptions of the lists are really just for logging purposes. This method can be used to balance lists of
    * upper and lower men, upper and lower women, upper men and women, or lower men and women
    *
    * @param players1 - list of a type of players
    * @param p1Desc   - description of the list of players
    * @param players2 - list of second type of players
    * @param p2Desc   - description of list of second type of players
    * @return
    */
  def balancePlayerSets(players1: Set[String], p1Desc: String, players2: Set[String], p2Desc: String): (Set[String], Set[String]) = {
    //The (players2.size + 1) is because one set has to be at least 2 greater than the other for it to make sense to transfer a player from one set to the other
    //otherwise, you're going to infinitely toggle which has say 12, and which has 13


    val diff = players1.size - players2.size
    //if neither list is at least 2 greater than the other, just return a tuple of the two lists
    if (diff <= 1 && diff >= -1) (players1, players2)
    else if (players1.size > players2.size + 1) {
      l.info(s"moving ${players1.head} from $p1Desc to $p2Desc ")
      balancePlayerSets(players1.drop(1), p1Desc, players2 + players1.head, p2Desc)
    } else {
      l.info(s"moving ${players2.head} from $p2Desc to $p1Desc ")
      balancePlayerSets(players1 + players2.head, p1Desc, players2.drop(1), p2Desc)
    }
  }
}
