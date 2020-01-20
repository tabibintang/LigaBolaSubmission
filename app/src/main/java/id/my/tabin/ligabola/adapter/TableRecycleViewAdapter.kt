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
import id.my.tabin.ligabola.model.Table
import org.jetbrains.anko.find

class TableRecycleViewAdapter(
    private val tables: List<Table>,
    val context: Context,
    private val listener: (Table) -> Unit
) : RecyclerView.Adapter<StandingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingViewHolder {
        return StandingViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.standing_list, parent, false)
        )
    }

    override fun getItemCount(): Int = tables.size

    override fun onBindViewHolder(holder: StandingViewHolder, position: Int) {
        holder.bindItem(tables[position], listener)
    }
}

class StandingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val imageTeam: ImageView = view.find(R.id.image_team_standing)
    private val textName: TextView = view.find(R.id.text_name_standing)
    private val textWin: TextView = view.find(R.id.text_win_standing)
    private val textDraw: TextView = view.find(R.id.text_draw_standing)
    private val textLoss: TextView = view.find(R.id.text_loss_standing)
    fun bindItem(tables: Table, listener: (Table) -> Unit) {
        Picasso.get().load(tables.teamBadge).fit().into(imageTeam)
        textName.text = tables.teamName
        textWin.text = "Win : " + tables.win
        textDraw.text = "Draw : " + tables.draw
        textLoss.text = "Loss : " + tables.loss
        itemView.setOnClickListener {
            listener(tables)
        }
    }
}