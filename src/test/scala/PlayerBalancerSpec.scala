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
}
