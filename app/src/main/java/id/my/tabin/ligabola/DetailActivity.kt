package id.my.tabin.ligabola

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.widget.*
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

import id.my.tabin.ligabola.R.color.colorAccent
import org.jetbrains.anko.support.v4.onRefresh

class DetailActivity : AppCompatActivity(), AnkoLogger, MainView {

    lateinit var leagueImage: ImageView
    lateinit var leagueName: TextView
    lateinit var leagueDescription: TextView

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner

    private var teams: MutableList<Team> = mutableListOf()
    private var leagues: MutableList<League> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: TeamRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            //scrollView {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.VERTICAL
                    leagueImage = imageView(R.drawable.american_mayor_league) {
                        id = R.id.detail_league_image
                        scaleType = ImageView.ScaleType.FIT_XY
                    }.lparams(
                        width = dip(160),
                        height = dip(160)
                    ) {
                        gravity = Gravity.CENTER
                    }
                    leagueName = textView {
                        id = R.id.detail_league_name
                        textSize = 25f
                        gravity = Gravity.CENTER
                    }.lparams {
                        padding = dip(5)
                        width = matchParent
                        height = matchParent
                        topMargin = dip(10)
                    }
//                    leagueDescription = textView {
//                        id = R.id.detail_league_description
//                        textSize = 15f
//                    }.lparams {
//                        padding = dip(5)
//                        leftMargin = dip(8)
//                        rightMargin = dip(8)
//                    }

                //spinner = spinner ()
                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(
                        colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                    )

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        listTeam = recyclerView {
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(context)
                        }

                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }

            val intent = intent
            val league = intent.getParcelableExtra<League>("league")


            val request = ApiRepository()
            val gson = Gson()

            presenter = MainPresenter(this, request, gson)

            presenter.getDetailLeague(league.id)
//
//            leagueName.text = league.name
//            //leagueDescription.text = tim.description
//            league.image!!.let { Picasso.get().load(it).fit().into(leagueImage) }

            adapter = TeamRecyclerViewAdapter(teams)
            listTeam.adapter = adapter

            presenter = MainPresenter(this, request, gson)

            presenter.getTeamList(league.name)

            swipeRefresh.onRefresh {
                presenter.getTeamList(league.name)
            }

        } catch (e: Exception) {
            info("DetailActivity: Load Data")
            error(e.message)
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showDetailLeague(data: List<League>){
        leagueName.text = data[0].name
        //leagueDescription.text = tim.description
        data[0].badge!!.let { Picasso.get().load(it).fit().into(leagueImage) }
    }
}

