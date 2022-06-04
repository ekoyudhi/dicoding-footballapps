package id.ekoyudhi.projectfinal.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventLeague (
        @SerializedName("idEvent")
        var eventId : String? = null,

        @SerializedName("dateEvent")
        var eventDate : String? = null,

        @SerializedName("strSport")
        var sportName : String? = null,

        @SerializedName("strTime")
        var eventTime : String? = null,

        @SerializedName("strHomeTeam")
        var homeTeamName : String? = null,

        @SerializedName("strAwayTeam")
        var awayTeamName : String? = null,

        @SerializedName("intHomeScore")
        var homeScore : String? = null,

        @SerializedName("intAwayScore")
        var awayScore : String? = null,

        @SerializedName("strHomeGoalDetails")
        var homeGoalDetails : String? = null,

        @SerializedName("strHomeRedCards")
        var homeRedCards : String? = null,

        @SerializedName("strHomeYellowCards") //10
        var homeYellowCards : String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var homeLineupGoalkeeper : String? = null,

        @SerializedName("strHomeLineupDefense")
        var homeLineupDefense : String? = null,

        @SerializedName("strHomeLineupMidfield")
        var homeLineupMidfield : String? = null,

        @SerializedName("strHomeLineupForward")
        var homeLineupForward : String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        var homeLineupSubstitutes : String? = null,

        @SerializedName("strHomeFormation")
        var homeFormation : String? = null,

        @SerializedName("strAwayRedCards")
        var awayRedCards : String? = null,

        @SerializedName("strAwayYellowCards")
        var awayYellowCards : String? = null,

        @SerializedName("strAwayGoalDetails")
        var awayGoalDetails : String? = null,

        @SerializedName("strAwayLineupGoalkeeper") //20
        var awayLineupGoalkeeper : String? = null,

        @SerializedName("strAwayLineupDefense")
        var awayLineupDefense : String? = null,

        @SerializedName("strAwayLineupMidfield")
        var awayLineupMidfield : String? = null,

        @SerializedName("strAwayLineupForward")
        var awayLineupForward : String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        var awayLineupSubstitutes : String? = null,

        @SerializedName("strAwayFormation")
        var awayFormation : String? = null,

        @SerializedName("intHomeShots")
        var homeShots : String? = null,

        @SerializedName("intAwayShots")
        var awayShots : String? = null,

        @SerializedName("idHomeTeam")
        var homeTeamId : String? = null,

        @SerializedName("idAwayTeam")
        var awayTeamId : String? = null
) : Parcelable