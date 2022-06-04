package id.ekoyudhi.projectfinal.favorites

import android.graphics.Typeface
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import id.ekoyudhi.projectfinal.R
import id.ekoyudhi.projectfinal.model.FavTeams

class TeamsFavAdapter(private val favTeamsList : List<FavTeams>, private val listener : (FavTeams) -> Unit)
    : RecyclerView.Adapter<TeamsFavAdapter.TeamsFavViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsFavViewHolder {
        return TeamsFavViewHolder(TeamsFavUI().createView(AnkoContext.create(parent.context,parent)))
    }

    override fun getItemCount(): Int = favTeamsList.size

    override fun onBindViewHolder(holder: TeamsFavViewHolder, position: Int) {
        holder.bindItem(favTeamsList[position], listener)
    }


    class TeamsFavViewHolder (view : View) : RecyclerView.ViewHolder(view) {

        private val imgTeam : ImageView = view.findViewById(R.id.img_team)
        private val nameTeam : TextView = view.findViewById(R.id.name_team)

        fun bindItem(favTeams: FavTeams, listener: (FavTeams) -> Unit) {

            nameTeam.text = favTeams.teamName
            Picasso.get().load(Uri.parse(favTeams.teamBadge)).into(imgTeam)
            itemView.setOnClickListener { listener(favTeams)}
        }

    }

}

class TeamsFavUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width= matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(5)

                    imageView {
                        id = R.id.img_team
                    }.lparams {
                        width = dip(65)
                        height = dip(65)
                        alignParentStart()
                        alignParentTop()
                        marginStart = dip(20)
                    }

                    textView {
                        id = R.id.name_team
                        textSize = 18F
                        typeface = Typeface.DEFAULT_BOLD
                        maxLines = 1
                        ellipsize = TextUtils.TruncateAt.END
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams {
                        width = matchParent
                        height = dip(65)
                        marginStart = dip(20)
                        endOf(R.id.img_team)
                    }
                }
            }
        }
    }
}