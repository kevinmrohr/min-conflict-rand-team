package model


class UpperLowerCoedQuadPlayers(val upperMen: Set[String], val upperWomen: Set[String], val lowerMen: Set[String], val lowerWomen: Set[String]) {
  def subtractTeam(team: Set[String]): UpperLowerCoedQuadPlayers = {
    new UpperLowerCoedQuadPlayers(this.upperMen -- team, this.upperWomen -- team, this.lowerMen -- team, this.lowerWomen -- team)
  }


  override def toString: String = "Upper Men: " + upperMen.toString() + "\nUpper Women:" + upperWomen.toString() +
    "\nLower Men: " + lowerMen.toString() + "\nLower Women: " + lowerWomen.toString()

  def playerCount: Int = upperMen.size + upperWomen.size + lowerMen.size + lowerWomen.size
}
