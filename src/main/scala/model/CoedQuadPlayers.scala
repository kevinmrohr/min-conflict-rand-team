package model

class CoedQuadPlayers(val men: Set[String], val women: Set[String]) {
  override def toString: String = "Men: " + men.toString() + ", Women: " + women.toString()
}
