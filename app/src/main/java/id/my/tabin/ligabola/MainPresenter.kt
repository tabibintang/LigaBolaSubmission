package id.my.tabin.ligabola

import com.google.gson.Gson
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
                    .doRequest(TheSportDBApi.getDetailLeague(league)),
                LeagueResponse::class.java
            )
            val dataTeam = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeams(leagueName)),
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