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
import id.ekoyudhi.projectfinal.matches.MatchesDetailActivity
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.ctx
import id.ekoyudhi.projectfinal.model.FavMatches
import id.ekoyudhi.projectfinal.util.convertFavMatchesToEventLeague
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.support.v4.onRefresh

class MatchesFavFragment : Fragment(), AnkoComponent<Context> {

    private lateinit var swipeRefresh : SwipeRefreshLayout
    private lateinit var listEvent : RecyclerView
    private lateinit var adapter : MatchesFavAdapter
    private var favMatchesList : MutableList<FavMatches> = mutableListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MatchesFavAdapter(favMatchesList) {
            ctx.startActivity<MatchesDetailActivity>("el" to convertFavMatchesToEventLeague(it))
        }

        listEvent.adapter = adapter
        showMatchesFavorites()
        swipeRefresh.onRefresh {
            favMatchesList.clear()
            showMatchesFavorites()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
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
                        id = R.id.list_event_matches
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }
        }
    }

    private fun showMatchesFavorites() {
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavMatches.TABLE_MATCHES_FAV)
            val matchesFav = result.parseList(classParser<FavMatches>())
            favMatchesList.addAll(matchesFav)
            adapter.notifyDataSetChanged()
        }
    }

}