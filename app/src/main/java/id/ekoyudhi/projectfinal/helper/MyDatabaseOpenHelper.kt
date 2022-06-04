package id.ekoyudhi.projectfinal.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.ekoyudhi.projectfinal.model.FavMatches
import id.ekoyudhi.projectfinal.model.FavTeams
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorites.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavMatches.TABLE_MATCHES_FAV, true,
                FavMatches.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavMatches.EVENT_ID to TEXT,
                FavMatches.EVENT_DATE to TEXT,
                FavMatches.EVENT_TIME to TEXT,
                FavMatches.HOME_TEAM_NAME to TEXT,
                FavMatches.HOME_SCORE to TEXT,
                FavMatches.AWAY_TEAM_NAME to TEXT,
                FavMatches.AWAY_SCORE to TEXT,
                FavMatches.HOME_GOAL_DETAILS to TEXT,
                FavMatches.HOME_RED_CARDS to TEXT,
                FavMatches.HOME_YELLOW_CARDS to TEXT,
                FavMatches.HOME_LINEUP_GOALKEEPER to TEXT,
                FavMatches.HOME_LINEUP_DEFENSE to TEXT,
                FavMatches.HOME_LINEUP_MIDFIELD to TEXT,
                FavMatches.HOME_LINEUP_FORWARD to TEXT,
                FavMatches.HOME_LINEUP_SUBSTITUTES to TEXT,
                FavMatches.HOME_FORMATION to TEXT,
                FavMatches.AWAY_GOAL_DETAILS to TEXT,
                FavMatches.AWAY_RED_CARDS to TEXT,
                FavMatches.AWAY_YELLOW_CARDS to TEXT,
                FavMatches.AWAY_LINEUP_GOALKEEPER to TEXT,
                FavMatches.AWAY_LINEUP_DEFENSE to TEXT,
                FavMatches.AWAY_LINEUP_MIDFIELD to TEXT,
                FavMatches.AWAY_LINEUP_FORWARD to TEXT,
                FavMatches.AWAY_LINEUP_SUBSTITUTES to TEXT,
                FavMatches.AWAY_FORMATION to TEXT,
                FavMatches.HOME_SHOTS to TEXT,
                FavMatches.AWAY_SHOTS to TEXT,
                FavMatches.HOME_TEAM_ID to TEXT,
                FavMatches.AWAY_TEAM_ID to TEXT)

        db.createTable(FavTeams.TABLE_TEAMS_FAV, true,
                FavTeams.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavTeams.TEAM_ID to TEXT,
                FavTeams.TEAM_NAME to TEXT,
                FavTeams.TEAM_FORMED_YEAR to TEXT,
                FavTeams.TEAM_STADIUM to TEXT,
                FavTeams.TEAM_DESCRIPTION_EN to TEXT,
                FavTeams.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavMatches.TABLE_MATCHES_FAV, true)
    }
}

val Context.database : MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)