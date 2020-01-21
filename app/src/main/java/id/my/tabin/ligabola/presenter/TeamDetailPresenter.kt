package id.my.tabin.ligabola.presenter

import com.google.gson.Gson
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.api.TheSportDBApi
import id.my.tabin.ligabola.response.TeamResponse
import id.my.tabin.ligabola.support.CoroutineContextProvider
import id.my.tabin.ligabola.view.TeamDetailView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamDetailPresenter(
    private val view: TeamDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getTeamDetail(idTeam: String) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(
                        TheSportDBApi.getDetailTeamById(
                            idTeam
                        )
                    ).await(),
                TeamResponse::class.java
            )
            view.hideLoading()
            view.showMatchDetail(data.teams)
        }
    }
}