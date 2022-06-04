package id.ekoyudhi.projectfinal.teams

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
import id.ekoyudhi.projectfinal.model.Team
import org.jetbrains.anko.*
import id.ekoyudhi.projectfinal.R.id.img_team
import id.ekoyudhi.projectfinal.R.id.name_team

class TeamsAdapter(private val teamList : List<Team>, private val listener : (Team) -> Unit)
    : RecyclerView.Adapter<TeamsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = teamList.size

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindItem(teamList[position], listener)
    }

}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width= matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(5)

                    imageView {
                        id = img_team
                    }.lparams {
                        width = dip(65)
                        height = dip(65)
                        alignParentStart()
                        alignParentTop()
                        marginStart = dip(20)
                    }

                    textView {
                        id = name_team
                        textSize = 18F
                        typeface = Typeface.DEFAULT_BOLD
                        maxLines = 1
                        ellipsize = TextUtils.TruncateAt.END
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams {
                        width = matchParent
                        height = dip(65)
                        marginStart = dip(20)
                        endOf(img_team)
                    }
                }
            }
        }
    }
}

class TeamsViewHolder (view: View) : RecyclerView.ViewHolder(view){

    private val imgTeam : ImageView = view.findViewById(img_team)
    private val nameTeam : TextView = view.findViewById(name_team)

    fun bindItem(team : Team, listener: (Team) -> Unit) {
        nameTeam.text = team.teamName
        Picasso.get().load(Uri.parse(team.teamBadge)).into(imgTeam)
        itemView.setOnClickListener { listener(team)}
    }
}
