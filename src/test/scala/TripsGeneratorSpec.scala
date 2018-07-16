import org.scalatest._
import util.TeamGenerator

class TripsGeneratorSpec extends FlatSpec with Matchers {
  "blah" should "blah" in {
    val men:Set[String] = Set("Mark", "Matthew", "Luke", "John")
    val women:Set[String] = Set("Mary", "Magdalene")
    val tripTeams:Set[Set[String]] = TeamGenerator.generateUniqCoedTrips(men, women)

    tripTeams.foreach(t => println(t))
  }

}
