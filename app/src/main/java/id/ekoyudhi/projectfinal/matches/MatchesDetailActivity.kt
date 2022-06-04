package id.ekoyudhi.projectfinal.matches

import android.database.sqlite.SQLiteConstraintException
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.ekoyudhi.projectfinal.R
import id.ekoyudhi.projectfinal.R.drawable.ic_add_to_favorites
import id.ekoyudhi.projectfinal.R.drawable.ic_added_to_favorites
import id.ekoyudhi.projectfinal.R.layout.activity_match_detail
import id.ekoyudhi.projectfinal.R.menu.detail_menu
import id.ekoyudhi.projectfinal.api.ApiRepository
import id.ekoyudhi.projectfinal.api.TheSportDBApi
import id.ekoyudhi.projectfinal.helper.database
import id.ekoyudhi.projectfinal.model.EventLeague
import id.ekoyudhi.projectfinal.model.FavMatches
import id.ekoyudhi.projectfinal.model.TeamResponse
import id.ekoyudhi.projectfinal.util.CoroutineContextProvider
import id.ekoyudhi.projectfinal.util.replaceSemiColonWithNewline
import id.ekoyudhi.projectfinal.util.toGMTFormat
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.text.SimpleDateFormat
import java.util.*

class MatchesDetailActivity : AppCompatActivity() {

