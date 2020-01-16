package id.my.tabin.ligabola.presenter

import com.google.gson.Gson
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.api.TheSportDBApi
import id.my.tabin.ligabola.model.Event
import id.my.tabin.ligabola.response.EventResponse
import id.my.tabin.ligabola.response.EventSearchResponse
import id.my.tabin.ligabola.response.TeamResponse
import id.my.tabin.ligabola.view.MatchListView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchListPresenter(
    private val view: MatchListView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    private var matchesList: MutableList<Event> = mutableListOf()
    fun getMatchNextList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(
                        TheSportDBApi.getNextMatch(
                            league
                        )
                    ),
                EventResponse::class.java
            )
            for(i in 0 until data.events.size){
                val dataHome = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getTeamDetail(
                                data.events[i].homeTeam
                            )
                        ),
                    TeamResponse::class.java
                )
                val dataAway = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getTeamDetail(
                                data.events[i].awayTeam
                            )
                        ),
                    TeamResponse::class.java
                )
                matchesList.add(
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
    fun getMatchPrevList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(
                        TheSportDBApi.getPrevMatch(
                            league
                        )
                    ),
                EventResponse::class.java
            )
            for(i in 0 until data.events.size){
                val dataHome = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getTeamDetail(
                                data.events[i].homeTeam
                            )
                        ),
                    TeamResponse::class.java
                )
                val dataAway = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getTeamDetail(
                                data.events[i].awayTeam
                            )
                        ),
                    TeamResponse::class.java
                )
                matchesList.add(
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
    fun getMatchSearchList(search: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(
                        TheSportDBApi.getSearchMatch(
                            search
                        )
                    ),
                EventSearchResponse::class.java
            )
            for(i in 0 until data.event.size){
                val dataHome = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getTeamDetail(
                                data.event[i].homeTeam
                            )
                        ),
                    TeamResponse::class.java
                )
                val dataAway = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getTeamDetail(
                                data.event[i].awayTeam
                            )
                        ),
                    TeamResponse::class.java
                )
                matchesList.add(
                    Event(
                        data.event[i].id,
                        data.event[i].eventName,
                        data.event[i].homeTeam,
                        data.event[i].awayTeam,
                        if (data.event[i].homeScore == null) {
                            "-"
                        } else {
                            data.event[i].homeScore
                        },
                        if (data.event[i].awayScore == null) {
                            "-"
                        } else {
                            data.event[i].awayScore
                        },
                        dataHome.teams[0].teamBadge, //data.event[i].homeBadge,
                        dataAway.teams[0].teamBadge, //data.event[i].awayBadge,
                        data.event[i].dateEventLocal,
                        data.event[i].timeLocal
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