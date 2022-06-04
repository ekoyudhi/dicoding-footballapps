package id.ekoyudhi.projectfinal.api

import id.ekoyudhi.projectfinal.BuildConfig

object TheSportDBApi {
    fun getNextLeagueEvents(leagueId: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id=" + leagueId
    }

    fun getPastLeagueEvents(leagueId: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=" + leagueId
    }

    fun getAllTeamsLeague(leagueId: String?) : String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_teams.php?id=" + leagueId
    }

    fun getTeamDetail(teamId: String?) : String {
        //https@ //www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=133604
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + teamId
    }

    fun getAllPlayer(teamId: String?) : String {
        //https://www.thesportsdb.com/api/v1/json/1/lookup_all_players.php?id=133604
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_players.php?id=" + teamId
    }

    fun getTeamSearch(teamName : String?) : String {
        //https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=Arsenal
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchteams.php?t=" + teamName
    }

    fun getEventSearch(teamMatch : String?) : String {
        //https://www.thesportsdb.com/api/v1/json/1/searchevents.php?e=Arsenal_vs_Chelsea
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchevents.php?e=" + teamMatch
    }


}