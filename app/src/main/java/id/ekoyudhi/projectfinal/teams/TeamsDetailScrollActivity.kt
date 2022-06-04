package id.ekoyudhi.projectfinal.teams

import android.database.sqlite.SQLiteConstraintException
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.ekoyudhi.projectfinal.R
import id.ekoyudhi.projectfinal.R.drawable.ic_add_to_favorites
import id.ekoyudhi.projectfinal.R.drawable.ic_added_to_favorites
import id.ekoyudhi.projectfinal.helper.database
import id.ekoyudhi.projectfinal.model.FavTeams
import id.ekoyudhi.projectfinal.model.Team
import kotlinx.android.synthetic.main.activity_teams_detail_scroll.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class TeamsDetailScrollActivity : AppCompatActivity() {

    private var menuItem: Menu? = null
    private lateinit var team : Team
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams_detail_scroll)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.extras
        team = data.getParcelable("te") as Team

        favoriteTeamsState()

        (findViewById<TextView>(R.id.name_det_scroll)).text = team.teamName
        (findViewById<TextView>(R.id.formed_det_scroll)).text = team.teamFormedYear
        (findViewById<TextView>(R.id.stadium_det_scroll)).text = team.teamStadium
        if(!team.teamBadge.isNullOrEmpty() || !team.teamBadge.isNullOrBlank())
            Picasso.get().load(Uri.parse(team.teamBadge)).into(findViewById<ImageView>(R.id.img_det_scroll))

        val viewPager : ViewPager =  findViewById(R.id.view_pager_scroll)
        val adapter = AdapterF(supportFragmentManager)
        adapter.add(OverviewTeamsFragment(), "OVERVIEW")
        adapter.add(PlayerTeamsFragment(), "PLAYERS")
        viewPager.adapter = adapter

        val tabs : TabLayout = findViewById(R.id.tabs_scroll)
        tabs.setupWithViewPager(viewPager)
        tabs.tabGravity = TabLayout.GRAVITY_FILL
    }

    private fun favoriteTeamsState() {
        database.use {
            val result = select(FavTeams.TABLE_TEAMS_FAV).whereArgs("TEAM_ID = {id}","id" to team.teamId!!)
            val favorite = result.parseList(classParser<FavTeams>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setTeamsFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorites -> {
                if(isFavorite) removeTeamsFromFavorites() else addTeamsToFavorite()

                isFavorite = !isFavorite
                setTeamsFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getTeam() : Team = team

    private fun addTeamsToFavorite() {
        try {
            database.use {
                insert(FavTeams.TABLE_TEAMS_FAV,
                        FavTeams.TEAM_ID to team.teamId,
                        FavTeams.TEAM_BADGE to team.teamBadge,
                        FavTeams.TEAM_DESCRIPTION_EN to team.teamDescriptionEN,
                        FavTeams.TEAM_STADIUM to team.teamStadium,
                        FavTeams.TEAM_FORMED_YEAR to team.teamFormedYear,
                        FavTeams.TEAM_NAME to team.teamName)
            }
            Snackbar.make(teams_detail_layout, "Added to favorites", Snackbar.LENGTH_SHORT).show()
        } catch (e:SQLiteConstraintException) {
            Snackbar.make(teams_detail_layout, e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun removeTeamsFromFavorites() {
        try {
            database.use {
                delete(FavTeams.TABLE_TEAMS_FAV, "TEAM_ID = {id}", "id" to team.teamId!!)
            }
            Snackbar.make(teams_detail_layout, "Removed from favorites", Snackbar.LENGTH_SHORT).show()
        } catch (e:SQLiteConstraintException) {
            Snackbar.make(teams_detail_layout, e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setTeamsFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

}

class AdapterF(fm : FragmentManager) : FragmentPagerAdapter(fm) {
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




