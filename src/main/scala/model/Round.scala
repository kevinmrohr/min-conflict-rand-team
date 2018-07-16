package model

class Round(val teams:Set[Set[String]]=Set()) {
  override def toString: String = teams.mkString(",")
}
