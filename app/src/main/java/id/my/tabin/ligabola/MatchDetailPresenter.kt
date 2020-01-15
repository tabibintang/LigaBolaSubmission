package id.my.tabin.ligabola

import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchDetailPresenter(
    private val view: MatchDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    private var matches: MutableList<Event> = mutableListOf()
    fun getMatchDetail(idEvent: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getMatchDetail(idEvent)),
                EventResponse::class.java
            )
            for (i in 0 until data.events.size) {
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
                matches.add(
                    Event(
                        data.events[i].id,
                        data.events[i].eventName,
                        data.events[i].homeTeam,
                        data.events[i].awayTeam,
                        if (data.events[i].homeScore == null) {
                            "-"
                        } else {
                            data.events[i].homeScore
                        },
                        if (data.events[i].awayScore == null) {
                            "-"
                        } else {
                            data.events[i].awayScore
                        },
                        dataHome.teams[0].teamBadge, //data.events[i].homeBadge,
                        dataAway.teams[0].teamBadge, //data.events[i].awayBadge,
                        data.events[i].dateEventLocal,
                        data.events[i].timeLocal,
                        data.events[i].round,
                        data.events[i].homeFormation,
                        data.events[i].homeKeeper,
                        data.events[i].homeDefense,
                        data.events[i].homeMidField,
                        data.events[i].homeForward,
                        data.events[i].homeSubtitute,
                        data.events[i].homeGoal,
                        data.events[i].awayFormation,
                        data.events[i].awayKeeper,
                        data.events[i].awayDefense,
                        data.events[i].awayMidField,
                        data.events[i].awayForward,
                        data.events[i].awaySubtitute,
                        data.events[i].awayGoal
                    )
                )
            }
            uiThread {
                view.hideLoading()
                view.showMatchDetail(matches)
            }
        }
    }
}