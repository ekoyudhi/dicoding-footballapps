package id.ekoyudhi.projectfinal.matches

import com.google.gson.Gson
import id.ekoyudhi.projectfinal.api.ApiRepository
import id.ekoyudhi.projectfinal.api.TheSportDBApi
import id.ekoyudhi.projectfinal.model.EventLeagueResponse
import id.ekoyudhi.projectfinal.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchesPresenter(private val view: MatchesView,
                       private val apiRepository: ApiRepository,
                       private val gson : Gson,
                       private val context : CoroutineContextProvider = CoroutineContextProvider()) {
    fun getNextEventLeagueList(leagueId: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextLeagueEvents(leagueId)),
                        EventLeagueResponse::class.java)
            }
            view.showEventList(data.await().events)
            view.hideLoading()
        }
        /*
        doAsync {
            val data = gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextLeagueEvents(leagueId)),
                        EventLeagueResponse::class.java)
            uiThread {
                view.showEventList(data.events)
                view.hideLoading()
            }
        }
        */
    }

    fun getPastEventLeagueList(leagueId: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPastLeagueEvents(leagueId)),
                        EventLeagueResponse::class.java)
            }
            view.showEventList(data.await().events)
            view.hideLoading()
        }
        /*
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getPastLeagueEvents(leagueId)),
                    EventLeagueResponse::class.java)
            uiThread {
                view.showEventList(data.events)
                view.hideLoading()
            }

        }
        */
    }

    /*
    fun getAllEventList() {
        view.showLoading()
        doAsync {
            var res : MutableList<EventLeague> = mutableListOf()
            for (leagueId in getAllLeagues().values.toList()) {
                val data = gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextLeagueEvents(leagueId)),
                        EventLeagueResponse::class.java)
                res.addAll(data.events)
            }
            uiThread {
                view.showEventList(res.toList())
                view.hideLoading()
            }
        }
    }
    */
}