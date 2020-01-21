package id.my.tabin.ligabola.presenter

import com.google.gson.Gson
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.api.TheSportDBApi
import id.my.tabin.ligabola.model.Table
import id.my.tabin.ligabola.response.TableResponse
import id.my.tabin.ligabola.response.TeamResponse
import id.my.tabin.ligabola.support.CoroutineContextProvider
import id.my.tabin.ligabola.view.StandingListView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StandingPresenter(
    private val view: StandingListView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    private var tableList: MutableList<Table> = mutableListOf()
    fun getStandingList(idLeague: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(
                        TheSportDBApi.getStandingList(
                            idLeague
                        )
                    ).await(),
                TableResponse::class.java
            )
            for (table in data.table) {
                val dataTeam = gson.fromJson(
                    apiRepository
                        .doRequestAsync(
                            TheSportDBApi.getSearchTeamByName(
                                table.teamName
                            )
                        ).await(),
                    TeamResponse::class.java
                )

                var teamBadge: String? = ""

                for (team in dataTeam.teams) {
                    teamBadge = team.teamBadge
                }
                tableList.add(
                    Table(
                        table.teamId,
                        table.teamName,
                        table.win,
                        table.draw,
                        table.loss,
                        teamBadge
                    )
                )
            }
            view.hideLoading()
            view.showTableList(tableList)

        }
    }
}