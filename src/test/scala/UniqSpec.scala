import org.scalatest._
import util.RoundGenerator

class UniqSpec extends FlatSpec with Matchers {
  "A set of men and a set of women" should "produce a uniq list of all 2 men 2 women possible combinations" in {
    val men = Set("Bert", "Ernie", "John Bobbitt", "Fuckin Frank",
      "Jeff", "Scott", "Aer", "James",
      "Michael", "Andrew", "Kevin", "Razi")
    val women = Set("Lorena Bobbitt", "Jenny", "Psycho Sarah", "Monica",
      "Amy", "Alexandria", "Poppy", "Eleanor",
      "Julie", "Ellie", "Farrah", "Ava")


    val uniqQuads = new RoundGenerator().generateUniqQuads(men, women)

    uniqQuads.foreach(q => println(q))
    println(uniqQuads.size)
  }
  "A set of men and women" should "produce a unique set of 100 rounds" in {
    val men = Set("Bert", "Ernie", "John Bobbitt", "Fuckin Frank",
      "Jeff", "Scott", "Aer", "James",
      "Michael", "Andrew", "Kevin", "Razi")
    val women = Set("Lorena Bobbitt", "Jenny", "Psycho Sarah", "Monica",
      "Amy", "Alexandria", "Poppy", "Eleanor",
      "Julie", "Ellie", "Farrah", "Ava")
    val rounds = new RoundGenerator().generateRounds(6, men, women)

    rounds.foreach(r => println(r))
  }
  /*
    "A set of players" should "produce a uniq set of pairs" in {
      val expected = Set[Set[String]](
        Set("Fuckin Frank", "Bert"),
        Set("Ernie", "Bert"),
        Set("Fuckin Frank", "Ernie"),
        Set("John Bobbitt", "Bert"),
        Set("Fuckin Frank", "John Bobbitt"),
        Set("John Bobbitt", "Ernie")
      )
      val men = Set("Bert", "Ernie", "John Bobbitt", "Fuckin Frank")
      val uniqPairs = new util.UniqQuadGenerator().generateUniqPairs(men)

      uniqPairs.foreach(p => println(p))
      //Sorting.stableSort(uniqPairs.toSeq) should be (Sorting.stableSort(expected.toSeq))
    }
  */
}
