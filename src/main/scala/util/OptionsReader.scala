package util

object OptionsReader {

  def readOptions(map: Map[Symbol, Any], list: List[String]): Map[Symbol, Any] = {
    def isSwitch(s: String) = s(0) == '-'

    list match {
      case Nil => map
      case "--input-file" :: value :: tail =>
        readOptions(map ++ Map('inputFile -> value), tail)
      case "--mode" :: value :: tail =>
        readOptions(map ++ Map('mode -> value), tail)
      case "--rounds" :: value :: tail =>
        readOptions(map ++ Map('rounds -> value.toInt), tail)
      case "--output-file" :: value :: tail =>
        readOptions(map ++ Map('outputFile -> value), tail)

      case option :: tail => println("Unknown option " + option)
        sys.exit(1)
    }
  }

}
