package id.ekoyudhi.projectfinal.favorites

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import id.ekoyudhi.projectfinal.R
import id.ekoyudhi.projectfinal.helper.database
import id.ekoyudhi.projectfinal.model.FavTeams
import id.ekoyudhi.projectfinal.teams.TeamsDetailScrollActivity
import id.ekoyudhi.projectfinal.util.convertFavTeamsToTeam
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class TeamsFavFragment : Fragment(), AnkoComponent<Context> {
    private lateinit var swipeRefresh : SwipeRefreshLayout
    private lateinit var listEvent : RecyclerView
    private lateinit var adapter: TeamsFavAdapter
    private var favTeamsList : MutableList<FavTeams> = mutableListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TeamsFavAdapter(favTeamsList) {
            ctx.startActivity<TeamsDetailScrollActivity>("te" to convertFavTeamsToTeam(it))
        }

        listEvent.adapter = adapter
        showTeamsFavorites()

        swipeRefresh.onRefresh {
            favTeamsList.clear()
            showTeamsFavorites()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }
    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            linearLayout {
                lparams (width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(2)
                leftPadding = dip(2)
                rightPadding = dip(2)
                bottomPadding = dip(2)

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout{
                        lparams (width = matchParent, height = wrapContent)

                        listEvent = recyclerView {
                            id = R.id.list_event_teams
                            lparams (width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }

                    }
                }
            }
        }
    }

    private fun showTeamsFavorites() {
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavTeams.TABLE_TEAMS_FAV)
            val teamsFav = result.parseList(classParser<FavTeams>())
            favTeamsList.addAll(teamsFav)
            adapter.notifyDataSetChanged()
        }
    }
}