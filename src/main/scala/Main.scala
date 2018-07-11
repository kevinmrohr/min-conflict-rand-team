import com.typesafe.scalalogging.Logger
import model.{CoedQuadPlayers, UpperLowerCoedQuadPlayers}
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import util.ExcelWriter.writeOutput
import util.OptionsReader.readOptions
import util.RoundGenerator.generateCoedQuadsUpperLowerRounds
import util.{ExcelReader, RoundGenerator}

val l = Logger("Main")

object Main extends App {
  val usage =
    """
    Usage: [--input-file=/path/to/file.xls --mode=[coed-quads,coed-upper-lower-quads,men-doubles,men-quads,women-doubles,women-quads,coed-doubles](default: coed-quads) --rounds=(default: 5) --output-file=/path/to/file.xls(default: ./target/output.xls)
  """

  val valid_modes = Set[String]("coed-quads", "coed-upper-lower-quads", "men-doubles", "men-quads", "women-doubles", "women-quads", "coed-doubles")
  if (args.length == 0) {
    println("No arguments provided!")
    println(usage)
  }

  val arglist = args.toList

  val options = readOptions(Map(), arglist)

  val mode: String = options.getOrElse('mode, "code-quads").toString
  val roundCount: Int = options.getOrElse('rounds, "5").toString.toInt

  if (valid_modes.contains(mode)) {
    println(mode + " is not a valid mode. Please specify a valid mode. Valid modes are: " + valid_modes)
    sys.exit(1)
  }

  val inputFileName: String = options.getOrElse('inputFile, "src/main/resources/players.xlsx").toString
  val outputFileName: String = options.getOrElse('inputFile, "./target/output.xls").toString
  if (mode.equals("code-quads")) {
    generateCoedQuadsRounds(inputFileName, outputFileName, roundCount)
  } else if (mode.equals("coed-upper-lower-quads")) {
    generateUpperLowerCoedQuadsRounds(inputFileName, outputFileName, roundCount)
  } else {
    println("Mode " + mode + " is not yet supported! Coming soon!")
    sys.exit(1)
  }
}


def generateUpperLowerCoedQuadsRounds(inputFileName: String, outputFileName: String, roundCount: Int): XSSFWorkbook = {

  val players: UpperLowerCoedQuadPlayers = ExcelReader.readUpperLowerCoedQuadsPlayers(inputFileName)

  val rounds = generateCoedQuadsUpperLowerRounds(roundCount, players.upperMen, players.upperWomen, players.lowerMen, players.lowerWomen)

  writeOutput(rounds, outputFileName)
}

def generateCoedQuadsRounds(inputFileName: String, outputFileName: String, roundCount: Int): XSSFWorkbook = {

  val players: CoedQuadPlayers = ExcelReader.readCoedQuadsPlayers(inputFileName)

  val rounds = RoundGenerator.generateCoedQuadsRounds(roundCount, players.men, players.women)

  writeOutput(rounds, outputFileName)
}


