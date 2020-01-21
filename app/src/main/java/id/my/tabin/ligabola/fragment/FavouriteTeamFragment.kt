package id.my.tabin.ligabola.fragment


import android.content.Intent
import android.os.Bundle
import android.support.constraint.Constraints.TAG
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson

import id.my.tabin.ligabola.R
import id.my.tabin.ligabola.activity.DetailTeamActivity
import id.my.tabin.ligabola.adapter.TeamRecyclerViewAdapter
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.helper.database
import id.my.tabin.ligabola.model.FavouriteTeam
import id.my.tabin.ligabola.model.Team
import id.my.tabin.ligabola.presenter.TeamListPresenter
import id.my.tabin.ligabola.support.invisible
import id.my.tabin.ligabola.support.visible
import id.my.tabin.ligabola.view.TeamListView
import kotlinx.android.synthetic.main.fragment_favourite_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

/**
 * A simple [Fragment] subclass.
 */
class FavouriteTeamFragment : Fragment(), TeamListView {
    private var favourites: MutableList<FavouriteTeam> = mutableListOf()
    private lateinit var adapter: TeamRecyclerViewAdapter
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamListPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = ApiRepository()
        val gson = Gson()

        recycler_favourite_team.layoutManager = LinearLayoutManager(context)
        showFavourite()
        adapter = TeamRecyclerViewAdapter(
            teams,
            context!!
        ) {
            val toast = Toast.makeText(context, it.teamName, Toast.LENGTH_SHORT)
            toast.show()
            Log.d(TAG, "Click: $it.id")
            val intent = Intent(context, DetailTeamActivity::class.java)
            intent.putExtra("team", it)
            startActivity(intent)
        }
        recycler_favourite_team.adapter = adapter

        presenter = TeamListPresenter(
            this,
            request,
            gson
        )
        presenter.getFavouriteTeamList(favourites)
        swipe_refresh_team_favourite_layout.onRefresh {
            showFavourite()
            presenter.getFavouriteTeamList(favourites)
        }
    }

    override fun onResume() {
        super.onResume()
        showFavourite()
    }

    private fun showFavourite() {
        favourites.clear()
        context?.database?.use {
            val result = select(FavouriteTeam.TABLE_FAVOURITE_TEAM)
            val favourite = result.parseList(classParser<FavouriteTeam>())
            favourites.addAll(favourite)
        }
    }

    override fun showLoading() {
        progress_bar_favourite_team.visible()
    }

    override fun hideLoading() {
        progress_bar_favourite_team.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
        swipe_refresh_team_favourite_layout.isRefreshing = false
    }
}
