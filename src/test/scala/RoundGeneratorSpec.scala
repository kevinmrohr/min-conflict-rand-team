import org.scalatest._
import util.{RoundGenerator, TournamentAnalytics}

import scala.collection.immutable.ListMap

class RoundGeneratorSpec extends FlatSpec with Matchers {
  "Sets of 10 upper men, 10 lower men, 10 upper women, 10 lower women" should "create 5 unique rounds" in {
    val upperMen:Set[String] = Set("Jon Drake", "Marcus", "Joe", "Adam Miracle", "Jonesy", "J.R.", "Darren Wentz", "Matt Tegtmeier", "Justin Bracken", "Jon Croston")
    val lowerMen:Set[String] = Set("Matt", "Andrew", "Bobby", "Allen", "Aaron", "Archer", "Kevin", "Jake", "Manuel", "Justin")
    val upperWomen:Set[String] = Set("Mary", "Heather", "Nicole", "Jenna", "Janet", "Jess", "Kloe", "Candy", "Zoey", "Alexandria")
    val lowerWomen:Set[String] = Set("Josephine", "Candice", "Paige", "Anna", "Laura", "Lauren", "Pam", "Rose", "Anne", "Jennifer")

    val rounds = RoundGenerator.generateCoedQuadsUpperLowerRounds(5, upperMen, upperWomen, lowerMen, lowerWomen)

    rounds.foreach(r => println(r))
    rounds.size should be(5)
  }
  "Sets of unbalanced upper men and women" should "create 5 unique rounds" in {

    val roundCount:Int = 5
    val upperMen:Set[String] = Set("Jon Drake", "Marcus", "Joe", "Adam Miracle")
    val lowerMen:Set[String] = Set("Matt", "Andrew", "Bobby", "Allen", "Aaron", "Archer", "Kevin", "Jake", "Manuel", "Justin", "Darren Wentz")
    val upperWomen:Set[String] = Set("Mary", "Heather")
    val lowerWomen:Set[String] = Set("Josephine", "Candice", "Paige", "Anna", "Laura", "Lauren", "Pam", "Rose", "Anne", "Jennifer", "Nicole", "Jenna", "Janet", "Jess", "Kloe", "Candy")

    val rounds = RoundGenerator.generateCoedQuadsUpperLowerRounds(roundCount, upperMen, upperWomen, lowerMen, lowerWomen)

    val stats:Map[Set[String], Int]=TournamentAnalytics.calculatePlayerDuplicates(rounds)

    val x = ListMap(stats.toSeq.sortWith(_._2 > _._2):_*)
    x.foreach(println)


    rounds.foreach(r => println(r))
    rounds.size should be(roundCount)
  }
}
