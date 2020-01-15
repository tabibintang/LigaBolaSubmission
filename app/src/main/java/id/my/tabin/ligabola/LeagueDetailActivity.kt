package id.my.tabin.ligabola

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_league_detail.*

class LeagueDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_detail)

        val intent = intent
        val league = intent.getParcelableExtra<League>("league")

        viewpager_container.adapter = LeagueDetailPagerAdapter(supportFragmentManager,league)
        tabs_league_detail.setupWithViewPager(viewpager_container)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            Toast.makeText(this, "Go to Search Page", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MatchSearchActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
