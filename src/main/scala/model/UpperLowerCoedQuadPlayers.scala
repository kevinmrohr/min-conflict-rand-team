package model

class UpperLowerCoedQuadPlayers(val upperMen: Set[String], val upperWomen: Set[String], val lowerMen: Set[String], val lowerWomen: Set[String]) {
  override def toString: String = "Upper Men: " + upperMen.toString() + ", Upper Women:" + upperWomen.toString() +
    ", Lower Men: " + lowerMen.toString() + ", Lower Women: " + lowerWomen.toString()
}
