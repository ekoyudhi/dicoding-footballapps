package id.ekoyudhi.projectfinal.favorites

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ekoyudhi.projectfinal.R

class FavoritesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.favorites_fragment, container, false)
        val viewPager : ViewPager = view.findViewById(R.id.view_pager_fav)

        val adapter = Adapter(childFragmentManager)
        adapter.add(MatchesFavFragment(), "MATCHES")
        adapter.add(TeamsFavFragment(), "TEAMS")
        viewPager.adapter = adapter

        val tabs : TabLayout = view.findViewById(R.id.tabs_fav)
        tabs.setupWithViewPager(viewPager)
        tabs.tabGravity = TabLayout.GRAVITY_FILL

        return view
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
}