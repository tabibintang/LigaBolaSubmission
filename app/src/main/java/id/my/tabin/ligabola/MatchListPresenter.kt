package id.my.tabin.ligabola

import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchListPresenter(
    private val view: MatchListView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    private var matchesList: MutableList<Event> = mutableListOf()
    fun getMatchList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getNextMatch(league)),
                EventResponse::class.java
            )
            for(i in 0 until data.events.size){
                val dataHome = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(data.events[i].homeTeam)),
                    TeamResponse::class.java
                )
                val dataAway = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(data.events[i].awayTeam)),
                    TeamResponse::class.java
                )
                matchesList.add(
                    Event(
                        data.events[i].id,
                        data.events[i].homeTeam,
                        data.events[i].awayTeam,
                        data.events[i].homeScore,
                        data.events[i].awayScore,
                        dataHome.teams[0].teamBadge, //data.events[i].homeBadge,
                        dataAway.teams[0].teamBadge, //data.events[i].awayBadge,
                        data.events[i].dateEventLocal,
                        data.events[i].timeLocal
                    )
                )
            }
            uiThread {
                view.hideLoading()
                view.showMatchList(matchesList)
            }
        }
    }
}