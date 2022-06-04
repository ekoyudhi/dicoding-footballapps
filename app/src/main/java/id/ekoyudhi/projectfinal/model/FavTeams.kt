package id.ekoyudhi.projectfinal.model

data class FavTeams (val id : Long?,
                     val teamId : String?,
                     val teamName : String?,
                     val teamFormedYear : String?,
                     val teamStadium : String?,
                     val teamDescriptionEN : String?,
                     val teamBadge : String?) {

    companion object {
        const val TABLE_TEAMS_FAV : String = "TABLE_TEAMS_FAV"
        const val ID : String = "ID_"
        const val TEAM_ID : String = "TEAM_ID"
        const val TEAM_NAME : String = "TEAM_NAME"
        const val TEAM_FORMED_YEAR : String = "TEAM_FORMED_YEAR"
        const val TEAM_STADIUM : String = "TEAM_STADIUM"
        const val TEAM_DESCRIPTION_EN : String = "TEAM_DESCRIPTION_EN"
        const val TEAM_BADGE : String = "TEAM_BADGE"
    }
}