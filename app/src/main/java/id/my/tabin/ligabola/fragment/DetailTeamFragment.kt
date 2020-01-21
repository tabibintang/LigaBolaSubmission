package id.my.tabin.ligabola.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.my.tabin.ligabola.R
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.model.Team
import id.my.tabin.ligabola.presenter.TeamDetailPresenter
import id.my.tabin.ligabola.support.invisible
import id.my.tabin.ligabola.support.visible
import id.my.tabin.ligabola.view.TeamDetailView
import kotlinx.android.synthetic.main.fragment_detail_team.*

/**
 * A simple [Fragment] subclass.
 */
class DetailTeamFragment : Fragment(), TeamDetailView {
    private lateinit var presenter: TeamDetailPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val team = arguments?.getParcelable<Team>("team")

        val request = ApiRepository()
        val gson = Gson()
        presenter =
            TeamDetailPresenter(this, request, gson)

        presenter.getTeamDetail(team?.teamId!!)
    }

    override fun showLoading() {
        progress_bar_detail_team.visible()
    }

    override fun hideLoading() {
        progress_bar_detail_team.invisible()
    }

    override fun showMatchDetail(data: List<Team>) {
        text_team_name.text = data[0].teamName
        text_team_stadium.text = data[0].stadium
        text_team_website.text = data[0].website
        text_team_description.text = data[0].description
        data[0].teamBadge!!.let { Picasso.get().load(it).fit().into(image_team_badge) }
    }

}
