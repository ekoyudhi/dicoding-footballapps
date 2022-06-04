package id.ekoyudhi.projectfinal.matches

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.google.gson.Gson
import id.ekoyudhi.projectfinal.R.color.colorAccent
import id.ekoyudhi.projectfinal.R.id.*
import id.ekoyudhi.projectfinal.api.ApiRepository
import id.ekoyudhi.projectfinal.model.EventLeague
import id.ekoyudhi.projectfinal.util.getAllLeagues
import id.ekoyudhi.projectfinal.util.invisible
import id.ekoyudhi.projectfinal.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*

class NextMatchesFragment : Fragment(), AnkoComponent<Context>, MatchesView {

    private var eventLeagueList : MutableList<EventLeague> = mutableListOf()
    private lateinit var swipeRefresh : SwipeRefreshLayout
    private lateinit var progressBar : ProgressBar
    private lateinit var spinner : Spinner
    private lateinit var listEvent : RecyclerView
    private lateinit var adapterNext : NextMatchesAdapter
    private lateinit var presenter : MatchesPresenter
    private var leagueId : String = "4328"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val request = ApiRepository()
        val gson = Gson()

        val spinnerItems : LinkedHashMap<String, String> = getAllLeagues()
        val spinnerAdapter = ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems.keys.toList())
        spinner.adapter = spinnerAdapter

        adapterNext = NextMatchesAdapter(eventLeagueList) {
            ctx.startActivity<MatchesDetailActivity>("el" to it)
        }

        listEvent.adapter = adapterNext

        presenter = MatchesPresenter(this, request,gson)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueId = spinnerItems.getValue(spinner.selectedItem.toString())
                presenter.getNextEventLeagueList(leagueId)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        swipeRefresh.onRefresh {
            presenter.getNextEventLeagueList(leagueId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
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
            id = con_layout

            spinner = spinner()
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        /*id = R.id.listEvent*/
                        id = list_event_next
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

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventList(data: List<EventLeague>?) {
        swipeRefresh.isRefreshing = false
        eventLeagueList.clear()
        data?.let { eventLeagueList.addAll(it) }
        adapterNext.notifyDataSetChanged()
    }
}
