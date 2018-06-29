class Round(val quads:Set[Set[String]]) {
  override def toString: String = quads.mkString(",")
}
