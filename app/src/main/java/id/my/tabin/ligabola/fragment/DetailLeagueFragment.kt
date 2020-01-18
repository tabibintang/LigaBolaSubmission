package id.my.tabin.ligabola.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.my.tabin.ligabola.R
import id.my.tabin.ligabola.adapter.TeamRecyclerViewAdapter
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.model.League
import id.my.tabin.ligabola.model.Team
import id.my.tabin.ligabola.presenter.MainPresenter
import id.my.tabin.ligabola.support.invisible
import id.my.tabin.ligabola.support.visible
import id.my.tabin.ligabola.view.MainView
import kotlinx.android.synthetic.main.fragment_detail_league.*

/**
 * A simple [Fragment] subclass.
 */
class DetailLeagueFragment : Fragment(), MainView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: TeamRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_league, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val league = arguments?.getParcelable<League>("league")

        val request = ApiRepository()
        val gson = Gson()

        recycler_detail_list_team.layoutManager = LinearLayoutManager(context)
        adapter = TeamRecyclerViewAdapter(
            teams,
            context!!
        )
        recycler_detail_list_team.adapter = adapter

        presenter =
            MainPresenter(this, request, gson)

        presenter.getDetailLeague(league?.id)
        //presenter.getTeamList(league?.name)

//        refresh_layout_detail.onRefresh {
//            presenter.getDetailLeague(league?.id,league?.name)
//        }

    }

    override fun showLoading() {
        progress_bar_detail.visible()
    }

    override fun hideLoading() {
        progress_bar_detail.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showDetailLeague(data: List<League>) {
        text_detail_name.text = data[0].name
        //leagueDescription.text = tim.description
        data[0].badge!!.let { Picasso.get().load(it).fit().into(image_detail_league) }
    }

}
