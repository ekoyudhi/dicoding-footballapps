package id.ekoyudhi.projectfinal.teams

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.*
import com.google.gson.Gson
import id.ekoyudhi.projectfinal.R
import id.ekoyudhi.projectfinal.api.ApiRepository
import id.ekoyudhi.projectfinal.model.Team
import id.ekoyudhi.projectfinal.util.getAllLeagues
import id.ekoyudhi.projectfinal.util.invisible
import id.ekoyudhi.projectfinal.util.visible
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamsFragment : Fragment(), AnkoComponent<Context>, TeamsView {

    private var teamList : MutableList<Team> = mutableListOf()
    private lateinit var spinner: Spinner
    private lateinit var swipeRefresh : SwipeRefreshLayout
    private lateinit var listEvent : RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: TeamsAdapter
    private lateinit var presenter: TeamsPresenter
    private var leagueId : String = "4328"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val request = ApiRepository()
        val gson = Gson()

        val spinnerItems : LinkedHashMap<String, String> = getAllLeagues()
        val spinnerAdapter = ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems.keys.toList())
        spinner.adapter = spinnerAdapter

        adapter = TeamsAdapter(teamList) {
            ctx.startActivity<TeamsDetailScrollActivity>("te" to it)
        }

        listEvent.adapter = adapter
        presenter = TeamsPresenter(this,request,gson)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueId = spinnerItems.getValue(spinner.selectedItem.toString())
                presenter.getAllTeams(leagueId)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            presenter.getAllTeams(leagueId)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return createView(AnkoContext.create(ctx))
    }
    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            id = R.id.teams_frag
            linearLayout {
                lparams (width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(2)
                leftPadding = dip(2)
                rightPadding = dip(2)
                bottomPadding = dip(2)

                spinner = spinner()
                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout{
                        lparams (width = matchParent, height = wrapContent)

                        listEvent = recyclerView {
                            id = R.id.list_event
                            lparams (width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }

                        progressBar = progressBar {
                        }.lparams{
                            centerHorizontally()
                        }
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>?) {
        swipeRefresh.isRefreshing = false
        teamList.clear()
        data?.let { teamList.addAll(it) }
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search_menu, menu)
        val mSearch = menu.findItem(R.id.action_search) as MenuItem
        val mSearchView = mSearch.actionView as SearchView
        mSearchView.queryHint = getString(R.string.search_hint)

        mSearch.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                val teamsFrag = find<LinearLayout>(R.id.teams_frag)
                val ll = teamsFrag.getChildAt(0) as LinearLayout
                ll.removeView(spinner)
                swipeRefresh.isEnabled = false
                progressBar.invisible()
                //presenter.getAllTeamsList()

                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                activity!!.bottom_navigation.selectedItemId = R.id.nav_teams
                return true
            }

        })

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //presenter.getTeamSearchList(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.isNotEmpty())
                    presenter.getTeamSearchList(newText)

                return true
            }

        })
    }
}