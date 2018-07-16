package util

import model.Round

import scala.collection.immutable.ListMap

object TournamentAnalytics {

  def incrementForPlayer(player: String, team: Set[String], stats: Map[Set[String], Int]): Map[Set[String], Int] = {
    (team - player).foldLeft(stats)((s: Map[Set[String], Int], op: String) => {
      val pair: Set[String] = Set(player, op)
      s.updated(pair, s.getOrElse(pair, 0) + 1)
    })
  }

  def incrementTeam(shrinkingTeam: Set[String], team: Set[String], stats: Map[Set[String], Int]): Map[Set[String], Int] = {
    if (shrinkingTeam.isEmpty) stats
    else {
      val player = shrinkingTeam.head
      incrementTeam(shrinkingTeam - player, team, incrementForPlayer(player, team, stats))
    }
  }

  def incrementTeams(teams: Set[Set[String]], stats: Map[Set[String], Int]): Map[Set[String], Int] = {
    if (teams.isEmpty) stats
    else {
      val team = teams.head
      incrementTeams(teams - team, incrementTeam(team, team, stats))
    }
  }

  def incrementRounds(rounds: Set[Round], stats: Map[Set[String], Int] = Map().withDefaultValue(0)): Map[Set[String], Int] = {
    if (rounds.isEmpty) stats
    else {
      val round: Round = rounds.head
      incrementRounds(rounds - round, incrementTeams(round.teams, stats))
    }
  }

  def calculatePlayerDuplicates(rounds: Set[Round]): Map[Set[String], Int] = {
    val stats=incrementRounds(rounds)
    val m = stats map {case (key, value) => if (key.size==2) (key, value/2) else (key, value)}
    ListMap(m.toSeq.sortWith(_._2 > _._2):_*)
  }

}
