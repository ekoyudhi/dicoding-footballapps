package id.ekoyudhi.projectfinal.matches

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.ProgressBar
import com.google.gson.Gson
import id.ekoyudhi.projectfinal.R
import id.ekoyudhi.projectfinal.R.id.action_search
import id.ekoyudhi.projectfinal.R.layout.matches_fragment
import id.ekoyudhi.projectfinal.model.EventLeague
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.matches_fragment.*
import id.ekoyudhi.projectfinal.api.ApiRepository
import id.ekoyudhi.projectfinal.util.invisible
import id.ekoyudhi.projectfinal.util.visible
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx

class MatchesFragment : Fragment(), SearchMatchesView {

    private lateinit var adapter: SearchMatchesAdapter
    private lateinit var presenter: SearchMatchesPresenter
    private var eventLeagueList : MutableList<EventLeague> = mutableListOf()
    private lateinit var listSearch : RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        listSearch = RecyclerView(ctx)
        listSearch.layoutManager = LinearLayoutManager(ctx)
        progressBar = ProgressBar(ctx)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)
        val view = inflater.inflate(matches_fragment,container, false)
        val viewPager : ViewPager = view.findViewById(R.id.view_pager)

        val adapter = Adapter(childFragmentManager)
        adapter.add(NextMatchesFragment(), "NEXT")
        adapter.add(PastMatchesFragment(), "LAST")
        viewPager.adapter = adapter

        val tabs : TabLayout = view.findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        tabs.tabGravity = TabLayout.GRAVITY_FILL

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search_menu, menu)
        val mSearch = menu.findItem(action_search) as MenuItem
        val mSearchView = mSearch.actionView as SearchView
        mSearchView.queryHint = getString(R.string.search_hint)

        mSearch.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                activity!!.matches_frag.removeAllViews()
                //
                val request = ApiRepository()
                val gson = Gson()

                adapter = SearchMatchesAdapter(eventLeagueList) {
                    ctx.startActivity<MatchesDetailActivity>("el" to it)
                }

                listSearch.adapter = adapter

                presenter = SearchMatchesPresenter(this@MatchesFragment, request, gson)
                //presenter.getNextEventLeagueList("4328")
                //presenter.getAllEventList()
                activity!!.matches_frag.addView(listSearch)
                activity!!.matches_frag.addView(progressBar)
                progressBar.invisible()
                //
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                activity!!.bottom_navigation.selectedItemId = R.id.nav_matches
                return true
            }

        })

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //presenter.getMatchesSearchList(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty())
                    presenter.getMatchesSearchList(newText)

                return true
            }

        })
    }

    override fun showSearchEventList(data: List<EventLeague>?) {
        eventLeagueList.clear()
        data?.let { it -> eventLeagueList.addAll(it.filter { it.sportName!!.contains("Soccer", true)}) }
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

}

class Adapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    private val tabNames : ArrayList<String> = ArrayList()
    private val fragments : ArrayList<Fragment> = ArrayList()

    fun add(fragment: Fragment, title : String) {
        tabNames.add(title)
        fragments.add(fragment)
    }
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabNames[position]
    }

}