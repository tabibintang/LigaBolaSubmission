package id.my.tabin.ligabola.presenter

import com.google.gson.Gson
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.api.TheSportDBApi
import id.my.tabin.ligabola.response.LeagueResponse
import id.my.tabin.ligabola.response.TeamResponse
import id.my.tabin.ligabola.view.MainView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(
    private val view: MainView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getDetailLeague(league: String?, leagueName: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(
                        TheSportDBApi.getDetailLeague(
                            league
                        )
                    ),
                LeagueResponse::class.java
            )
            val dataTeam = gson.fromJson(
                apiRepository
                    .doRequest(
                        TheSportDBApi.getTeams(
                            data.leagues[0].name
                        )
                    ),
                TeamResponse::class.java
            )
            uiThread {
                view.hideLoading()
                view.showDetailLeague(data.leagues)
                view.showTeamList(dataTeam.teams)
            }
        }
    }
}