package id.my.tabin.ligabola.presenter

import com.google.gson.Gson
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.api.TheSportDBApi
import id.my.tabin.ligabola.response.LeagueResponse
import id.my.tabin.ligabola.response.TeamResponse
import id.my.tabin.ligabola.support.CoroutineContextProvider
import id.my.tabin.ligabola.view.MainView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainPresenter(
    private val view: MainView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getDetailLeague(league: String?) {
        GlobalScope.launch(context.main) {
            view.showLoading()
            val data = gson.fromJson(
                apiRepository
                    .doRequest(
                        TheSportDBApi.getDetailLeague(
                            league
                        )
                    ).await(),
                LeagueResponse::class.java
            )
            val dataTeam = gson.fromJson(
                apiRepository
                    .doRequest(
                        TheSportDBApi.getTeams(
                            data.leagues[0].name
                        )
                    ).await(),
                TeamResponse::class.java
            )
            view.hideLoading()
            view.showDetailLeague(data.leagues)
            view.showTeamList(dataTeam.teams)

        }
    }
}
