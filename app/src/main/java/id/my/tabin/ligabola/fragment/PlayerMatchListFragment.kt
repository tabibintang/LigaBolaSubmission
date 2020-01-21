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
import id.my.tabin.ligabola.model.Event
import id.my.tabin.ligabola.model.Team
import id.my.tabin.ligabola.presenter.MatchListPresenter
import id.my.tabin.ligabola.support.invisible
import id.my.tabin.ligabola.support.visible
import id.my.tabin.ligabola.view.MatchListView
import kotlinx.android.synthetic.main.fragment_team_nextmatch_list.*

/**
 * A simple [Fragment] subclass.
 */
class PlayerMatchListFragment : Fragment(), MatchListView {

    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MatchListPresenter
    private lateinit var adapter: MatchRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_nextmatch_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val team = arguments?.getParcelable<Team>("team")

        val request = ApiRepository()
        val gson = Gson()

        recycler_team_next_match.layoutManager = LinearLayoutManager(context)
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
        recycler_team_next_match.adapter = adapter

        presenter = MatchListPresenter(
            this,
            request,
            gson
        )
        presenter.getTeamNextMatchList(team?.teamId)

    }

    override fun showLoading() {
        progress_bar_team_next.visible()
    }

    override fun hideLoading() {
        if (progress_bar_team_next != null) {
            progress_bar_team_next.invisible()
        }
    }

    override fun showMatchList(data: List<Event>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
