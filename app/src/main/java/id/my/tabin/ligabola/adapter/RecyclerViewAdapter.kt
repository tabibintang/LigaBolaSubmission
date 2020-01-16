package id.my.tabin.ligabola.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.my.tabin.ligabola.R
import id.my.tabin.ligabola.model.League
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class RecyclerViewAdapter (private val context: Context, private val items:List<League>, private val listener: (League) -> Unit): RecyclerView.Adapter<ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TimListUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }
    override fun getItemCount(): Int = items.size
}

class TimListUI:AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        cardView {
            lparams {
                width = matchParent
                height = wrapContent
                gravity = Gravity.CENTER
                topMargin = dip(4)
                bottomMargin = dip(4)
                leftMargin = dip(8)
                rightMargin = dip(8)
            }
            relativeLayout {
                lparams(width = matchParent, height = matchParent)
                imageView(R.drawable.american_mayor_league){
                    id = R.id.team_image
                    scaleType = ImageView.ScaleType.FIT_XY
                }.lparams(
                    width = matchParent,
                    height = dip(160)
                )
                textView {
                    id = R.id.team_name
                    textSize = 15f
                }.lparams {
                    bottomOf(R.id.team_image)
                    padding = dip(5)
                }
            }
        }
    }
}

class ViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView){
    private val teamImage: ImageView = containerView.find(R.id.team_image)
    private val teamName: TextView = containerView.find(R.id.team_name)

    fun bindItem(items: League, listener: (League)-> Unit){
        teamName.text = items.name
        items.image?.let { Picasso.get().load(it).fit().into(teamImage)}
        itemView.setOnClickListener { listener(items)}
    }
}


