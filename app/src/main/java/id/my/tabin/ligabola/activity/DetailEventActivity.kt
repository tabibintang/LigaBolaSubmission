package id.my.tabin.ligabola.activity

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.my.tabin.ligabola.R
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.helper.database
import id.my.tabin.ligabola.model.Event
import id.my.tabin.ligabola.model.Favourite
import id.my.tabin.ligabola.presenter.MatchDetailPresenter
import id.my.tabin.ligabola.support.invisible
import id.my.tabin.ligabola.support.visible
import id.my.tabin.ligabola.view.MatchDetailView
import kotlinx.android.synthetic.main.activity_detail_event.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailEventActivity : AppCompatActivity(),
    MatchDetailView {
    private var menuItem: Menu? = null
    private var isFavourite: Boolean = false
    private lateinit var idEvent: String
    private lateinit var presenter: MatchDetailPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        val intent = intent
        idEvent = intent.getStringExtra("id_match")

        favouriteState()

        val request = ApiRepository()
        val gson = Gson()

        presenter = MatchDetailPresenter(
            this,
            request,
            gson
        )
        presenter.getMatchDetail(idEvent)
    }

    override fun showLoading() {
        progress_bar_detail_match.visible()
    }

    override fun hideLoading() {
        progress_bar_detail_match.invisible()
    }

    override fun showMatchDetail(data: List<Event>) {
        text_date_event_detail.text = data[0].dateEventLocal
        text_time_event_detail.text = data[0].timeLocal

        text_round_detail.text = data[0].round
        data[0].homeBadge!!.let { Picasso.get().load(it).fit().into(image_team_home_detail) }
        text_score_home_detail.text = data[0].homeScore
        text_team_home_detail.text = data[0].homeTeam
        text_keeper_home_detail.text = data[0].homeKeeper
        text_defend_home_detail.text = data[0].homeDefense
        text_midfield_home_detail.text = data[0].homeMidField
        text_forward_home_detail.text = data[0].homeForward
        text_formation_home_detail.text = data[0].homeFormation
        text_subtitute_home_detail.text = data[0].homeSubtitute
        text_goal_home_detail.text = data[0].homeGoal

        data[0].awayBadge!!.let { Picasso.get().load(it).fit().into(image_team_away_detail) }
        text_score_away_detail.text = data[0].awayScore
        text_team_away_detail.text = data[0].awayTeam
        text_keeper_away_detail.text = data[0].awayKeeper
        text_defend_away_detail.text = data[0].awayDefense
        text_midfield_away_detail.text = data[0].awayMidField
        text_forward_away_detail.text = data[0].awayForward
        text_formation_away_detail.text = data[0].awayFormation
        text_subtitute_away_detail.text = data[0].awaySubtitute
        text_goal_away_detail.text = data[0].awayGoal

        //idEvent = data[0].id
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavourite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            Toast.makeText(this, "Go to Search Page", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MatchSearchActivity::class.java)
            startActivity(intent)
            true
        }
        android.R.id.home -> {
            finish()
            true
        }
        R.id.add_to_favourite -> {
            if (isFavourite) removeFromFavourite() else addToFavourite()

            isFavourite = !isFavourite
            setFavourite()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavourite() {
        try {
            database.use {
                insert(
                    Favourite.TABLE_FAVOURITE,
                    Favourite.ID_EVENT to idEvent
                )
            }
            Toast.makeText(this, "Added to Favourite", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {

            Toast.makeText(this, "Failed add favourite : " + e.localizedMessage, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun removeFromFavourite() {
        try {
            database.use {
                delete(
                    Favourite.TABLE_FAVOURITE, "(ID_EVENT = {id})",
                    "id" to idEvent
                )
            }
            Toast.makeText(this, "Removed from Favourite", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(this, "Failed add favourite : " + e.localizedMessage, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setFavourite() {
        if (isFavourite)
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_added_to_favourites)
        else
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_add_to_favourites)
    }

    private fun favouriteState() {
        database.use {
            val result = select(Favourite.TABLE_FAVOURITE)
                .whereArgs(
                    "(ID_EVENT = {id})",
                    "id" to idEvent
                )
            val favorite = result.parseList(classParser<Favourite>())
            if (!favorite.isEmpty()) isFavourite = true
            setFavourite()
        }
    }
}
