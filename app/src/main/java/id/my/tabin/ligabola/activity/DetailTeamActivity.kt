package id.my.tabin.ligabola.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import id.my.tabin.ligabola.R
import id.my.tabin.ligabola.adapter.TeamDetailPagerAdapter
import id.my.tabin.ligabola.model.Team
import kotlinx.android.synthetic.main.activity_detail_team.*

class DetailTeamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        val intent = intent
        val team = intent.getParcelableExtra<Team>("team")

        viewpager_team_container.adapter = TeamDetailPagerAdapter(supportFragmentManager, team)
        tabs_team_detail.setupWithViewPager(viewpager_team_container)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
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
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
