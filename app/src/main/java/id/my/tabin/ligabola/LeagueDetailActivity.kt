package id.my.tabin.ligabola

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
}
