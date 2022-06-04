package id.ekoyudhi.projectfinal.matches

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import id.ekoyudhi.projectfinal.R
import id.ekoyudhi.projectfinal.api.ApiRepository
import id.ekoyudhi.projectfinal.api.TheSportDBApi
import id.ekoyudhi.projectfinal.model.EventLeague
import id.ekoyudhi.projectfinal.model.EventLeagueResponse
import id.ekoyudhi.projectfinal.model.MatchesResponse
import id.ekoyudhi.projectfinal.util.getAllLeagues
import id.ekoyudhi.projectfinal.util.toGMTFormat
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import java.text.SimpleDateFormat
import java.util.*

interface SearchMatchesView {
    fun showLoading()
    fun hideLoading()
    fun showSearchEventList(data : List<EventLeague>?)
}

class SearchMatchesPresenter(private val view: SearchMatchesView,
                             private val apiRepository: ApiRepository,
                             private val gson: Gson) {

    /*
    fun getAllEventList() {
        view.showLoading()
        doAsync {
            val res : MutableList<EventLeague> = mutableListOf()
            for (leagueId in getAllLeagues().values.toList()) {
                val data = gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextLeagueEvents(leagueId)),
                        EventLeagueResponse::class.java)
                res.addAll(data.events)
            }
            uiThread {
                view.showSearchEventList(res.toList())
                view.hideLoading()
            }
        }
    }
    */
    /*
    fun getNextEventLeagueList(leagueId: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getNextLeagueEvents(leagueId)),
                    EventLeagueResponse::class.java)
            uiThread {
                view.showSearchEventList(data.events)
                view.hideLoading()
            }

        }
    }*/

    fun getMatchesSearchList(teamMatch : String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getEventSearch(teamMatch)),
                    MatchesResponse::class.java)
            uiThread {
                //data.event?.let { it1 -> view.showSearchEventList(it1) }
                view.showSearchEventList(data.event)
                view.hideLoading()
            }
        }
    }
}

class SearchMatchesAdapter(private val eventLeagueList : List<EventLeague>, private val listener : (EventLeague) -> Unit)
    : RecyclerView.Adapter<SearchMatchesAdapter.SearchMatchesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMatchesViewHolder {
        return SearchMatchesViewHolder(SearchMatchesUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = eventLeagueList.size

    override fun onBindViewHolder(holder: SearchMatchesViewHolder, position: Int) {
        holder.bindItem(eventLeagueList[position], listener)
    }


    class SearchMatchesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val eventDate : TextView = view.find(R.id.event_date)
        private val homeScore : TextView = view.find(R.id.home_score)
        private val homeTeam : TextView = view.find(R.id.home_team)
        private val awayScore : TextView = view.find(R.id.away_score)
        private val awayTeam : TextView = view.find(R.id.away_team)
        private val eventTime : TextView = view.find(R.id.event_time)

        fun bindItem(eventLeague: EventLeague, listener: (EventLeague) -> Unit) {
            val date : Date? = toGMTFormat(eventLeague.eventDate!!, eventLeague.eventTime)//convertToDate(eventLeague.eventDate!!, eventLeague.eventTime!!)
            eventDate.text = SimpleDateFormat("E, dd MMM yyyy").format(date)//fullDateString(eventLeague.eventDate!!)
            //eventDate.text = fullDateString(eventLeague.eventDate!!)
            homeScore.text = eventLeague.homeScore
            homeTeam.text = eventLeague.homeTeamName
            awayScore.text = eventLeague.awayScore
            awayTeam.text = eventLeague.awayTeamName
            eventTime.text = SimpleDateFormat("HH:mm").format(date) //eventLeague.eventTime
            //eventTime.text = eventLeague.eventTime
            itemView.setOnClickListener { listener(eventLeague) }
        }
    }

    class SearchMatchesUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    topPadding = dip(2)
                    bottomPadding = dip(2)
                    leftPadding = dip(4)
                    rightPadding = dip (4)
                    orientation = LinearLayout.VERTICAL

                    cardView {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout {
                            lparams(width = matchParent, height = matchParent)
                            orientation = LinearLayout.VERTICAL

                            relativeLayout {
                                lparams(width= matchParent, height= wrapContent)

                                textView {
                                    id = R.id.event_date
                                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                                    textColor = R.color.colorPrimaryText
                                }.lparams {
                                    width = matchParent
                                    //height = dip(20)
                                    height = wrapContent
                                    centerVertically()
                                }
                            }
                            textView {
                                id = R.id.event_time
                                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                                textColor = R.color.colorPrimaryText
                            }.lparams {
                                width = matchParent
                                height = dip(20)
                            }

                            relativeLayout {
                                lparams(width = matchParent, height = dip(40))

                                textView {
                                    id = R.id.away_team
                                    textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
                                    textSize = 16f
                                    maxLines = 1
                                    ellipsize = TextUtils.TruncateAt.END
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                    centerVertically()
                                    endOf(R.id.away_score)
                                }

                                textView {
                                    id = R.id.home_team
                                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_END
                                    textSize = 16f
                                    maxLines = 1
                                    ellipsize = TextUtils.TruncateAt.END
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                    centerVertically()
                                    startOf(R.id.home_score)
                                }

                                textView {
                                    id = R.id.away_score
                                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                                    textSize = 16f
                                    typeface = Typeface.DEFAULT_BOLD
                                    maxLines = 1
                                    ellipsize = TextUtils.TruncateAt.END
                                }.lparams {
                                    width = dip(30)
                                    height = wrapContent
                                    centerVertically()
                                    endOf(R.id.vs)
                                }

                                textView {
                                    id = R.id.home_score
                                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_END
                                    textSize = 16f
                                    typeface = Typeface.DEFAULT_BOLD
                                    maxLines = 1
                                    ellipsize = TextUtils.TruncateAt.END
                                }.lparams {
                                    width = dip(30)
                                    height = wrapContent
                                    centerVertically()
                                    startOf(R.id.vs)
                                }

                                textView {
                                    id = R.id.vs
                                    textResource = R.string.title_vs
                                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                                }.lparams {
                                    width = dip(30)
                                    height = wrapContent
                                    centerInParent()
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}