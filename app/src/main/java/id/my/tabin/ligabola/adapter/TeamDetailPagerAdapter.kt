package id.my.tabin.ligabola.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import id.my.tabin.ligabola.fragment.DetailTeamFragment
import id.my.tabin.ligabola.fragment.PlayerListFragment
import id.my.tabin.ligabola.model.Team

class TeamDetailPagerAdapter(fragmentManager: FragmentManager, val team: Team) :
    FragmentPagerAdapter(fragmentManager) {
    private val pages = listOf(
        DetailTeamFragment(),
        PlayerListFragment()
    )

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putParcelable("team", team)
        when (position) {
            0 -> {
                val detailTeamFragment =
                    DetailTeamFragment()
                detailTeamFragment.arguments = bundle
                return detailTeamFragment
            }
            1 -> {
                val playerListFragment =
                    PlayerListFragment()
                playerListFragment.arguments = bundle
                return playerListFragment
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
            else -> "Player"
        }
    }
}
