package id.ekoyudhi.projectfinal.teams

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.ekoyudhi.projectfinal.R
import id.ekoyudhi.projectfinal.model.Player
import id.ekoyudhi.projectfinal.R.id.*
import id.ekoyudhi.projectfinal.api.ApiRepository
import id.ekoyudhi.projectfinal.api.TheSportDBApi
import id.ekoyudhi.projectfinal.model.PlayerResponse
import id.ekoyudhi.projectfinal.model.Team
import id.ekoyudhi.projectfinal.player.PlayerDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx

class PlayerTeamsFragment : Fragment(), AnkoComponent<Context>, PlayerView {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlayerAdapter
    private lateinit var presenter: PlayerPresenter
    private var playerList : MutableList<Player> = mutableListOf()
    private lateinit var team : Team

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val request = ApiRepository()
        val gson = Gson()

        adapter = PlayerAdapter(playerList) {
            ctx.startActivity<PlayerDetailActivity>("pl" to it)
        }

        recyclerView.adapter = adapter
        presenter = PlayerPresenter(this, request,gson)
        presenter.getPlayerDetail(team.teamId!!)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val activity = activity as TeamsDetailScrollActivity
        team = activity.getTeam()
        return createView(AnkoContext.create(ctx))
    }
    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            padding = dip(4)

            recyclerView = recyclerView {
                id = list_event_players
                lparams (width = matchParent, height = wrapContent)
                layoutManager = LinearLayoutManager(ctx)
            }
        }
    }

    override fun showPlayerList(data: List<Player>) {
        playerList.clear()
        playerList.addAll(data)
        adapter.notifyDataSetChanged()
    }

}

interface PlayerView {
    fun showPlayerList(data : List<Player>)
}

class PlayerAdapter(private val playerList : List<Player>, private val listener : (Player) -> Unit)
    : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerAdapter.PlayerViewHolder {
        return PlayerViewHolder(PlayerUI().createView(AnkoContext.create(parent.context,parent)))
    }

    override fun getItemCount(): Int = playerList.size

    override fun onBindViewHolder(holder: PlayerAdapter.PlayerViewHolder, position: Int) {
        holder.bindItem(playerList[position], listener)
    }

    class PlayerViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        private val imgDetail : ImageView = view.findViewById(img_player_frag)
        private val nameDetail : TextView = view.findViewById(name_player_frag)
        private val posDetail : TextView = view.findViewById(pos_player_frag)

        fun bindItem(player : Player, listener: (Player) -> Unit) {
            nameDetail.text = player.playerName
            posDetail.text = player.playerPosition
            if (!player.playerCutout.isNullOrEmpty() || !player.playerCutout.isNullOrBlank())
                Picasso.get().load(Uri.parse(player.playerCutout)).into(imgDetail)
            else
                Picasso.get().load(R.mipmap.ic_launcher).into(imgDetail)
            itemView.setOnClickListener { listener(player)}
        }
    }
}

class PlayerUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(6)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(10)

                    imageView {
                        id = img_player_frag
                    }.lparams {
                        width = dip(65)
                        height = dip(65)
                    }

                    textView {
                        id = name_player_frag
                        gravity = Gravity.CENTER_VERTICAL
                        textSize = 16f
                        ellipsize = TextUtils.TruncateAt.END
                        maxLines = 1
                    }.lparams {
                        width = wrapContent
                        height = dip(65)
                        centerVertically()
                        endOf(img_player_frag)
                    }

                    textView {
                        id = pos_player_frag
                        gravity = Gravity.CENTER_VERTICAL
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_END
                        textSize = 12f
                        ellipsize = TextUtils.TruncateAt.END
                        maxLines = 1
                    }.lparams {
                        width = dip(75)
                        height = dip (65)
                        alignParentTop()
                        alignParentEnd()

                    }
                }
            }
        }
    }

}

class PlayerPresenter(private val view : PlayerView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson)
{

    fun getPlayerDetail(teamId : String) {
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getAllPlayer(teamId)),
                    PlayerResponse::class.java)

            uiThread {
                view.showPlayerList(data.player)
            }
        }
    }
}