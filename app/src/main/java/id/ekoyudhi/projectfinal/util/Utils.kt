package id.ekoyudhi.projectfinal.util

import android.view.View
import id.ekoyudhi.projectfinal.model.EventLeague
import id.ekoyudhi.projectfinal.model.FavMatches
import id.ekoyudhi.projectfinal.model.FavTeams
import id.ekoyudhi.projectfinal.model.Team
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.LinkedHashMap

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun replaceSemiColonWithNewline(str : String?) : String? {
    return if (str.isNullOrBlank())
        str
    else
        str!!.replace(";","\n",false)
}

/*fun fullDateString(strDate : String) : String {
    val date : Date = SimpleDateFormat("yyyy-MM-dd").parse(strDate)
    //val date : Date = SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ").parse(strFull)
    return SimpleDateFormat("E, dd MMM yyyy").format(date)
}*/

fun getAllLeagues() : LinkedHashMap<String, String> {
    val res = LinkedHashMap<String, String>()
    res["English Premier League"] = "4328"
    res["English League Championship"] = "4329"
    res["Scottish Premier League"] = "4330"
    res["German Bundesliga"] = "4331" //
    res["Italian Serie A"] = "4332" //
    res["French Ligue 1"] = "4334" //
    res["Spanish La Liga"] = "4335" //
    //res.put("Greek Superleague Greece","4336")
    //res.put("Dutch Eredivisie","4337") //
    //res.put("Belgian Jupiler League","4338")
    //res.put("Turkish Super Lig","4339")
    //res.put("Danish Superliga","4340")
    //res.put("Portuguese Primeira Liga","4344") //
    //res.put("American Major League Soccer","4346") //
    //res.put("Swedish Allsvenskan","4347")
    //res.put("Mexican Primera League","4350")
    //res.put("Brazilian Brasileirao","4351")
    //res.put("Ukrainian Premier League","4354")
    //res.put("Russian Football Premier League","4355")
    //res.put("Australian A-League","4356")
    //res.put("Eliteserien","4358")
    //res.put("Chinese Super League","4359")

    return res
}


/*fun convertToDate(strDate : String, strTime : String) : Date {
    //2018-09-10 19:00:00+00:00
    val strFull = "$strDate $strTime"
    val date: Date = SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ").parse(strFull)
    val gc = GregorianCalendar()
    gc.timeInMillis = date.time

    return gc.time
}*/

fun toGMTFormat(strDate : String, strTime : String?) : Date {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    var dateTime = ""
    if (strTime.isNullOrEmpty())
        dateTime = "$strDate 00:00:00"
    else
        dateTime = "$strDate ${strTime?.substring(0,8)}"
    return formatter.parse(dateTime)
}

fun convertFavMatchesToEventLeague(fav:FavMatches) : EventLeague {
    return EventLeague(
            eventId = fav.eventId,
            eventDate = fav.eventDate,
            eventTime = fav.eventTime,
            homeYellowCards = fav.homeYellowCards,
            homeTeamName = fav.homeTeamName,
            homeTeamId = fav.homeTeamId,
            homeShots = fav.homeShots,
            homeScore = fav.homeScore,
            homeRedCards = fav.homeRedCards,
            homeLineupSubstitutes = fav.homeLineupSubstitutes,
            homeLineupMidfield = fav.homeLineupMidfield,
            homeLineupGoalkeeper = fav.homeLineupGoalkeeper,
            homeLineupForward = fav.homeLineupForward,
            homeLineupDefense = fav.homeLineupDefense,
            homeGoalDetails = fav.homeGoalDetails,
            homeFormation = fav.homeFormation,
            awayYellowCards = fav.awayYellowCards,
            awayTeamName = fav.awayTeamName,
            awayTeamId = fav.awayTeamId,
            awayShots = fav.awayShots,
            awayScore = fav.awayScore,
            awayRedCards = fav.awayRedCards,
            awayLineupSubstitutes = fav.awayLineupSubstitutes,
            awayLineupMidfield = fav.awayLineupMidfield,
            awayLineupGoalkeeper = fav.awayLineupGoalkeeper,
            awayLineupForward = fav.awayLineupForward,
            awayLineupDefense = fav.awayLineupDefense,
            awayGoalDetails = fav.awayGoalDetails,
            awayFormation = fav.awayFormation
    )
}

fun convertFavTeamsToTeam(fav:FavTeams) : Team {
    return Team(
            teamId = fav.teamId,
            teamBadge = fav.teamBadge,
            teamDescriptionEN = fav.teamDescriptionEN,
            teamFormedYear = fav.teamFormedYear,
            teamName = fav.teamName,
            teamStadium = fav.teamStadium
    )
}