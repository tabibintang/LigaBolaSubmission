package id.my.tabin.ligabola.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import id.my.tabin.ligabola.R
import id.my.tabin.ligabola.fragment.FavouriteEventFragment
import id.my.tabin.ligabola.fragment.FavouriteTeamFragment
import id.my.tabin.ligabola.fragment.LeagueListFragment
import id.my.tabin.ligabola.model.League
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger

class MainActivity : AppCompatActivity(), AnkoLogger {
    private var items: MutableList<League> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        //showRecyclerList()

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.league -> {
                    val toast =
                        Toast.makeText(applicationContext, "List League", Toast.LENGTH_SHORT)
                    toast.show()
                    loadLeagueFragment(savedInstanceState)
                }
                R.id.favorite_event -> {
                    val toast =
                        Toast.makeText(
                            applicationContext,
                            "List Favourite Event",
                            Toast.LENGTH_SHORT
                        )
                    toast.show()
                    loadFavoriteEventFragment(savedInstanceState)
                }
                R.id.favorite_team -> {
                    val toast =
                        Toast.makeText(
                            applicationContext,
                            "List Favourite Team",
                            Toast.LENGTH_SHORT
                        )
                    toast.show()
                    loadFavoriteTeamFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.league
    }

    private fun initData() {
        val id = resources.getStringArray(R.array.league_id)
        val name = resources.getStringArray(R.array.league_name)
        val image = resources.obtainTypedArray(R.array.league_image)
        val description = resources.getStringArray(R.array.league_description)
        items.clear()
        for (i in name.indices) {
            items.add(
                League(
                    id[i],
                    name[i],
                    image.getResourceId(i, 0),
                    null,
                    description[i]
                )
            )
        }
        image.recycle()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun loadLeagueFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    LeagueListFragment(),
                    LeagueListFragment::class.java.simpleName
                )
                .commit()
        }
    }

    private fun loadFavoriteEventFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    FavouriteEventFragment(),
                    FavouriteEventFragment::class.java.simpleName
                )
                .commit()
        }
    }

    private fun loadFavoriteTeamFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    FavouriteTeamFragment(),
                    FavouriteTeamFragment::class.java.simpleName
                )
                .commit()
        }
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
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
