package id.ekoyudhi.projectfinal.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import id.ekoyudhi.projectfinal.R
import id.ekoyudhi.projectfinal.R.layout.activity_main
import id.ekoyudhi.projectfinal.R.id.nav_teams
import id.ekoyudhi.projectfinal.R.id.nav_matches
import id.ekoyudhi.projectfinal.R.id.nav_favorites
import id.ekoyudhi.projectfinal.favorites.FavoritesFragment
import id.ekoyudhi.projectfinal.matches.MatchesFragment
import id.ekoyudhi.projectfinal.teams.TeamsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                nav_teams -> {
                    loadFragment(savedInstanceState, R.id.main_container, TeamsFragment(), TeamsFragment::class.java.simpleName)
                }
                nav_matches -> {
                    loadFragment(savedInstanceState, R.id.main_container, MatchesFragment(), MatchesFragment::class.java.simpleName)
                }
                nav_favorites -> {
                    loadFragment(savedInstanceState, R.id.main_container, FavoritesFragment(), FavoritesFragment::class.java.simpleName)
                }
            }
            true
        }

        bottom_navigation.selectedItemId = nav_matches
    }

    private fun loadFragment(savedInstanceState: Bundle?, mContainViewId : Int, mFragment : Fragment, mTag : String) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(mContainViewId, mFragment, mTag)
                    .commit()
        }
    }
}
