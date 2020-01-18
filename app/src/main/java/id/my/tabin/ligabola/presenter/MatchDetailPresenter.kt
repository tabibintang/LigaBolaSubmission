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
                var homeBadge : String? = ""
                var awayBadge : String? = ""

                for (home in dataHome.teams){homeBadge = home.teamBadge}
                for (home in dataAway.teams){awayBadge = home.teamBadge}
                matches.add(
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
                        event.timeLocal,
                        event.round,
                        event.homeFormation,
                        event.homeKeeper,
                        event.homeDefense,
                        event.homeMidField,
                        event.homeForward,
                        event.homeSubtitute,
                        event.homeGoal,
                        event.awayFormation,
                        event.awayKeeper,
                        event.awayDefense,
                        event.awayMidField,
                        event.awayForward,
                        event.awaySubtitute,
                        event.awayGoal
                    )
                )
            }
            view.hideLoading()
            view.showMatchDetail(matches)
        }
    }
}