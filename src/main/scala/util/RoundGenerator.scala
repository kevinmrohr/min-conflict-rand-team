package util

import model.{Round, UpperLowerCoedQuadPlayers}

import scala.util.Random

object RoundGenerator {

  def calculateCoedUpperLowerTripsPerRound(players: UpperLowerCoedQuadPlayers): Int = {
    players.playerCount % 4 match {
      case 0 => 0
      case 1 => 3
      case 2 => 2
      case 3 => 1
    }
  }


  def generateCoedQuadsUpperLowerRounds(roundCount: Int, upperMen: Set[String], upperWomen: Set[String], lowerMen: Set[String], lowerWomen: Set[String]): Set[Round] = {
    val players: UpperLowerCoedQuadPlayers = PlayerBalancer.balanceUpperLowerCoedQuadsPlayers(new UpperLowerCoedQuadPlayers(upperMen, upperWomen, lowerMen, lowerWomen))

    val tripsRequired: Int = calculateCoedUpperLowerTripsPerRound(players)
    generateRounds(roundCount, tripsRequired, players)
  }


  def incTripsCountsForTeam(counts: Map[String, Int], tripsTeam: Set[String]): Map[String, Int] = {
    if (tripsTeam.isEmpty) counts
    else {
      val player: String = tripsTeam.head
      incTripsCountsForTeam(counts.updated(player, counts.getOrElse(player, 0) + 1), tripsTeam - player)
    }
  }

  def incTripsCountsForRound(tripsTeamCountByPlayer: Map[String, Int], tripsTeams: Set[Set[String]]): Map[String, Int] = {
    if (tripsTeams.isEmpty) tripsTeamCountByPlayer
    else {
      val tripsTeam = tripsTeams.head
      incTripsCountsForRound(
        incTripsCountsForTeam(tripsTeamCountByPlayer, tripsTeam),
        tripsTeams - tripsTeam)
    }
  }

  def getPlayersThatHavePlayedOnTheLeastTripTeams(players: UpperLowerCoedQuadPlayers, counts: Map[String, Int]): UpperLowerCoedQuadPlayers = {
    def findMinTripsSet(s: Set[String], p: String): Set[String] = {
      if (s.isEmpty) Set(p)
      else {
        val leastTimesPlayedInTrips = counts.getOrElse(s.head, 0)
        val timesPlayerPlayedInTrips = counts.getOrElse(p, 0)
        if (timesPlayerPlayedInTrips < leastTimesPlayedInTrips) Set(p)
        else if (timesPlayerPlayedInTrips == leastTimesPlayedInTrips) s + p
        else s
      }
    }

    new UpperLowerCoedQuadPlayers(players.upperMen.foldLeft(Set[String]())(findMinTripsSet),
      players.upperWomen.foldLeft(Set[String]())(findMinTripsSet),
      players.lowerMen.foldLeft(Set[String]())(findMinTripsSet),
      players.lowerWomen.foldLeft(Set[String]())(findMinTripsSet))

  }

  def generateTripsTeam(players: UpperLowerCoedQuadPlayers, rounds: Set[Round], tripsTeamCountByPlayer: Map[String, Int],
                        round: Round): Set[String] = {
    val rnd = new Random()

    val existingTripsTeams: Set[Set[String]] = rounds.flatMap(r => r.teams.filter(t => t.size == 3))

    val low: UpperLowerCoedQuadPlayers = getPlayersThatHavePlayedOnTheLeastTripTeams(players, tripsTeamCountByPlayer)

    val lowerMenCount = tripsTeamCountByPlayer.getOrElse(low.lowerMen.head, 0)
    val lowerWomenCount = tripsTeamCountByPlayer.getOrElse(low.lowerWomen.head, 0)

    val thirdPlayer = if (lowerMenCount <= lowerWomenCount) low.lowerMen.toVector(rnd.nextInt(low.lowerMen.size))
    else low.lowerWomen.toVector(rnd.nextInt(low.lowerWomen.size))

    Set(low.upperMen.toVector(rnd.nextInt(low.upperMen.size)), low.upperWomen.toVector(rnd.nextInt(low.upperWomen.size)), thirdPlayer)
  }

  def findMinOtherPlayers(upperMan: String, p: UpperLowerCoedQuadPlayers):(String, String, String) = {

  }

