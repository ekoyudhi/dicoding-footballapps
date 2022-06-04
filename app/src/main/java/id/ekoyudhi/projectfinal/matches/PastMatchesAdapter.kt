package id.ekoyudhi.projectfinal.matches

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import id.ekoyudhi.projectfinal.R
import id.ekoyudhi.projectfinal.R.id.*
import id.ekoyudhi.projectfinal.model.EventLeague
import id.ekoyudhi.projectfinal.util.toGMTFormat
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import java.text.SimpleDateFormat
import java.util.*

class PastMatchesAdapter(private val evenLeagueList : List<EventLeague>, private val listener : (EventLeague) -> Unit)
    : RecyclerView.Adapter<PastMatchesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastMatchesViewHolder {
        return PastMatchesViewHolder(PastMatchesUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = evenLeagueList.size

    override fun onBindViewHolder(holder: PastMatchesViewHolder, position: Int) {
        holder.bindItem(evenLeagueList[position], listener)
    }
}

class PastMatchesUI : AnkoComponent<ViewGroup> {
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
                                id = event_date
                                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                                textColor = R.color.colorPrimaryText
                            }.lparams {
                                width = matchParent
                                //height = dip(20)
                                height = wrapContent
                                centerVertically()
                            }

                            /*
                            imageButton {
                                id = id.ekoyudhi.projectfinal.R.id.alarm_button
                                setImageResource(id.ekoyudhi.projectfinal.R.drawable.ic_alarm)
                                setBackgroundResource(id.ekoyudhi.projectfinal.R.color.cardview_shadow_end_color)
                            }.lparams {
                                marginEnd = dip(15)
                                width = wrapContent
                                height = wrapContent
                                alignParentTop()
                                alignParentEnd()
                            }
                            */
                        }
                        textView {
                            id = event_time
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textColor = R.color.colorPrimaryText
                        }.lparams {
                            width = matchParent
                            height = dip(20)
                        }

                        relativeLayout {
                            lparams(width = matchParent, height = dip(40))

                            textView {
                                id = away_team
                                textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
                                textSize = 16f
                                maxLines = 1
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                centerVertically()
                                endOf(away_score)
                            }

                            textView {
                                id = home_team
                                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_END
                                textSize = 16f
                                maxLines = 1
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                centerVertically()
                                startOf(home_score)
                            }

                            textView {
                                id = away_score
                                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                                textSize = 16f
                                typeface = Typeface.DEFAULT_BOLD
                                maxLines = 1
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                width = dip(30)
                                height = wrapContent
                                centerVertically()
                                endOf(vs)
                            }

                            textView {
                                id = home_score
                                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_END
                                textSize = 16f
                                typeface = Typeface.DEFAULT_BOLD
                                maxLines = 1
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                width = dip(30)
                                height = wrapContent
                                centerVertically()
                                startOf(vs)
                            }

                            textView {
                                id = vs
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

class PastMatchesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val eventDate : TextView = view.find(event_date)
    private val homeScore : TextView = view.find(home_score)
    private val homeTeam : TextView = view.find(home_team)
    private val awayScore : TextView = view.find(away_score)
    private val awayTeam : TextView = view.find(away_team)
    private val eventTime : TextView = view.find(event_time)

    fun bindItem(eventLeague: EventLeague, listener: (EventLeague) -> Unit) {
        val date : Date = toGMTFormat(eventLeague.eventDate!!, eventLeague.eventTime!!)//convertToDate(eventLeague.eventDate!!, eventLeague.eventTime!!)
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
