package id.my.tabin.ligabola.presenter

import com.google.gson.Gson
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.api.TheSportDBApi
import id.my.tabin.ligabola.model.Event
import id.my.tabin.ligabola.response.EventResponse
import id.my.tabin.ligabola.response.TeamResponse
import id.my.tabin.ligabola.support.CoroutineContextProvider
import id.my.tabin.ligabola.view.MatchDetailView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchDetailPresenter(
    private val view: MatchDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    private var matches: MutableList<Event> = mutableListOf()
    fun getMatchDetail(idEvent: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(
                        TheSportDBApi.getMatchDetail(
                            idEvent
                        )
                    ).await(),
                EventResponse::class.java
            )
            for (i in 0 until data.events.size) {
                val dataHome = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getTeamDetail(
                                data.events[i].homeTeam
                            )
                        ).await(),
                    TeamResponse::class.java
                )
                val dataAway = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getTeamDetail(
                                data.events[i].awayTeam
                            )
                        ).await(),
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
            view.hideLoading()
            view.showMatchDetail(matches)
        }
    }
}