    private var menuItem: Menu? = null
    private lateinit var eventLeague: EventLeague
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_match_detail)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.extras
        eventLeague = data.getParcelable("el") as EventLeague

        favoriteMatchesState()

        val date : Date = toGMTFormat(eventLeague.eventDate!!, eventLeague.eventTime!!)//convertToDate(eventLeague.eventDate!!, eventLeague.eventTime!!)

        det_event_date.text = SimpleDateFormat("E, dd MMM yyyy").format(date)//fullDateString(eventLeague.eventDate!!)
        //det_event_date.text = fullDateString(eventLeague.eventDate!!)
        det_event_time.text = SimpleDateFormat("HH:mm").format(date)//eventLeague.eventTime
        //det_event_time.text = eventLeague.eventTime
        det_home_score.text = eventLeague.homeScore
        det_home_team.text = eventLeague.homeTeamName
        det_away_score.text = eventLeague.awayScore
        det_away_team.text = eventLeague.awayTeamName
        home_formation.text = eventLeague.homeFormation
        away_formation.text = eventLeague.awayFormation

        home_goals.text = replaceSemiColonWithNewline(eventLeague.homeGoalDetails)
        away_goals.text = replaceSemiColonWithNewline(eventLeague.awayGoalDetails)

        home_shots.text = eventLeague.homeShots
        away_shots.text = eventLeague.awayShots

        home_goalkeeper.text = replaceSemiColonWithNewline(eventLeague.homeLineupGoalkeeper)
        away_goalkeeper.text = replaceSemiColonWithNewline(eventLeague.awayLineupGoalkeeper)

        home_defense.text = replaceSemiColonWithNewline(eventLeague.homeLineupDefense)
        away_defense.text = replaceSemiColonWithNewline(eventLeague.awayLineupDefense)

        home_midfield.text = replaceSemiColonWithNewline(eventLeague.homeLineupMidfield)
        away_midfield.text = replaceSemiColonWithNewline(eventLeague.awayLineupMidfield)

        home_forward.text = replaceSemiColonWithNewline(eventLeague.homeLineupForward)
        away_forward.text = replaceSemiColonWithNewline(eventLeague.awayLineupForward)

        home_substitutes.text = replaceSemiColonWithNewline(eventLeague.homeLineupSubstitutes)
        away_substitutes.text = replaceSemiColonWithNewline(eventLeague.awayLineupSubstitutes)

        val gson = Gson()
        val apiRepository = ApiRepository()
        val ctx = CoroutineContextProvider()
        launch(ctx.main) {
            val dataHome = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(eventLeague.homeTeamId)),
                        TeamResponse::class.java)
            }
            Picasso.get().load(Uri.parse(dataHome.await().teams?.get(0)?.teamBadge)).into(home_badge)
        }
        launch(ctx.main) {
            val dataAway = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(eventLeague.awayTeamId)),
                        TeamResponse::class.java)
            }
            Picasso.get().load(Uri.parse(dataAway.await().teams?.get(0)?.teamBadge)).into(away_badge)
        }


    }

    private fun favoriteMatchesState() {
        database.use {
            val result = select(FavMatches.TABLE_MATCHES_FAV).whereArgs("EVENT_ID = {id}", "id" to eventLeague.eventId!!)
            val favorite = result.parseList(classParser<FavMatches>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setMatchesFavorites()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorites -> {
                if (isFavorite) removeMatchesFromFavorite() else addMatchesToFavorite()

                isFavorite = !isFavorite
                setMatchesFavorites()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addMatchesToFavorite() {
        try {
            database.use {
                insert(FavMatches.TABLE_MATCHES_FAV,
                        FavMatches.EVENT_ID to eventLeague.eventId,
                        FavMatches.EVENT_DATE to eventLeague.eventDate,
                        FavMatches.EVENT_TIME to eventLeague.eventTime,
                        FavMatches.HOME_FORMATION to eventLeague.homeFormation,
                        FavMatches.HOME_GOAL_DETAILS to eventLeague.homeGoalDetails,
                        FavMatches.HOME_LINEUP_DEFENSE to eventLeague.homeLineupDefense,
                        FavMatches.HOME_LINEUP_FORWARD to eventLeague.homeLineupForward,
                        FavMatches.HOME_LINEUP_GOALKEEPER to eventLeague.homeLineupGoalkeeper,
                        FavMatches.HOME_LINEUP_MIDFIELD to eventLeague.homeLineupMidfield,
                        FavMatches.HOME_LINEUP_SUBSTITUTES to eventLeague.homeLineupSubstitutes,
                        FavMatches.HOME_RED_CARDS to eventLeague.homeRedCards,
                        FavMatches.HOME_SCORE to eventLeague.homeScore,
                        FavMatches.HOME_SHOTS to eventLeague.homeShots,
                        FavMatches.HOME_TEAM_ID to eventLeague.homeTeamId,
                        FavMatches.HOME_TEAM_NAME to eventLeague.homeTeamName,
                        FavMatches.HOME_YELLOW_CARDS to eventLeague.homeYellowCards,
                        FavMatches.AWAY_FORMATION to eventLeague.awayFormation,
                        FavMatches.AWAY_GOAL_DETAILS to eventLeague.awayGoalDetails,
                        FavMatches.AWAY_LINEUP_DEFENSE to eventLeague.awayLineupDefense,
                        FavMatches.AWAY_LINEUP_FORWARD to eventLeague.awayLineupForward,
                        FavMatches.AWAY_LINEUP_GOALKEEPER to eventLeague.awayLineupGoalkeeper,
                        FavMatches.AWAY_LINEUP_MIDFIELD to eventLeague.awayLineupMidfield,
                        FavMatches.AWAY_LINEUP_SUBSTITUTES to eventLeague.awayLineupSubstitutes,
                        FavMatches.AWAY_RED_CARDS to eventLeague.awayRedCards,
                        FavMatches.AWAY_SCORE to eventLeague.awayScore,
                        FavMatches.AWAY_SHOTS to eventLeague.awayShots,
                        FavMatches.AWAY_TEAM_ID to eventLeague.awayTeamId,
                        FavMatches.AWAY_TEAM_NAME to eventLeague.awayTeamName,
                        FavMatches.AWAY_YELLOW_CARDS to eventLeague.awayYellowCards)
            }
            Snackbar.make(matches_detail_layout, "Added to favorites", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(matches_detail_layout, e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun removeMatchesFromFavorite() {
        try {
            database.use {
                delete(FavMatches.TABLE_MATCHES_FAV, "EVENT_ID = {id}", "id" to (eventLeague.eventId!!))
            }
            Snackbar.make(matches_detail_layout, "Removed from favorites", Snackbar.LENGTH_SHORT).show()
        } catch (e : SQLiteConstraintException) {
            Snackbar.make(matches_detail_layout,e.localizedMessage,Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setMatchesFavorites() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}