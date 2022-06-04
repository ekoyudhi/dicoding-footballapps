package id.ekoyudhi.projectfinal.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
        @SerializedName("idPlayer")
        var playerId : String? = null,

        @SerializedName("strPlayer")
        var playerName : String?=null,

        @SerializedName("strDescriptionEN")
        var playerDescriptionEN : String?=null,

        @SerializedName("strTeam")
        var playerTeamName:String?=null,

        @SerializedName("strHeight")
        var playerHeight:String?=null,

        @SerializedName("strWeight")
        var playerWeight:String?=null,

        @SerializedName("strPosition")
        var playerPosition:String?=null,

        @SerializedName("strThumb")
        var playerThumb:String?=null,

        @SerializedName("strCutout")
        var playerCutout:String?=null

) : Parcelable