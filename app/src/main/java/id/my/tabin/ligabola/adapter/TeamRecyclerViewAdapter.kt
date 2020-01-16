package id.my.tabin.ligabola.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.my.tabin.ligabola.R
import id.my.tabin.ligabola.model.Team
import org.jetbrains.anko.*

import id.my.tabin.ligabola.R.id.team_badge
import id.my.tabin.ligabola.R.id.team_name

class TeamRecyclerViewAdapter (private val teams: List<Team>, val context: Context)
    : RecyclerView.Adapter<TeamViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        //return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
        return TeamViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.team_list, parent, false)
        )
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position])
    }
}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val teamBadge: ImageView = view.find(team_badge)
    private val teamName: TextView = view.find(team_name)
    fun bindItem(teams: Team) {
        Picasso.get().load(teams.teamBadge).fit().into(teamBadge)
        teamName.text = teams.teamName
    }
}
//
//class TeamUI : AnkoComponent<ViewGroup> {
//    override fun createView(ui: AnkoContext<ViewGroup>): View {
//        return with(ui) {
//            linearLayout {
//                lparams(width = matchParent, height = wrapContent)
//                padding = dip(16)
//                orientation = LinearLayout.HORIZONTAL
//
//                imageView {
//                    id = R.id.team_badge
//                }.lparams{
//                    height = dip(50)
//                    width = dip(50)
//                }
//
//                textView {
//                    id = R.id.team_name
//                    textSize = 16f
//                }.lparams{
//                    margin = dip(15)
//                }
//            }
//        }
//    }
//}