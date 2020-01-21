package id.my.tabin.ligabola.activity

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import id.my.tabin.ligabola.R
import id.my.tabin.ligabola.adapter.TeamDetailPagerAdapter
import id.my.tabin.ligabola.helper.database
import id.my.tabin.ligabola.model.FavouriteTeam
import id.my.tabin.ligabola.model.Team
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailTeamActivity : AppCompatActivity() {
    private var menuItem: Menu? = null
    private var isFavourite: Boolean = false
    private lateinit var idTeam: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        val intent = intent
        val team = intent.getParcelableExtra<Team>("team")
        idTeam = team.teamId!!

        favouriteState()

        viewpager_team_container.adapter = TeamDetailPagerAdapter(supportFragmentManager, team)
        tabs_team_detail.setupWithViewPager(viewpager_team_container)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavourite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_search_match -> {
            Toast.makeText(this, "Go to Search Match Page", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MatchSearchActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.action_search_team -> {
            Toast.makeText(this, "Go to Search Team Page", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TeamSearchActivity::class.java)
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
                    FavouriteTeam.TABLE_FAVOURITE_TEAM,
                    FavouriteTeam.ID_TEAM to idTeam
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
                    FavouriteTeam.TABLE_FAVOURITE_TEAM, "(ID_TEAM = {id})",
                    "id" to idTeam
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
            val result = select(FavouriteTeam.TABLE_FAVOURITE_TEAM)
                .whereArgs(
                    "(ID_TEAM = {id})",
                    "id" to idTeam
                )
            val favorite = result.parseList(classParser<FavouriteTeam>())
            if (!favorite.isEmpty()) isFavourite = true
            setFavourite()
        }
    }
}
