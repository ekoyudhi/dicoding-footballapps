package id.ekoyudhi.projectfinal.teams

import com.google.gson.Gson
import id.ekoyudhi.projectfinal.api.ApiRepository
import id.ekoyudhi.projectfinal.api.TheSportDBApi
import id.ekoyudhi.projectfinal.model.Team
import id.ekoyudhi.projectfinal.model.TeamResponse
import id.ekoyudhi.projectfinal.util.CoroutineContextProvider
import id.ekoyudhi.projectfinal.util.getAllLeagues
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamsPresenter(private val view : TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context : CoroutineContextProvider = CoroutineContextProvider())
{
    fun getAllTeams(leagueId : String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getAllTeamsLeague(leagueId)),
                        TeamResponse::class.java)
            }
            //data.await().teams?.let { view.showTeamList(it) }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
        /*
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getAllTeamsLeague(leagueId)),
                    TeamResponse::class.java)
            uiThread {
                view.showTeamList(data.teams)
                view.hideLoading()
            }
        }
        */
    }

    fun getTeamSearchList(teamName : String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamSearch(teamName)),
                        TeamResponse::class.java)
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }
}