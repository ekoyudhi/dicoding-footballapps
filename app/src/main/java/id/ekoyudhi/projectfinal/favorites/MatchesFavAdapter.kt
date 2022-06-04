package id.ekoyudhi.projectfinal.favorites

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import id.ekoyudhi.projectfinal.R
import id.ekoyudhi.projectfinal.model.FavMatches
import id.ekoyudhi.projectfinal.util.toGMTFormat
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import java.text.SimpleDateFormat
import java.util.*

class MatchesFavAdapter(private val favMatchesList : List<FavMatches>, private val listener : (FavMatches) -> Unit)
    : RecyclerView.Adapter<MatchesFavAdapter.MatchesFavViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesFavViewHolder {
        return MatchesFavViewHolder(MatchesFavUI().createView(AnkoContext.create(parent.context,parent)))
    }

    override fun getItemCount(): Int = favMatchesList.size

    override fun onBindViewHolder(holder: MatchesFavViewHolder, position: Int) {
        holder.bindItem(favMatchesList[position], listener)
    }

    class MatchesFavViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val eventDate : TextView = view.find(R.id.event_date_detail)
        private val homeScore : TextView = view.find(R.id.home_score_detail)
        private val homeTeam : TextView = view.find(R.id.home_team_detail)
        private val awayScore : TextView = view.find(R.id.away_score_detail)
        private val awayTeam : TextView = view.find(R.id.away_team_detail)
        private val eventTime : TextView = view.find(R.id.event_time_detail)

        fun bindItem(favMatches : FavMatches, listener: (FavMatches) -> Unit) {

            val date : Date = toGMTFormat(favMatches.eventDate!!, favMatches.eventTime!!)//convertToDate(favMatches.eventDate!!, favMatches.eventTime!!)
            eventDate.text = SimpleDateFormat("E, dd MMM yyyy").format(date)
            homeScore.text = favMatches.homeScore
            homeTeam.text = favMatches.homeTeamName
            awayScore.text = favMatches.awayScore
            awayTeam.text = favMatches.awayTeamName
            eventTime.text = SimpleDateFormat("HH:mm").format(date)
            itemView.setOnClickListener { listener(favMatches) }
        }
    }

}

class MatchesFavUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
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

                        textView {
                            id = R.id.event_date_detail
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textColor = R.color.colorPrimaryText
                        }.lparams {
                            width = matchParent
                            height = dip(20)
                        }

                        textView {
                            id = R.id.event_time_detail
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textColor = R.color.colorPrimaryText
                        }.lparams {
                            width = matchParent
                            height = dip(20)
                        }

                        relativeLayout {
                            lparams(width = matchParent, height = dip(40))

                            textView {
                                id = R.id.away_team_detail
                                textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
                                textSize = 16f
                                maxLines = 1
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                centerVertically()
                                endOf(R.id.away_score_detail)
                            }

                            textView {
                                id = R.id.home_team_detail
                                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_END
                                textSize = 16f
                                maxLines = 1
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                centerVertically()
                                startOf(R.id.home_score_detail)
                            }

                            textView {
                                id = R.id.away_score_detail
                                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                                textSize = 16f
                                typeface = Typeface.DEFAULT_BOLD
                                maxLines = 1
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                width = dip(30)
                                height = wrapContent
                                centerVertically()
                                endOf(R.id.vs_detail)
                            }

                            textView {
                                id = R.id.home_score_detail
                                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_END
                                textSize = 16f
                                typeface = Typeface.DEFAULT_BOLD
                                maxLines = 1
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                width = dip(30)
                                height = wrapContent
                                centerVertically()
                                startOf(R.id.vs_detail)
                            }

                            textView {
                                id = R.id.vs_detail
                                text = "vs"
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