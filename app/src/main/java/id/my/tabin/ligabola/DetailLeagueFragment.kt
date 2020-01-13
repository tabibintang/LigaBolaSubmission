package id.my.tabin.ligabola


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_league.*
import org.jetbrains.anko.support.v4.onRefresh

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

//        text_detail_name.text = league?.name
//        league?.image!!.let { Picasso.get().load(it).fit().into(image_detail_league) }

        val request = ApiRepository()
        val gson = Gson()

        presenter = MainPresenter(this, request, gson)

        presenter.getDetailLeague(league?.id)
        adapter = TeamRecyclerViewAdapter(teams)
        recycler_detail_list_team.adapter = adapter

        presenter = MainPresenter(this, request, gson)

        presenter.getTeamList(league?.name)

//        refresh_layout_detail.onRefresh {
//            presenter.getTeamList(league?.name)
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
