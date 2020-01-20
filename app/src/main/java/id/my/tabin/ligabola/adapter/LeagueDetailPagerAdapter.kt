package id.my.tabin.ligabola.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import id.my.tabin.ligabola.fragment.DetailLeagueFragment
import id.my.tabin.ligabola.fragment.NextMatchFragment
import id.my.tabin.ligabola.fragment.PrevMatchFragment
import id.my.tabin.ligabola.fragment.StandingFragment
import id.my.tabin.ligabola.model.League

class LeagueDetailPagerAdapter(fragmentManager: FragmentManager, val league: League) :
    FragmentPagerAdapter(fragmentManager) {

    private val pages = listOf(
        DetailLeagueFragment(),
        NextMatchFragment(),
        PrevMatchFragment(),
        StandingFragment()
    )

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putParcelable("league", league)

        when (position) {
            0 -> {
                val detailLeagueFragment =
                    DetailLeagueFragment()
                detailLeagueFragment.arguments = bundle
                return detailLeagueFragment
            }
            1 -> {
                val nextMatchFragment =
                    NextMatchFragment()
                nextMatchFragment.arguments = bundle
                return nextMatchFragment
            }
            2 -> {
                val prevMatchFragment =
                    PrevMatchFragment()
                prevMatchFragment.arguments = bundle
                return prevMatchFragment
            }
            3 -> {
                val standingsFragment =
                    StandingFragment()
                standingsFragment.arguments = bundle
                return standingsFragment
            }
            else -> throw Throwable()
        }
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Detail"
            1 -> "Next Match"
            2 -> "Prev Match"
            else -> "Standing"
        }
    }
}