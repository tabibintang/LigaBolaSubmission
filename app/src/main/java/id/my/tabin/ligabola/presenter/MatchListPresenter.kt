package id.my.tabin.ligabola.presenter

import com.google.gson.Gson
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.api.TheSportDBApi
import id.my.tabin.ligabola.model.Event
import id.my.tabin.ligabola.model.Favourite
import id.my.tabin.ligabola.response.EventResponse
import id.my.tabin.ligabola.response.EventSearchResponse
import id.my.tabin.ligabola.response.TeamResponse
import id.my.tabin.ligabola.support.CoroutineContextProvider
import id.my.tabin.ligabola.view.MatchListView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchListPresenter(
    private val view: MatchListView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    private var matchesList: MutableList<Event> = mutableListOf()
    fun getMatchNextList(league: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(
                        TheSportDBApi.getNextMatch(
                            league
                        )
                    ).await(),
                EventResponse::class.java
            )
            for (event in data.events) {
                val dataHome = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getTeamDetail(
                                event.homeTeam
                            )
                        ).await(),
                    TeamResponse::class.java
                )
                val dataAway = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getTeamDetail(
                                event.awayTeam
                            )
                        ).await(),
                    TeamResponse::class.java
                )
                var homeBadge: String? = ""
                var awayBadge: String? = ""

                for (home in dataHome.teams) {
                    homeBadge = home.teamBadge
                }
                for (home in dataAway.teams) {
                    awayBadge = home.teamBadge
                }
                matchesList.add(
                    Event(
                        event.id,
                        event.eventName,
                        event.homeTeam,
                        event.awayTeam,
                        if (event.homeScore == null) {
                            "-"
                        } else {
                            event.homeScore
                        },
                        if (event.awayScore == null) {
                            "-"
                        } else {
                            event.awayScore
                        },
                        homeBadge, //event.homeBadge,
                        awayBadge, //event.awayBadge,
                        event.dateEventLocal,
                        event.timeLocal
                    )
                )
            }
            view.showMatchList(matchesList)

            view.hideLoading()
        }
    }

    fun getMatchPrevList(league: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(
                        TheSportDBApi.getPrevMatch(
                            league
                        )
                    ).await(),
                EventResponse::class.java
            )
            for (event in data.events) {
                val dataHome = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getTeamDetail(
                                event.homeTeam
                            )
                        ).await(),
                    TeamResponse::class.java
                )
                val dataAway = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getTeamDetail(
                                event.awayTeam
                            )
                        ).await(),
                    TeamResponse::class.java
                )
                var homeBadge: String? = ""
                var awayBadge: String? = ""

                for (home in dataHome.teams) {
                    homeBadge = home.teamBadge
                }
                for (home in dataAway.teams) {
                    awayBadge = home.teamBadge
                }
                matchesList.add(
                    Event(
                        event.id,
                        event.eventName,
                        event.homeTeam,
                        event.awayTeam,
                        if (event.homeScore == null) {
                            "-"
                        } else {
                            event.homeScore
                        },
                        if (event.awayScore == null) {
                            "-"
                        } else {
                            event.awayScore
                        },
                        homeBadge, //event.homeBadge,
                        awayBadge, //event.awayBadge,
                        event.dateEventLocal,
                        event.timeLocal
                    )
                )
            }
            view.hideLoading()
            view.showMatchList(matchesList)

        }
    }

    fun getMatchSearchList(search: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(
                        TheSportDBApi.getSearchMatch(
                            search
                        )
                    ).await(),
                EventSearchResponse::class.java
            )
            for (event in data.event) {
                if (event.sportCategory != "Soccer") {
                    continue
                }
                val dataHome = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getTeamDetail(
                                event.homeTeam
                            )
                        ).await(),
                    TeamResponse::class.java
                )
                val dataAway = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getTeamDetail(
                                event.awayTeam
                            )
                        ).await(),
                    TeamResponse::class.java
                )
                var homeBadge: String? = ""
                var awayBadge: String? = ""

                for (home in dataHome.teams) {
                    homeBadge = home.teamBadge
                }
                for (home in dataAway.teams) {
                    awayBadge = home.teamBadge
                }
                matchesList.add(
                    Event(
                        event.id,
                        event.eventName,
                        event.homeTeam,
                        event.awayTeam,
                        if (event.homeScore == null) {
                            "-"
                        } else {
                            event.homeScore
                        },
                        if (event.awayScore == null) {
                            "-"
                        } else {
                            event.awayScore
                        },
                        homeBadge, //event.homeBadge,
                        awayBadge, //event.awayBadge,
                        event.dateEventLocal,
                        event.timeLocal
                    )
                )
            }
            view.hideLoading()
            view.showMatchList(matchesList)

        }
    }

    fun getMatchFavouriteList(favourites: List<Favourite>) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            matchesList.clear()
            for (favourite in favourites) {
                val data = gson.fromJson(
                    apiRepository
                        .doRequest(
                            TheSportDBApi.getMatchDetail(
                                favourite.idEvent
                            )
                        ).await(),
                    EventResponse::class.java
                )
                for (event in data.events) {
                    val dataHome = gson.fromJson(
                        apiRepository
                            .doRequest(
                                TheSportDBApi.getTeamDetail(
                                    event.homeTeam
                                )
                            ).await(),
                        TeamResponse::class.java
                    )
                    val dataAway = gson.fromJson(
                        apiRepository
                            .doRequest(
                                TheSportDBApi.getTeamDetail(
                                    event.awayTeam
                                )
                            ).await(),
                        TeamResponse::class.java
                    )
                    var homeBadge: String? = ""
                    var awayBadge: String? = ""

                    for (home in dataHome.teams) {
                        homeBadge = home.teamBadge
                    }
                    for (home in dataAway.teams) {
                        awayBadge = home.teamBadge
                    }

                    matchesList.add(
                        Event(
                            event.id,
                            event.eventName,
                            event.homeTeam,
                            event.awayTeam,
                            if (event.homeScore == null) {
                                "-"
                            } else {
                                event.homeScore
                            },
                            if (event.awayScore == null) {
                                "-"
                            } else {
                                event.awayScore
                            },
                            homeBadge, //event.homeBadge,
                            awayBadge, //event.awayBadge,
                            event.dateEventLocal,
                            event.timeLocal
                        )
                    )
                }
            }
            view.hideLoading()
            view.showMatchList(matchesList)
        }
    }
}