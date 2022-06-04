package id.ekoyudhi.projectfinal.model

data class FavMatches (val id: Long?,
                       val eventId: String?,
                       val eventDate: String?,
                       val eventTime : String?,
                       val homeTeamName: String?,
                       val homeScore: String?,
                       val awayTeamName: String?,
                       val awayScore: String?,
                       val homeGoalDetails : String?,
                       val homeRedCards : String?,
                       val homeYellowCards : String?,
                       val homeLineupGoalkeeper : String?,
                       val homeLineupDefense : String?,
                       val homeLineupMidfield : String?,
                       val homeLineupForward : String?,
                       val homeLineupSubstitutes : String?,
                       val homeFormation : String?,
                       val awayGoalDetails : String?,
                       val awayRedCards : String?,
                       val awayYellowCards : String?,
                       val awayLineupGoalkeeper : String?,
                       val awayLineupDefense : String?,
                       val awayLineupMidfield : String?,
                       val awayLineupForward : String?,
                       val awayLineupSubstitutes : String?,
                       val awayFormation : String?,
                       val homeShots : String?,
                       val awayShots : String?,
                       val homeTeamId : String?,
                       val awayTeamId : String?) {

    companion object {
        const val TABLE_MATCHES_FAV: String = "TABLE_MATCHES_FAV"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val EVENT_TIME : String = "EVENT_TIME"
        const val HOME_TEAM_NAME: String = "HOME_TEAM_NAME"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_TEAM_NAME: String = "AWAY_TEAM_NAME"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val HOME_GOAL_DETAILS: String = "HOME_GOAL_DETAILS"
        const val HOME_RED_CARDS: String = "HOME_RED_CARDS"
        const val HOME_YELLOW_CARDS: String = "HOME_YELLOW_CARDS"
        const val HOME_LINEUP_GOALKEEPER: String = "HOME_LINEUP_GOALKEEPER"
        const val HOME_LINEUP_DEFENSE: String = "HOME_LINEUP_DEFENSE"
        const val HOME_LINEUP_MIDFIELD: String = "HOME_LINEUP_MIDFIELD"
        const val HOME_LINEUP_FORWARD: String = "HOME_LINEUP_FORWARD"
        const val HOME_LINEUP_SUBSTITUTES: String = "HOME_LINEUP_SUBSTITUTES"
        const val HOME_FORMATION: String = "HOME_FORMATION"
        const val AWAY_GOAL_DETAILS: String = "AWAY_GOAL_DETAILS"
        const val AWAY_RED_CARDS: String = "AWAY_RED_CARDS"
        const val AWAY_YELLOW_CARDS: String = "AWAY_YELLOW_CARDS"
        const val AWAY_LINEUP_GOALKEEPER: String = "AWAY_LINEUP_GOALKEEPER"
        const val AWAY_LINEUP_DEFENSE: String = "AWAY_LINEUP_DEFENSE"
        const val AWAY_LINEUP_MIDFIELD: String = "AWAY_LINEUP_MIDFIELD"
        const val AWAY_LINEUP_FORWARD: String = "AWAY_LINEUP_FORWARD"
        const val AWAY_LINEUP_SUBSTITUTES: String = "AWAY_LINEUP_SUBSTITUTES"
        const val AWAY_FORMATION: String = "AWAY_FORMATION"
        const val HOME_SHOTS: String = "HOME_SHOTS"
        const val AWAY_SHOTS: String = "AWAY_SHOTS"
        const val HOME_TEAM_ID: String = "HOME_TEAM_ID"
        const val AWAY_TEAM_ID: String = "AWAY_TEAM_ID"

    }
}