package id.my.tabin.ligabola.presenter

import com.google.gson.Gson
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.api.TheSportDBApi
import id.my.tabin.ligabola.model.FavouriteTeam
import id.my.tabin.ligabola.model.Team
import id.my.tabin.ligabola.response.TeamResponse
import id.my.tabin.ligabola.support.CoroutineContextProvider
import id.my.tabin.ligabola.view.TeamListView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TeamListPresenter(
    private val view: TeamListView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getFavouriteTeamList(favourites: List<FavouriteTeam>) {
        GlobalScope.launch(context.main) {
            view.showLoading()

            var listTeam: MutableList<Team> = mutableListOf()
            for (favourite in favourites) {
                val dataTeam = gson.fromJson(
                    apiRepository
                        .doRequestAsync(
                            TheSportDBApi.getDetailTeamById(
                                favourite.idTeam
                            )
                        ).await(),
                    TeamResponse::class.java
                )

                for (data in dataTeam.teams) {
                    listTeam.add(Team(data.teamId, data.teamName, data.teamBadge))
                }
            }
            view.hideLoading()
            view.showTeamList(listTeam)

        }
    }

    fun getSearchTeamList(query: String) {
        GlobalScope.launch(context.main) {
            view.showLoading()

            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(
                        TheSportDBApi.getSearchTeamByName(
                            query
                        )
                    ).await(),
                TeamResponse::class.java
            )

            view.hideLoading()
            view.showTeamList(data.teams)

        }
    }
}