package id.my.tabin.ligabola

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class LeagueDetailPagerAdapter (fragmentManager: FragmentManager, league: League) :FragmentPagerAdapter(fragmentManager){
    private val pages = listOf(
        DetailLeagueFragment(),
        NextMatchFragment(),
        PrevMatchFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Detail"
            1 -> "Next Match"
            else -> "Prev Match"
        }
    }
}