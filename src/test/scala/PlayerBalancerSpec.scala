import model.UpperLowerCoedQuadPlayers
import org.scalatest._
import util.PlayerBalancer
class PlayerBalancerSpec extends FlatSpec with Matchers {

  "A set of lower men with more than upper men" should "move men from lower to upper until they are within 1 of each other" in {
    val upper:Set[String] = Set("Jon Drake", "Marcus", "Joe", "Adam Miracle")
    val lower:Set[String] = Set("Matt", "Andrew", "Bobby", "Allen", "Aaron", "Archer", "Kevin", "Jake", "Manuel")

    val balanced:(Set[String], Set[String]) = PlayerBalancer.balancePlayerSets(upper, "Upper Men", lower, "Lower Men")

    println("Upper Players:")
    balanced._1.foreach(p => println(p))

    println
    println("Lower Players:")
    balanced._2.foreach(p => println(p))

    balanced._1.size should be(6)
    balanced._2.size should be(7)
  }

  "unbalanced coed quads players" should "get balanced" in {
    val upperMen:Set[String] = Set("Joe", "Jon Drake", "Andrew", "Matt", "Aaron")
    val upperWomen:Set[String] = Set("Anna", "Rose", "Lauren", "Laura", "Jenna")
    val lowerMen:Set[String] = Set("Manuel", "Allen", "Bobby", "Archer", "Jake", "Darren Wentz")
    val lowerWomen:Set[String] = Set("Nicole", "Candy", "Candice", "Kloe", "Janet", "Pam", "Paige", "Anne")

    val players = PlayerBalancer.balanceUpperLowerCoedQuadsPlayers(new UpperLowerCoedQuadPlayers(upperMen, upperWomen, lowerMen, lowerWomen))

    players.upperMen.size should be (players.upperWomen.size)
    players.upperMen.size should be (players.lowerMen.size)
    players.upperMen.size should be (players.lowerWomen.size)
  }

  "more unbalanced coed quads players" should "also get balanced" in {
    val upperMen:Set[String] = Set("Joe", "Jon Drake", "Andrew", "Matt", "Aaron")
    val upperWomen:Set[String] = Set("Anna", "Rose", "Lauren", "Laura", "Jenna", "Hillary")
    val lowerMen:Set[String] = Set("Anna", "Rose", "Lauren", "Laura", "Jenna")
    val lowerWomen:Set[String] = Set("Nicole", "Candy", "Candice", "Kloe", "Janet", "Pam", "Paige", "Anne")

    val players = PlayerBalancer.balanceUpperLowerCoedQuadsPlayers(new UpperLowerCoedQuadPlayers(upperMen, upperWomen, lowerMen, lowerWomen))

    players.upperMen.size should be (players.upperWomen.size)
    players.upperMen.size should be (players.lowerMen.size)
    players.upperMen.size should be (players.lowerWomen.size)
  }
}
