import org.scalatest._
import util.{TeamGenerator, RoundGenerator}

class UniqSpec extends FlatSpec with Matchers {
  "A set of men and a set of women" should "produce a uniq list of all 2 men 2 women possible combinations" in {
    val men = Set("Bert", "Ernie", "John Bobbitt", "Fuckin Frank",
      "Jeff", "Scott", "Aer", "James",
      "Michael", "Andrew", "Kevin", "Razi")
    val women = Set("Lorena Bobbitt", "Jenny", "Psycho Sarah", "Monica",
      "Amy", "Alexandria", "Poppy", "Eleanor",
      "Julie", "Ellie", "Farrah", "Ava")


    val uniqQuads = TeamGenerator.generateUniqCoedQuads(men, women)

    uniqQuads.foreach(q => println(q))
    println(uniqQuads.size)
  }
  /*
  "A set of men and women" should "produce a unique set of 100 rounds" in {
    val men = Set("Bert", "Ernie", "John Bobbitt", "11Fuckin Frank",
      "Jeff", "Scott", "Aer", "James",
      "Michael", "Andrew", "Kevin", "Razi")
    val women = Set("Lorena Bobbitt", "Jenny", "Psycho Sarah", "Monica",
      "Amy", "Alexandria", "Poppy", "Eleanor",
      "Julie", "Ellie", "Farrah", "Ava")
    val rounds = RoundGenerator.generateCoedQuadsRounds(6, men, women)

    rounds.foreach(r => println(r))
  }
  */
}
