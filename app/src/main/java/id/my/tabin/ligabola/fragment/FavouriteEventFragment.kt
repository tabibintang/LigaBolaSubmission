package id.my.tabin.ligabola.fragment


import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import id.my.tabin.ligabola.R
import id.my.tabin.ligabola.activity.DetailEventActivity
import id.my.tabin.ligabola.adapter.MatchRecyclerViewAdapter
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.helper.database
import id.my.tabin.ligabola.model.Event
import id.my.tabin.ligabola.model.Favourite
import id.my.tabin.ligabola.presenter.MatchListPresenter
import id.my.tabin.ligabola.support.invisible
import id.my.tabin.ligabola.support.visible
import id.my.tabin.ligabola.view.MatchListView
import kotlinx.android.synthetic.main.fragment_favourite_event.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

/**
 * A simple [Fragment] subclass.
 */
class FavouriteEventFragment : Fragment(), MatchListView {
    private var favourites: MutableList<Favourite> = mutableListOf()
    private lateinit var adapter: MatchRecyclerViewAdapter
    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MatchListPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = ApiRepository()
        val gson = Gson()

        recycler_favourite_list_match.layoutManager = LinearLayoutManager(context)
        adapter = MatchRecyclerViewAdapter(
            events,
            context!!
        ) {
            val toast = Toast.makeText(context, it.eventName, Toast.LENGTH_SHORT)
            toast.show()
            Log.d(ContentValues.TAG, "Click: $it.id")
            val intent = Intent(context, DetailEventActivity::class.java)
            intent.putExtra("id_match", it.id)
            startActivity(intent)
            //startActivity<DetailEventActivity>("id_match" to it.id )
        }
        recycler_favourite_list_match.adapter = adapter
        showFavourite()

        presenter = MatchListPresenter(
            this,
            request,
            gson
        )
        presenter.getMatchFavouriteList(favourites)
        swipe_refresh_layout.onRefresh {
            showFavourite()
            presenter.getMatchFavouriteList(favourites)
        }
    }

    override fun onResume() {
        super.onResume()
        showFavourite()
    }

    private fun showFavourite() {
        favourites.clear()
        context?.database?.use {
            val result = select(Favourite.TABLE_FAVOURITE)
            val favourite = result.parseList(classParser<Favourite>())
            favourites.addAll(favourite)
        }
    }

    override fun showLoading() {
        progress_bar_favourite.visible()
    }

    override fun hideLoading() {
        progress_bar_favourite.invisible()
    }

    override fun showMatchList(data: List<Event>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
        swipe_refresh_layout.isRefreshing = false
    }
}
