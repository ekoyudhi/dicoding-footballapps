package id.ekoyudhi.projectfinal.teams

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import id.ekoyudhi.projectfinal.R.id.list_event
import id.ekoyudhi.projectfinal.R.id.overview
import id.ekoyudhi.projectfinal.model.Team
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx

class OverviewTeamsFragment : Fragment(), AnkoComponent<Context> {

    private lateinit var team : Team
    private lateinit var recyclerView: RecyclerView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val overviewList : MutableList<String> = mutableListOf()
        overviewList.add(team.teamDescriptionEN.toString())

        val adapter = OverviewAdapter(overviewList)
        recyclerView.adapter = adapter

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val activity = activity as TeamsDetailScrollActivity
        team = activity.getTeam()
        return createView(AnkoContext.create(ctx))
    }
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            padding = dip (6)


            recyclerView = recyclerView {
                id = list_event
                lparams (width = matchParent, height = wrapContent)
                layoutManager = LinearLayoutManager(ctx)
            }
            /*
            scrollView {
                lparams(width = matchParent, height = wrapContent)
                linearLayout {
                    lparams(width = matchParent, height = matchParent)

                    txtOverview = textView {
                        text = "Overview"
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }
            }
            */
        }
    }
}

class OverviewUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(4)

                textView {
                    id = overview
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }
            }
        }
    }
}

class OverviewAdapter(private val strList: List<String>)
    : RecyclerView.Adapter<OverviewAdapter.OverviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        return OverviewViewHolder(OverviewUI().createView(AnkoContext.create(parent.context,parent)))
    }

    override fun getItemCount(): Int = strList.size

    override fun onBindViewHolder(holder: OverviewViewHolder, position: Int) {
        holder.bindItem(strList[position])
    }

    class OverviewViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        private val strOverview : TextView = view.findViewById(overview)
        fun bindItem(str: String) {
            strOverview.text = str
        }
    }

}