  def findTeam(p: UpperLowerCoedQuadPlayers, prevRoundsTeams: Set[Set[String]]): Set[String] = {
    val rnd = new Random()

    val upperMan = p.upperMen.toVector(rnd.nextInt(p.upperMen.size))
    val otherPlayers = findMinOtherPlayers(upperMan, p)

    val potentialTeam =
      Set(upperMan,
        p.upperWomen.toVector(rnd.nextInt(p.upperWomen.size)),
        p.lowerMen.toVector(rnd.nextInt(p.lowerMen.size)),
        p.lowerWomen.toVector(rnd.nextInt(p.lowerWomen.size)))
    if (prevRoundsTeams.contains(potentialTeam)) findTeam(p, prevRoundsTeams) else potentialTeam
  }

  def generateQuadsTeam(p: UpperLowerCoedQuadPlayers, rounds: Set[Round], tripsTeamCountByPlayer: Map[String, Int], round: Round,
                        pairs: Map[Set[String], Int]): Set[String] = {
    val prevRoundsTeams: Set[Set[String]] = rounds.flatMap(r => r.teams)
    findTeam(p, prevRoundsTeams)
  }

  /**
    * Generate a team for a given round.
    * We know how many trips teams we need in each round, so the current round doesn't have that many trips teams yet, generate a trips team.
    * Otherwise generate a quads team
    *
    * @param tripsRequired          - number of trips teams needed per round
    * @param players                - players that have not yet been played on a team
    * @param rounds                 - previously generated rounds
    * @param tripsTeamCountByPlayer - a map keyed by player name, where the value is the number of times that player
    *                               has been placed on a trips team across all rounds
    * @param round                  - the round we are currently generating, with all the teams that have been generated so far
    * @return a new team of players
    */
  def generateTeam(tripsRequired: Int, players: UpperLowerCoedQuadPlayers, rounds: Set[Round],
                   tripsTeamCountByPlayer: Map[String, Int], round: Round,
                   pairs: Map[Set[String], Int]): Set[String] = {
    if (round.teams.count(t => t.size == 3) < tripsRequired) {
      generateTripsTeam(players, rounds, tripsTeamCountByPlayer, round, pairs)
    } else {
      generateQuadsTeam(players, rounds, tripsTeamCountByPlayer, round, pairs)
    }
  }


  def addToPairsForPlayer(player: String, otherPlayers: Set[String], pairs: Map[Set[String], Int]): Map[Set[String], Int] = {
    otherPlayers.foldLeft(pairs)((pairs, p) => pairs.updated(Set(p, player), pairs.getOrElse(Set(p, player), 0) + 1))
  }

  def updatePairs(pairs: Map[Set[String], Int], team: Set[String]): Map[Set[String], Int] = {
    if (team.isEmpty) pairs
    else {
      val player: String = team.head
      val otherPlayers = team - team.head

      updatePairs(addToPairsForPlayer(player, otherPlayers, pairs), team - player)
    }
  }

  def generateRound(tripsRequired: Int,
                    players: UpperLowerCoedQuadPlayers,
                    rounds: Set[Round],
                    tripsTeamCountByPlayer: Map[String, Int],
                    round: Round = new Round(),
                    pairs: Map[Set[String], Int] = Map()): Round = {
    if (players.playerCount == 0) round
    else {
      val team: Set[String] = generateTeam(tripsRequired, players, rounds, tripsTeamCountByPlayer, round, pairs)
      val isTrip: Boolean = team.size == 3
      val newCounts: Map[String, Int] = if (isTrip) incTripsCountsForTeam(tripsTeamCountByPlayer, team) else tripsTeamCountByPlayer
      val remainingPlayers = players.subtractTeam(team)
      val newPairs = updatePairs(pairs, team)
      if (isTrip && tripsRequired == (round.teams + team).size) {
        //If we just got through allocating all of the trips teams, we need to rebalance the categories
        generateRound(tripsRequired, PlayerBalancer.balanceUpperLowerCoedQuadsPlayers(remainingPlayers), rounds, newCounts, new Round(round.teams + team), newPairs)
      } else {
        generateRound(tripsRequired, remainingPlayers, rounds, newCounts, new Round(round.teams + team), newPairs)
      }
    }
  }

  def generateRounds(roundCount: Int, tripsRequired: Int, players: UpperLowerCoedQuadPlayers, rounds: Set[Round] = Set(),
                     tripsTeamCountByPlayer: Map[String, Int] = Map[String, Int]().withDefaultValue(0)): Set[Round] = {
    if (roundCount == 0) rounds
    else {
      val newRound: Round = generateRound(tripsRequired, players, rounds, tripsTeamCountByPlayer)
      generateRounds(roundCount - 1, tripsRequired, players, rounds + newRound, incTripsCountsForRound(tripsTeamCountByPlayer, newRound.teams.filter(t => t.size == 3)))
    }
  }
}
