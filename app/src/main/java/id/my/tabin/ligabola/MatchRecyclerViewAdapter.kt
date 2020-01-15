package id.my.tabin.ligabola

import android.content.ContentValues.TAG
import android.content.Context
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity


class MatchRecyclerViewAdapter (private val events: List<Event>, val context: Context,private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<MatchViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        //return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
        return MatchViewHolder(LayoutInflater.from(context).inflate(R.layout.match_list,parent,false))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }
}

class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val imageTeamHome: ImageView = view.find(R.id.image_team_home)
    private val imageTeamAway: ImageView = view.find(R.id.image_team_away)
    private val dateEvent: TextView = view.find(R.id.text_date_event)
    private val timeEvent: TextView = view.find(R.id.text_time_event)
    private val scoreHome: TextView = view.find(R.id.text_score_home)
    private val scoreAway: TextView = view.find(R.id.text_score_away)
    private val teamHome: TextView = view.find(R.id.text_team_home)
    private val teamAway: TextView = view.find(R.id.text_team_away)
    fun bindItem(matches: Event, listener: (Event) -> Unit) {
        Picasso.get().load(matches.homeBadge).fit().into(imageTeamHome)
        Picasso.get().load(matches.awayBadge).fit().into(imageTeamAway)
        scoreHome.text = matches.homeScore
        scoreAway.text = matches.awayScore
        teamHome.text = matches.homeTeam
        teamAway.text = matches.awayTeam
        dateEvent.text = matches.dateEventLocal
        timeEvent.text = matches.timeLocal
        itemView.setOnClickListener{
            listener(matches)
        }
    }
}